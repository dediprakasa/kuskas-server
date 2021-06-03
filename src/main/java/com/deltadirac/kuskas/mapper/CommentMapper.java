package com.deltadirac.kuskas.mapper;

import com.deltadirac.kuskas.dto.CommentDto;
import com.deltadirac.kuskas.model.Comment;
import com.deltadirac.kuskas.model.Post;
import com.deltadirac.kuskas.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "commentDto.content")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);
}
