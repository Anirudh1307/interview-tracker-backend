-- Sample Test Data for Interview Tracker

-- Note: Password is 'password123' hashed with BCrypt
-- You should register through the API to get proper hashed passwords

-- Sample Users
INSERT INTO users (username, email, password, created_at) VALUES
('john_doe', 'john@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', CURRENT_TIMESTAMP),
('jane_smith', 'jane@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', CURRENT_TIMESTAMP);

-- Sample Job Applications for john_doe (user_id = 1)
INSERT INTO job_applications (user_id, company_name, role, applied_date, status, created_at, updated_at) VALUES
(1, 'Google', 'Software Engineer', '2024-01-10', 'INTERVIEW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Amazon', 'Backend Developer', '2024-01-12', 'OA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Microsoft', 'Full Stack Developer', '2024-01-15', 'APPLIED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Meta', 'Frontend Engineer', '2024-01-08', 'OFFERED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Apple', 'iOS Developer', '2024-01-05', 'REJECTED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Netflix', 'Senior Software Engineer', '2024-01-18', 'HR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Sample Job Applications for jane_smith (user_id = 2)
INSERT INTO job_applications (user_id, company_name, role, applied_date, status, created_at, updated_at) VALUES
(2, 'Tesla', 'Software Engineer', '2024-01-14', 'INTERVIEW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'SpaceX', 'Backend Engineer', '2024-01-16', 'APPLIED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Sample Interview Rounds for Google application (job_application_id = 1)
INSERT INTO interview_rounds (job_application_id, round_name, date, feedback, result, created_at) VALUES
(1, 'Phone Screen', '2024-01-15', 'Good communication skills and technical knowledge', 'PASS', CURRENT_TIMESTAMP),
(1, 'Technical Round 1', '2024-01-18', 'Strong problem-solving abilities, solved all coding questions', 'PASS', CURRENT_TIMESTAMP),
(1, 'Technical Round 2', '2024-01-20', 'Excellent system design knowledge', 'PASS', CURRENT_TIMESTAMP),
(1, 'Hiring Manager Round', '2024-01-22', 'Waiting for feedback', 'PENDING', CURRENT_TIMESTAMP);

-- Sample Interview Rounds for Amazon application (job_application_id = 2)
INSERT INTO interview_rounds (job_application_id, round_name, date, feedback, result, created_at) VALUES
(2, 'Online Assessment', '2024-01-14', 'Completed all test cases', 'PASS', CURRENT_TIMESTAMP);

-- Sample Interview Rounds for Meta application (job_application_id = 4)
INSERT INTO interview_rounds (job_application_id, round_name, date, feedback, result, created_at) VALUES
(4, 'Phone Screen', '2024-01-10', 'Great technical skills', 'PASS', CURRENT_TIMESTAMP),
(4, 'Onsite Round 1', '2024-01-12', 'Excellent coding and problem solving', 'PASS', CURRENT_TIMESTAMP),
(4, 'Onsite Round 2', '2024-01-12', 'Strong system design', 'PASS', CURRENT_TIMESTAMP),
(4, 'Behavioral Round', '2024-01-12', 'Good cultural fit', 'PASS', CURRENT_TIMESTAMP);

-- Sample Interview Rounds for Apple application (job_application_id = 5)
INSERT INTO interview_rounds (job_application_id, round_name, date, feedback, result, created_at) VALUES
(5, 'Phone Screen', '2024-01-08', 'Did not meet technical requirements', 'FAIL', CURRENT_TIMESTAMP);

-- Sample Interview Rounds for Netflix application (job_application_id = 6)
INSERT INTO interview_rounds (job_application_id, round_name, date, feedback, result, created_at) VALUES
(6, 'Technical Phone Screen', '2024-01-20', 'Good technical knowledge', 'PASS', CURRENT_TIMESTAMP),
(6, 'HR Round', '2024-01-23', 'Discussing compensation', 'PENDING', CURRENT_TIMESTAMP);

-- Sample Interview Rounds for Tesla application (job_application_id = 7)
INSERT INTO interview_rounds (job_application_id, round_name, date, feedback, result, created_at) VALUES
(7, 'Technical Assessment', '2024-01-16', 'Strong coding skills', 'PASS', CURRENT_TIMESTAMP),
(7, 'Team Interview', '2024-01-19', 'Scheduled for next week', 'PENDING', CURRENT_TIMESTAMP);

-- Verify data
SELECT 'Users' as table_name, COUNT(*) as count FROM users
UNION ALL
SELECT 'Job Applications', COUNT(*) FROM job_applications
UNION ALL
SELECT 'Interview Rounds', COUNT(*) FROM interview_rounds;
