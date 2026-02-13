package com.interviewtracker.controller;

import com.interviewtracker.dto.AnalyticsResponse;
import com.interviewtracker.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public ResponseEntity<AnalyticsResponse> getAnalytics(Authentication auth) {
        return ResponseEntity.ok(analyticsService.getAnalytics(auth.getName()));
    }
}
