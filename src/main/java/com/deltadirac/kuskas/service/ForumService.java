package com.deltadirac.kuskas.service;

import java.util.List;
import java.util.stream.Collectors;

import com.deltadirac.kuskas.dto.ForumDto;
import com.deltadirac.kuskas.exception.ForumNotFoundException;
import com.deltadirac.kuskas.mapper.ForumMapper;
import com.deltadirac.kuskas.model.Forum;
import com.deltadirac.kuskas.repository.ForumRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;
    private final ForumMapper forumMapper;

    @Transactional
    public List<ForumDto> getAll() {
        return forumRepository.findAll().stream().map(forumMapper::mapForumToDto).collect(Collectors.toList());
    }

    @Transactional
    public ForumDto save(ForumDto forumDto) {
        Forum forum = forumRepository.save(forumMapper.mapDtoToForum(forumDto));
        forumDto.setId(forum.getId());

        return forumDto;
    }

    @Transactional(readOnly = true)
    public ForumDto getForum(Long id) {
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new ForumNotFoundException("forum with id " + id + " is not found"));

        return forumMapper.mapForumToDto(forum);
    }

}
