package com.deltadirac.kuskas.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.deltadirac.kuskas.dto.ForumDto;
import com.deltadirac.kuskas.exception.ForumNotFoundException;
import com.deltadirac.kuskas.model.Forum;
import com.deltadirac.kuskas.repository.ForumRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final AuthService authService;

    @Transactional
    public List<ForumDto> getAll() {
        return forumRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public ForumDto save(ForumDto forumDto) {
        Forum forum = forumRepository.save(mapToForum(forumDto));
        forumDto.setId(forum.getId());

        return forumDto;
    }

    @Transactional(readOnly = true)
    public ForumDto getForum(Long id) {
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new ForumNotFoundException("forum with id " + id + " is not found"));

        return mapToDto(forum);
    }

    private ForumDto mapToDto(Forum forum) {
        return ForumDto.builder().name(forum.getName()).id(forum.getId()).description(forum.getDescription())
                .postCount(forum.getPosts().size()).build();
    }

    private Forum mapToForum(ForumDto forumDto) {
        return Forum.builder().name("/forum/" + forumDto.getName()).description(forumDto.getDescription())
                .user(authService.getCurrentUser()).createdAt(Instant.now()).build();
    }

}
