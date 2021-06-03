package com.deltadirac.kuskas.controller;

import java.util.List;

import com.deltadirac.kuskas.dto.CommentDto;
import com.deltadirac.kuskas.service.CommentService;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentByPostId(postId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUsername(username));
    }
}
