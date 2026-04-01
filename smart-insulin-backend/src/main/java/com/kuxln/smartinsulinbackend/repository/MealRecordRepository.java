package com.kuxln.smartinsulinbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kuxln.smartinsulinbackend.entity.MealRecord;

public interface MealRecordRepository extends JpaRepository<MealRecord, Long> {
}
