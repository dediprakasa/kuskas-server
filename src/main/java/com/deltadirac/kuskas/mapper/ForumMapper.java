package com.deltadirac.kuskas.mapper;

import java.util.List;

import com.deltadirac.kuskas.dto.ForumDto;
import com.deltadirac.kuskas.model.Forum;
import com.deltadirac.kuskas.model.Post;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForumMapper {

    @Mapping(target = "postCount", expression = "java(mapPosts(forum.getPosts()))")
    ForumDto mapForumToDto(Forum forum);

    default Integer mapPosts(List<Post> posts) {
        return posts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Forum mapDtoToForum(ForumDto forumDto);

}