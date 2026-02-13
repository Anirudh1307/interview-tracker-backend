package com.interviewtracker.dto;

import com.interviewtracker.enums.RoundResult;
import java.time.LocalDate;

public class InterviewRoundRequest {
    private String roundName;
    private LocalDate date;
    private String feedback;
    private RoundResult result;

    public String getRoundName() { return roundName; }
    public void setRoundName(String roundName) { this.roundName = roundName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public RoundResult getResult() { return result; }
    public void setResult(RoundResult result) { this.result = result; }
}
