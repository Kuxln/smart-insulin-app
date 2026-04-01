package com.kuxln.smartinsulinbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kuxln.smartinsulinbackend.entity.GlucoseReading;

import java.time.Instant;
import java.util.List;

public interface GlucoseReadingRepository extends JpaRepository<GlucoseReading, Long> {
    Page<GlucoseReading> findByUserIdOrderByMeasuredAtDesc(Long userId, Pageable pageable);
    List<GlucoseReading> findByUserIdAndMeasuredAtBetweenOrderByMeasuredAtAsc(
            Long userId, Instant from, Instant to);
}
