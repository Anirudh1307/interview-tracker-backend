package com.interviewtracker.service;

import com.interviewtracker.dto.JobApplicationRequest;
import com.interviewtracker.dto.JobApplicationResponse;
import com.interviewtracker.entity.JobApplication;
import com.interviewtracker.entity.User;
import com.interviewtracker.exception.ResourceNotFoundException;
import com.interviewtracker.repository.JobApplicationRepository;
import com.interviewtracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(JobApplicationService.class);
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository, UserRepository userRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
    }

    public JobApplicationResponse create(String username, JobApplicationRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check for duplicates
        if (jobApplicationRepository.existsByUserAndCompanyNameAndRole(user, request.getCompanyName(), request.getRole())) {
            logger.warn("Duplicate job application attempt by user: {} for company: {}, role: {}", username, request.getCompanyName(), request.getRole());
            throw new IllegalArgumentException("Job application already exists for this company and role");
        }

        JobApplication job = new JobApplication();
        job.setUser(user);
        job.setCompanyName(request.getCompanyName());
        job.setRole(request.getRole());
        job.setAppliedDate(request.getAppliedDate());
        job.setStatus(request.getStatus());

        JobApplication saved = jobApplicationRepository.save(job);
        logger.info("Job application created: id={}, user={}, company={}", saved.getId(), username, request.getCompanyName());
        return toResponse(saved);
    }

    public List<JobApplicationResponse> getAll(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return jobApplicationRepository.findByUserId(user.getId()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public JobApplicationResponse getById(String username, Long id) {
        JobApplication job = findJobByIdAndUsername(id, username);
        return toResponse(job);
    }

    public JobApplicationResponse update(String username, Long id, JobApplicationRequest request) {
        JobApplication job = findJobByIdAndUsername(id, username);
        job.setCompanyName(request.getCompanyName());
        job.setRole(request.getRole());
        job.setAppliedDate(request.getAppliedDate());
        job.setStatus(request.getStatus());
        return toResponse(jobApplicationRepository.save(job));
    }

    public void delete(String username, Long id) {
        JobApplication job = findJobByIdAndUsername(id, username);
        jobApplicationRepository.delete(job);
        logger.info("Job application deleted: id={}, user={}", id, username);
    }

    private JobApplication findJobByIdAndUsername(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        JobApplication job = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));
        if (!job.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Job application not found");
        }
        return job;
    }

    private JobApplicationResponse toResponse(JobApplication job) {
        JobApplicationResponse response = new JobApplicationResponse();
        response.setId(job.getId());
        response.setCompanyName(job.getCompanyName());
        response.setRole(job.getRole());
        response.setAppliedDate(job.getAppliedDate());
        response.setStatus(job.getStatus());
        response.setCreatedAt(job.getCreatedAt());
        response.setUpdatedAt(job.getUpdatedAt());
        return response;
    }
}
