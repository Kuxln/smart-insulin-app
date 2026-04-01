package com.kuxln.smartinsulinbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kuxln.smartinsulinbackend.entity.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
