package com.interviewtracker.controller;

import com.interviewtracker.dto.ApiResponse;
import com.interviewtracker.dto.JobApplicationRequest;
import com.interviewtracker.dto.JobApplicationResponse;
import com.interviewtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobApplicationResponse>> create(
            @Valid @RequestBody JobApplicationRequest request,
            Authentication auth) {
        JobApplicationResponse response = jobApplicationService.create(auth.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Job application created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobApplicationResponse>>> getAll(Authentication auth) {
        List<JobApplicationResponse> jobs = jobApplicationService.getAll(auth.getName());
        return ResponseEntity.ok(ApiResponse.success("Jobs retrieved successfully", jobs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobApplicationResponse>> getById(@PathVariable Long id, Authentication auth) {
        JobApplicationResponse job = jobApplicationService.getById(auth.getName(), id);
        return ResponseEntity.ok(ApiResponse.success("Job retrieved successfully", job));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobApplicationResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request,
            Authentication auth) {
        JobApplicationResponse response = jobApplicationService.update(auth.getName(), id, request);
        return ResponseEntity.ok(ApiResponse.success("Job application updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, Authentication auth) {
        jobApplicationService.delete(auth.getName(), id);
        return ResponseEntity.ok(ApiResponse.success("Job application deleted successfully", null));
    }
}
