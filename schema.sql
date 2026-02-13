-- Create Users Table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Job Applications Table
CREATE TABLE job_applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    applied_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Interview Rounds Table
CREATE TABLE interview_rounds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_application_id BIGINT NOT NULL,
    round_name VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    feedback TEXT,
    result VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_application_id) REFERENCES job_applications(id) ON DELETE CASCADE
);

-- Create Indexes
CREATE INDEX idx_job_applications_user_id ON job_applications(user_id);
CREATE INDEX idx_interview_rounds_job_id ON interview_rounds(job_application_id);
