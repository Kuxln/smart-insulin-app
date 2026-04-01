package com.kuxln.smartinsulinbackend.dto;

public record UserProfileResponse(
        String email,
        String fullName,
        Integer diabetesType,
        Double weightKg,
        Double heightCm,
        Double insulinSensitivityFactor,
        Double insulinToCarbRatio,
        Double targetGlucoseMin,
        Double targetGlucoseMax,
        Double durationOfInsulinAction,
        String basalInsulinType,
        String bolusInsulinType
) {}
