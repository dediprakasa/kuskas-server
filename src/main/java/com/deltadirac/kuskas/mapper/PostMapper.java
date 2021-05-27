package com.deltadirac.kuskas.mapper;

import com.deltadirac.kuskas.dto.PostRequest;
import com.deltadirac.kuskas.dto.PostResponse;
import com.deltadirac.kuskas.model.Forum;
import com.deltadirac.kuskas.model.Post;
import com.deltadirac.kuskas.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "forumId", source = "forum.id")
    @Mapping(target = "username", source = "user.username")
    PostResponse mapToDto(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "postRequest.title")
    @Mapping(target = "url", source = "postRequest.url")
    @Mapping(target = "content", source = "postRequest.content")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "forum", source = "forum")
    Post map(PostRequest postRequest, Forum forum, User user);

}
