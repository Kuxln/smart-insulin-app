package com.kuxln.smartinsulinbackend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kuxln.smartinsulinbackend.dto.UserProfileResponse;
import com.kuxln.smartinsulinbackend.dto.UserProfileUpdateRequest;
import com.kuxln.smartinsulinbackend.entity.User;
import com.kuxln.smartinsulinbackend.service.UserProfileService;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public String profile(@AuthenticationPrincipal User user, Model model) {
        UserProfileResponse p = userProfileService.getProfile(user);
        model.addAttribute("profile", p);

        UserProfileUpdateRequest form = new UserProfileUpdateRequest();
        form.setFullName(p.fullName());
        form.setDiabetesType(p.diabetesType());
        form.setWeightKg(p.weightKg());
        form.setHeightCm(p.heightCm());
        form.setInsulinSensitivityFactor(p.insulinSensitivityFactor());
        form.setInsulinToCarbRatio(p.insulinToCarbRatio());
        form.setTargetGlucoseMin(p.targetGlucoseMin());
        form.setTargetGlucoseMax(p.targetGlucoseMax());
        form.setDurationOfInsulinAction(p.durationOfInsulinAction());
        form.setBasalInsulinType(p.basalInsulinType());
        form.setBolusInsulinType(p.bolusInsulinType());
        model.addAttribute("form", form);
        return "profile";
    }

    @PostMapping
    public String updateProfile(@AuthenticationPrincipal User user,
                                @ModelAttribute("form") UserProfileUpdateRequest form,
                                RedirectAttributes redirectAttributes) {
        userProfileService.updateProfile(user, form);
        redirectAttributes.addFlashAttribute("success", "Профіль оновлено.");
        return "redirect:/profile";
    }
}
