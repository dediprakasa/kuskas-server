package com.deltadirac.kuskas.service;

import java.util.List;
import java.util.stream.Collectors;

import com.deltadirac.kuskas.dto.CommentDto;
import com.deltadirac.kuskas.exception.PostNotFoundException;
import com.deltadirac.kuskas.mapper.CommentMapper;
import com.deltadirac.kuskas.model.Comment;
import com.deltadirac.kuskas.model.NotificationEmail;
import com.deltadirac.kuskas.model.Post;
import com.deltadirac.kuskas.model.User;
import com.deltadirac.kuskas.repository.CommentRepository;
import com.deltadirac.kuskas.repository.PostRepository;
import com.deltadirac.kuskas.repository.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void createComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));

        Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder
                .build(post.getUser().getUsername() + " commented on your post. " + POST_URL);
        sendCommentNotification(message, authService.getCurrentUser());
    }

    public List<CommentDto> getCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findAllByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(
                new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
    }

}
