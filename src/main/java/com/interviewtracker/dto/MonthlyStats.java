package com.interviewtracker.dto;

public class MonthlyStats {
    private int month;
    private int year;
    private long applications;
    private long interviews;
    private long offers;

    public MonthlyStats() {}

    public MonthlyStats(int month, int year, long applications, long interviews, long offers) {
        this.month = month;
        this.year = year;
        this.applications = applications;
        this.interviews = interviews;
        this.offers = offers;
    }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    public long getApplications() { return applications; }
    public void setApplications(long applications) { this.applications = applications; }
    
    public long getInterviews() { return interviews; }
    public void setInterviews(long interviews) { this.interviews = interviews; }
    
    public long getOffers() { return offers; }
    public void setOffers(long offers) { this.offers = offers; }
}
