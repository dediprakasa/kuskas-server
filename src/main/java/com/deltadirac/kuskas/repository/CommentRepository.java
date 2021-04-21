package com.deltadirac.kuskas.repository;

import java.util.List;

import com.deltadirac.kuskas.model.Comment;
import com.deltadirac.kuskas.model.Post;
import com.deltadirac.kuskas.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);
}
