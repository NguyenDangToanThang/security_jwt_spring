package com.socialnetwork.socialbackend.repository;

import com.socialnetwork.socialbackend.entity.Role;
import com.socialnetwork.socialbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    User findByRole(Role role);
}
