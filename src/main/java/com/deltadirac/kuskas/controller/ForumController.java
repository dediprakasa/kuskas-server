package com.deltadirac.kuskas.controller;

import java.util.List;

import javax.validation.Valid;

import com.deltadirac.kuskas.dto.ForumDto;
import com.deltadirac.kuskas.service.ForumService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/forum")
@AllArgsConstructor
public class ForumController {

    private final ForumService forumService;

    @GetMapping
    public List<ForumDto> getAllForums() {
        return forumService.getAll();
    }

    @GetMapping("/{id}")
    public ForumDto getForum(@PathVariable Long id) {
        return forumService.getForum(id);
    }

    @PostMapping
    public ForumDto create(@RequestBody @Valid ForumDto forumDto) {
        return forumService.save(forumDto);
    }
}
