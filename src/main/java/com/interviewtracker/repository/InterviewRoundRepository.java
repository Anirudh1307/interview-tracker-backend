package com.interviewtracker.repository;

import com.interviewtracker.entity.InterviewRound;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InterviewRoundRepository extends JpaRepository<InterviewRound, Long> {
    List<InterviewRound> findByJobApplicationId(Long jobApplicationId);
}
