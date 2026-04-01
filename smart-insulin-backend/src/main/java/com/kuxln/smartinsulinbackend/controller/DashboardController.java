package com.kuxln.smartinsulinbackend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.kuxln.smartinsulinbackend.entity.User;
import com.kuxln.smartinsulinbackend.service.GlucoseReadingService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Controller
public class DashboardController {
    private final GlucoseReadingService glucoseReadingService;

    public DashboardController(GlucoseReadingService glucoseReadingService) {
        this.glucoseReadingService = glucoseReadingService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        Instant now = Instant.now();
        Instant from = now.minus(90, ChronoUnit.DAYS);

        model.addAttribute("user", user);
        model.addAttribute("hba1c", glucoseReadingService.calculateHbA1c(user.getId(), from, now));
        model.addAttribute("readings", glucoseReadingService.getRecentReadings(user.getId(), 10));
        return "dashboard";
    }
}
