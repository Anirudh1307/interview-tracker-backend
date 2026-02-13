package com.interviewtracker.dto;

import com.interviewtracker.enums.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class JobApplicationRequest {
    @NotBlank(message = "Company name is required")
    private String companyName;
    
    @NotBlank(message = "Role is required")
    private String role;
    
    @NotNull(message = "Applied date is required")
    private LocalDate appliedDate;
    
    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
}
