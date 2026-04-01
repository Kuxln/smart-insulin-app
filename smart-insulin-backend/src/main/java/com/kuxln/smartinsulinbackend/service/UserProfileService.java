package com.kuxln.smartinsulinbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kuxln.smartinsulinbackend.dto.UserProfileResponse;
import com.kuxln.smartinsulinbackend.dto.UserProfileUpdateRequest;
import com.kuxln.smartinsulinbackend.entity.User;
import com.kuxln.smartinsulinbackend.entity.UserProfile;
import com.kuxln.smartinsulinbackend.repository.UserProfileRepository;
import com.kuxln.smartinsulinbackend.repository.UserRepository;

import java.time.Instant;

@Service
@Transactional
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository,
                              UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(User user) {
        UserProfile profile = userProfileRepository.findByUserId(user.getId()).orElse(null);
        return new UserProfileResponse(
                user.getUsername(),
                user.getFullName(),
                user.getDiabetesType(),
                profile != null ? profile.getWeightKg() : null,
                profile != null ? profile.getHeightCm() : null,
                profile != null ? profile.getInsulinSensitivityFactor() : null,
                profile != null ? profile.getInsulinToCarbRatio() : null,
                profile != null ? profile.getTargetGlucoseMin() : null,
                profile != null ? profile.getTargetGlucoseMax() : null,
                profile != null ? profile.getDurationOfInsulinAction() : null,
                profile != null ? profile.getBasalInsulinType() : null,
                profile != null ? profile.getBolusInsulinType() : null
        );
    }

    public UserProfileResponse updateProfile(User user, UserProfileUpdateRequest request) {
        // Update User fields if changed
        if (request.getFullName() != null || request.getDiabetesType() != null) {
            User managed = userRepository.findById(user.getId()).orElseThrow();
            if (request.getFullName() != null) managed.setFullName(request.getFullName());
            if (request.getDiabetesType() != null) managed.setDiabetesType(request.getDiabetesType());
            userRepository.save(managed);
        }

        // Update or create UserProfile
        UserProfile profile = userProfileRepository.findByUserId(user.getId())
                .orElse(new UserProfile(user));

        if (request.getWeightKg() != null) profile.setWeightKg(request.getWeightKg());
        if (request.getHeightCm() != null) profile.setHeightCm(request.getHeightCm());
        if (request.getInsulinSensitivityFactor() != null) profile.setInsulinSensitivityFactor(request.getInsulinSensitivityFactor());
        if (request.getInsulinToCarbRatio() != null) profile.setInsulinToCarbRatio(request.getInsulinToCarbRatio());
        if (request.getTargetGlucoseMin() != null) profile.setTargetGlucoseMin(request.getTargetGlucoseMin());
        if (request.getTargetGlucoseMax() != null) profile.setTargetGlucoseMax(request.getTargetGlucoseMax());
        if (request.getDurationOfInsulinAction() != null) profile.setDurationOfInsulinAction(request.getDurationOfInsulinAction());
        if (request.getBasalInsulinType() != null) profile.setBasalInsulinType(request.getBasalInsulinType());
        if (request.getBolusInsulinType() != null) profile.setBolusInsulinType(request.getBolusInsulinType());
        profile.setUpdatedAt(Instant.now());
        userProfileRepository.save(profile);

        User refreshed = userRepository.findById(user.getId()).orElseThrow();
        return getProfile(refreshed);
    }
}
