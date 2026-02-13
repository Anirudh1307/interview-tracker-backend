package com.interviewtracker.repository;

import com.interviewtracker.entity.JobApplication;
import com.interviewtracker.entity.User;
import com.interviewtracker.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
    long countByUserId(Long userId);
    long countByUserIdAndStatus(Long userId, ApplicationStatus status);
    boolean existsByUserAndCompanyNameAndRole(User user, String companyName, String role);
    List<JobApplication> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);
}
