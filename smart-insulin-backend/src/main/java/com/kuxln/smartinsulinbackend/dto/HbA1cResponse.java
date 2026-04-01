package com.kuxln.smartinsulinbackend.dto;

public record HbA1cResponse(
        double hba1c,
        int readingsCount,
        double averageGlucose
) {}
