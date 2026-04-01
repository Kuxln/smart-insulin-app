package com.kuxln.smartinsulinbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kuxln.smartinsulinbackend.entity.InsulinDose;

public interface InsulinDoseRepository extends JpaRepository<InsulinDose, Long> {
}
