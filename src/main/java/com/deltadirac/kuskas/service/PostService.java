package com.deltadirac.kuskas.service;

import java.util.List;
import java.util.stream.Collectors;

import com.deltadirac.kuskas.dto.PostRequest;
import com.deltadirac.kuskas.dto.PostResponse;
import com.deltadirac.kuskas.exception.ForumNotFoundException;
import com.deltadirac.kuskas.exception.PostNotFoundException;
import com.deltadirac.kuskas.mapper.PostMapper;
import com.deltadirac.kuskas.model.Forum;
import com.deltadirac.kuskas.model.Post;
import com.deltadirac.kuskas.model.User;
import com.deltadirac.kuskas.repository.ForumRepository;
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
public class PostService {

    private final PostRepository postRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    @Transactional
    public void save(PostRequest postRequest) {
        Forum forum = forumRepository.findById(postRequest.getForumId())
                .orElseThrow(() -> new ForumNotFoundException(postRequest.getForumId().toString()));

        postRepository.save(postMapper.map(postRequest, forum, authService.getCurrentUser()));

    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllMyPosts() {
        return postRepository.findAllByUser(authService.getCurrentUser()).stream().map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));

        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return postRepository.findAllByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByForumId(Long id) {
        return postRepository.findAllByForumId(id).stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

}
