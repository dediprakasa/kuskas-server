package com.deltadirac.kuskas.controller;

import com.deltadirac.kuskas.dto.PostRequest;
import com.deltadirac.kuskas.dto.PostResponse;
import com.deltadirac.kuskas.model.Post;
import com.deltadirac.kuskas.service.PostService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllMyPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllMyPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @GetMapping("forum/{id}")
    public ResponseEntity<List<PostResponse>> getPostsByForumId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByForumId(id));
    }

    @GetMapping("user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }

    @PreAuthorize("isAuthor(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
