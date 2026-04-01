package com.kuxln.smartinsulinbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateRequest {
    private String fullName;
    private Integer diabetesType;
    private Double weightKg;
    private Double heightCm;
    private Double insulinSensitivityFactor;
    private Double insulinToCarbRatio;
    private Double targetGlucoseMin;
    private Double targetGlucoseMax;
    private Double durationOfInsulinAction;
    private String basalInsulinType;
    private String bolusInsulinType;
}
