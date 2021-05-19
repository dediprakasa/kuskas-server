package com.deltadirac.kuskas.service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.deltadirac.kuskas.dto.AuthenticationResponse;
import com.deltadirac.kuskas.dto.LoginRequest;
import com.deltadirac.kuskas.dto.RegisterRequest;
import com.deltadirac.kuskas.exception.KuskasException;
import com.deltadirac.kuskas.model.NotificationEmail;
import com.deltadirac.kuskas.model.User;
import com.deltadirac.kuskas.model.VerificationToken;
import com.deltadirac.kuskas.repository.UserRepository;
import com.deltadirac.kuskas.repository.VerificationTokenRepository;
import com.deltadirac.kuskas.security.JWTProvider;
import com.deltadirac.kuskas.util.Constants;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final Validator validator;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setEnabled(false);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        for (ConstraintViolation<User> violation : violations) {
            if (!violation.getMessage().isEmpty()) {
                log.error(violation.getMessage());
                throw new KuskasException(violation.getMessage());
            }
        }

        userRepository.save(user);
        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Hi, " + user.getUsername()
                + "!. Thank you for signing up. Please click the following link to activate your account: "
                + Constants.ACTIVATION_EMAIL + "/" + token);

        mailService.sendMail(new NotificationEmail("Please activate your account.", user.getEmail(), message));
    }

    @Transactional
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authenticationToken = jwtProvider.generateToken(authentication);

        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now().plusSeconds(600));

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new KuskasException("Invalid token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new KuskasException("username " + username + " not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
