package com.kuxln.smartinsulinbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kuxln.smartinsulinbackend.entity.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);
}
