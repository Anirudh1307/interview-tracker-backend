package com.interviewtracker.dto;

import com.interviewtracker.enums.RoundResult;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InterviewRoundResponse {
    private Long id;
    private Long jobApplicationId;
    private String roundName;
    private LocalDate date;
    private String feedback;
    private RoundResult result;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getJobApplicationId() { return jobApplicationId; }
    public void setJobApplicationId(Long jobApplicationId) { this.jobApplicationId = jobApplicationId; }
    public String getRoundName() { return roundName; }
    public void setRoundName(String roundName) { this.roundName = roundName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public RoundResult getResult() { return result; }
    public void setResult(RoundResult result) { this.result = result; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
