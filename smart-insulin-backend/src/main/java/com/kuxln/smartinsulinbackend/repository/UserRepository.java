package com.kuxln.smartinsulinbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kuxln.smartinsulinbackend.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
