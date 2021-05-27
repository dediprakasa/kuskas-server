package com.deltadirac.kuskas.repository;

import java.util.List;
import java.util.Optional;

import com.deltadirac.kuskas.model.Forum;
import com.deltadirac.kuskas.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    List<Forum> findAllByUser(User user);

    Optional<Forum> findByName(String forumName);
}
