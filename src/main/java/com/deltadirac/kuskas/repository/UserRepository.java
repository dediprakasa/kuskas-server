package com.deltadirac.kuskas.repository;

import java.util.List;
// import java.util.Optional;

import com.deltadirac.kuskas.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
}
