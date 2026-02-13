package com.interviewtracker.controller;

import com.interviewtracker.dto.InterviewRoundRequest;
import com.interviewtracker.dto.InterviewRoundResponse;
import com.interviewtracker.service.InterviewRoundService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs/{jobId}/rounds")
@CrossOrigin(origins = "*")
public class InterviewRoundController {

    private final InterviewRoundService interviewRoundService;

    public InterviewRoundController(InterviewRoundService interviewRoundService) {
        this.interviewRoundService = interviewRoundService;
    }

    @PostMapping
    public ResponseEntity<InterviewRoundResponse> create(
            @PathVariable Long jobId,
            @RequestBody InterviewRoundRequest request,
            Authentication auth) {
        return ResponseEntity.ok(interviewRoundService.create(auth.getName(), jobId, request));
    }

    @GetMapping
    public ResponseEntity<List<InterviewRoundResponse>> getByJobId(
            @PathVariable Long jobId,
            Authentication auth) {
        return ResponseEntity.ok(interviewRoundService.getByJobId(auth.getName(), jobId));
    }

    @PutMapping("/{roundId}")
    public ResponseEntity<InterviewRoundResponse> update(
            @PathVariable Long jobId,
            @PathVariable Long roundId,
            @RequestBody InterviewRoundRequest request,
            Authentication auth) {
        return ResponseEntity.ok(interviewRoundService.update(auth.getName(), jobId, roundId, request));
    }

    @DeleteMapping("/{roundId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long jobId,
            @PathVariable Long roundId,
            Authentication auth) {
        interviewRoundService.delete(auth.getName(), jobId, roundId);
        return ResponseEntity.noContent().build();
    }
}
