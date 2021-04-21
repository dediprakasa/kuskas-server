package com.deltadirac.kuskas.repository;

import java.util.List;

import com.deltadirac.kuskas.model.Forum;
import com.deltadirac.kuskas.model.Post;
import com.deltadirac.kuskas.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByForum(Forum forum);
    List<Post> findAllByUser(User user);
}
