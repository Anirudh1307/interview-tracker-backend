package com.interviewtracker.service;

import com.interviewtracker.dto.InterviewRoundRequest;
import com.interviewtracker.dto.InterviewRoundResponse;
import com.interviewtracker.entity.InterviewRound;
import com.interviewtracker.entity.JobApplication;
import com.interviewtracker.exception.ResourceNotFoundException;
import com.interviewtracker.repository.InterviewRoundRepository;
import com.interviewtracker.repository.JobApplicationRepository;
import com.interviewtracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewRoundService {

    private final InterviewRoundRepository interviewRoundRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    public InterviewRoundService(InterviewRoundRepository interviewRoundRepository, 
                                JobApplicationRepository jobApplicationRepository, 
                                UserRepository userRepository) {
        this.interviewRoundRepository = interviewRoundRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.userRepository = userRepository;
    }

    public InterviewRoundResponse create(String username, Long jobId, InterviewRoundRequest request) {
        JobApplication job = findJobByIdAndUsername(jobId, username);

        InterviewRound round = new InterviewRound();
        round.setJobApplication(job);
        round.setRoundName(request.getRoundName());
        round.setDate(request.getDate());
        round.setFeedback(request.getFeedback());
        round.setResult(request.getResult());

        return toResponse(interviewRoundRepository.save(round));
    }

    public List<InterviewRoundResponse> getByJobId(String username, Long jobId) {
        findJobByIdAndUsername(jobId, username);
        return interviewRoundRepository.findByJobApplicationId(jobId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public InterviewRoundResponse update(String username, Long jobId, Long roundId, InterviewRoundRequest request) {
        findJobByIdAndUsername(jobId, username);
        InterviewRound round = interviewRoundRepository.findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found"));

        round.setRoundName(request.getRoundName());
        round.setDate(request.getDate());
        round.setFeedback(request.getFeedback());
        round.setResult(request.getResult());

        return toResponse(interviewRoundRepository.save(round));
    }

    public void delete(String username, Long jobId, Long roundId) {
        findJobByIdAndUsername(jobId, username);
        InterviewRound round = interviewRoundRepository.findById(roundId)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found"));
        interviewRoundRepository.delete(round);
    }

    private JobApplication findJobByIdAndUsername(Long jobId, String username) {
        JobApplication job = jobApplicationRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found"));
        if (!job.getUser().getUsername().equals(username)) {
            throw new ResourceNotFoundException("Job application not found");
        }
        return job;
    }

    private InterviewRoundResponse toResponse(InterviewRound round) {
        InterviewRoundResponse response = new InterviewRoundResponse();
        response.setId(round.getId());
        response.setJobApplicationId(round.getJobApplication().getId());
        response.setRoundName(round.getRoundName());
        response.setDate(round.getDate());
        response.setFeedback(round.getFeedback());
        response.setResult(round.getResult());
        response.setCreatedAt(round.getCreatedAt());
        return response;
    }
}
