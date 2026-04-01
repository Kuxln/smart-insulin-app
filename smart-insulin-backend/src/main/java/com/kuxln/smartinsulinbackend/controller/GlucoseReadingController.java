package com.kuxln.smartinsulinbackend.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kuxln.smartinsulinbackend.dto.GlucoseReadingForm;
import com.kuxln.smartinsulinbackend.dto.GlucoseReadingResponse;
import com.kuxln.smartinsulinbackend.entity.User;
import com.kuxln.smartinsulinbackend.service.GlucoseReadingService;

@Controller
@RequestMapping("/glucose")
public class GlucoseReadingController {
    private final GlucoseReadingService glucoseReadingService;

    public GlucoseReadingController(GlucoseReadingService glucoseReadingService) {
        this.glucoseReadingService = glucoseReadingService;
    }

    @GetMapping
    public String list(@AuthenticationPrincipal User user,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {
        Page<GlucoseReadingResponse> readings = glucoseReadingService.getReadings(user.getId(), page, 20);
        model.addAttribute("readings", readings);
        model.addAttribute("form", new GlucoseReadingForm());
        model.addAttribute("currentPage", page);
        return "glucose";
    }

    @PostMapping
    public String addReading(@AuthenticationPrincipal User user,
                             @ModelAttribute("form") GlucoseReadingForm form,
                             RedirectAttributes redirectAttributes) {
        glucoseReadingService.addReading(user, form);
        redirectAttributes.addFlashAttribute("success", "Показник глюкози додано.");
        return "redirect:/glucose";
    }

    @PostMapping("/{id}/delete")
    public String deleteReading(@AuthenticationPrincipal User user,
                                @PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        glucoseReadingService.deleteReading(user.getId(), id);
        redirectAttributes.addFlashAttribute("success", "Показник видалено.");
        return "redirect:/glucose";
    }
}
