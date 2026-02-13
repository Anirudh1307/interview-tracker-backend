package com.interviewtracker.dto;

import java.util.List;
import java.util.Map;

public class AnalyticsResponse {
    private long totalApplications;
    private long totalInterviews;
    private long totalOffers;
    private long activeApplications;
    private double offerRate;
    private Map<String, Long> statusCounts;
    private List<JobApplicationResponse> recentApplications;
    private List<MonthlyStats> monthlyTrends;

    public AnalyticsResponse() {}

    public long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }
    
    public long getTotalInterviews() { return totalInterviews; }
    public void setTotalInterviews(long totalInterviews) { this.totalInterviews = totalInterviews; }
    
    public long getTotalOffers() { return totalOffers; }
    public void setTotalOffers(long totalOffers) { this.totalOffers = totalOffers; }
    
    public long getActiveApplications() { return activeApplications; }
    public void setActiveApplications(long activeApplications) { this.activeApplications = activeApplications; }
    
    public double getOfferRate() { return offerRate; }
    public void setOfferRate(double offerRate) { this.offerRate = offerRate; }
    
    public Map<String, Long> getStatusCounts() { return statusCounts; }
    public void setStatusCounts(Map<String, Long> statusCounts) { this.statusCounts = statusCounts; }
    
    public List<JobApplicationResponse> getRecentApplications() { return recentApplications; }
    public void setRecentApplications(List<JobApplicationResponse> recentApplications) { this.recentApplications = recentApplications; }
    
    public List<MonthlyStats> getMonthlyTrends() { return monthlyTrends; }
    public void setMonthlyTrends(List<MonthlyStats> monthlyTrends) { this.monthlyTrends = monthlyTrends; }
}
