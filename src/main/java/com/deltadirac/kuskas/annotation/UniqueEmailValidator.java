package com.deltadirac.kuskas.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.deltadirac.kuskas.repository.UserRepository;

@Slf4j
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userRepository == null) {
            log.info("LOHHHHH--------------------------");
            return true;
        }
        log.info("JOSSSSS--------------------------");
        return userRepository.findByEmail(email).isEmpty();
    }
}