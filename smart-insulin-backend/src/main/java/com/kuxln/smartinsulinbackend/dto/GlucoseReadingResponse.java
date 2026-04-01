package com.kuxln.smartinsulinbackend.dto;

import java.time.LocalDateTime;

public record GlucoseReadingResponse(
        Long id,
        Double glucoseValue,
        String measurementType,
        LocalDateTime measuredAt,
        String notes,
        LocalDateTime createdAt
) {}
