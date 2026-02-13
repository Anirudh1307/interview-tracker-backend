package com.interviewtracker.service;

import com.interviewtracker.dto.AnalyticsResponse;
import com.interviewtracker.dto.JobApplicationResponse;
import com.interviewtracker.dto.MonthlyStats;
import com.interviewtracker.entity.JobApplication;
import com.interviewtracker.entity.User;
import com.interviewtracker.enums.ApplicationStatus;
import com.interviewtracker.exception.ResourceNotFoundException;
import com.interviewtracker.repository.JobApplicationRepository;
import com.interviewtracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    public AnalyticsService(JobApplicationRepository jobApplicationRepository, UserRepository userRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
    }

    public AnalyticsResponse getAnalytics(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        AnalyticsResponse response = new AnalyticsResponse();
        
        // Get all jobs for user
        List<JobApplication> allJobs = jobApplicationRepository.findByUserId(user.getId());
        long total = allJobs.size();
        response.setTotalApplications(total);
        
        // Status counts
        Map<String, Long> statusCounts = new HashMap<>();
        long interviews = 0;
        long offers = 0;
        long active = 0;
        
        for (ApplicationStatus status : ApplicationStatus.values()) {
            long count = allJobs.stream().filter(j -> j.getStatus() == status).count();
            statusCounts.put(status.name(), count);
            
            if (status == ApplicationStatus.INTERVIEW || status == ApplicationStatus.HR) {
                interviews += count;
            }
            if (status == ApplicationStatus.OFFERED) {
                offers = count;
            }
            // Active = NOT (OFFERED or REJECTED)
            if (status != ApplicationStatus.OFFERED && status != ApplicationStatus.REJECTED) {
                active += count;
            }
        }
        
        response.setStatusCounts(statusCounts);
        response.setTotalInterviews(interviews);
        response.setTotalOffers(offers);
        response.setActiveApplications(active);
        
        // Offer rate
        double offerRate = total > 0 ? (offers * 100.0 / total) : 0.0;
        response.setOfferRate(offerRate);
        
        // Recent applications (last 5)
        List<JobApplication> recentJobs = jobApplicationRepository.findTop5ByUserIdOrderByCreatedAtDesc(user.getId());
        List<JobApplicationResponse> recentResponses = recentJobs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        response.setRecentApplications(recentResponses);
        
        // Monthly trends (last 6 months)
        List<MonthlyStats> monthlyTrends = calculateMonthlyTrends(allJobs);
        response.setMonthlyTrends(monthlyTrends);
        
        logger.info("Analytics generated for user: {}", username);
        return response;
    }
    
    private List<MonthlyStats> calculateMonthlyTrends(List<JobApplication> jobs) {
        LocalDate now = LocalDate.now();
        List<MonthlyStats> trends = new ArrayList<>();
        
        for (int i = 5; i >= 0; i--) {
            LocalDate monthDate = now.minusMonths(i);
            int month = monthDate.getMonthValue();
            int year = monthDate.getYear();
            
            long applications = jobs.stream()
                .filter(j -> j.getCreatedAt() != null)
                .filter(j -> {
                    LocalDate created = j.getCreatedAt().toLocalDate();
                    return created.getYear() == year && created.getMonthValue() == month;
                })
                .count();
            
            long interviews = jobs.stream()
                .filter(j -> j.getCreatedAt() != null)
                .filter(j -> {
                    LocalDate created = j.getCreatedAt().toLocalDate();
                    return created.getYear() == year && created.getMonthValue() == month;
                })
                .filter(j -> j.getStatus() == ApplicationStatus.INTERVIEW || j.getStatus() == ApplicationStatus.HR)
                .count();
            
            long offers = jobs.stream()
                .filter(j -> j.getCreatedAt() != null)
                .filter(j -> {
                    LocalDate created = j.getCreatedAt().toLocalDate();
                    return created.getYear() == year && created.getMonthValue() == month;
                })
                .filter(j -> j.getStatus() == ApplicationStatus.OFFERED)
                .count();
            
            trends.add(new MonthlyStats(month, year, applications, interviews, offers));
        }
        
        return trends;
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
