# Interview Tracker Backend API

Interview Tracker Backend is a Spring Boot REST API built to support a production-style job application tracking platform. It provides secure authentication, structured domain logic, and analytics endpoints that power the frontend dashboard. The service is designed for real-world deployment on Render with PostgreSQL hosted on Neon.

## 🚀 Key Highlights

- Production deployment strategy with Render for the API and Neon for PostgreSQL
- Secure JWT authentication with Spring Security and BCrypt password hashing
- Analytics endpoints that power dashboard KPIs and trend visualizations
- Clean layered backend architecture with controllers, services, repositories, and DTOs
- Environment-driven configuration for local development and cloud deployment

## 📌 Project Purpose

The backend exists to give job seekers a secure and scalable way to store application activity, interview rounds, and performance metrics. It turns personal job-search data into a structured system that can support dashboards, progress tracking, and future feature growth.

## ✨ Advanced Features

- Secure API with token-based authentication and protected routes
- Scalable backend architecture with clear separation of concerns
- Analytics endpoints for dashboards and data visualization
- Environment-based deployment configuration for local and production environments
- Validation, standardized responses, and security-focused request handling

## Overview

This API solves the data and security layer of the job-tracking workflow. It manages users, applications, interview rounds, and aggregated metrics in a way that supports both product usability and maintainable engineering structure. The result is a backend that is easy to explain in interviews and practical to deploy.

## Live API

- API Base URL: https://interview-tracker-backend-tkf4.onrender.com

## Tech Stack

| Technology | Purpose | Version |
|------------|---------|---------|
| Java | Primary language | 17 |
| Spring Boot | Application framework | 3.2.0 |
| Spring Security | Authentication and authorization | 3.2.0 |
| Spring Data JPA | Data persistence | 3.2.0 |
| PostgreSQL | Relational database | Managed on Neon |
| JJWT | Token generation and validation | 0.12.3 |
| Maven | Build and dependency management | 3.x |
| Docker | Deployment packaging | Included |

## Architecture

The application follows a classic layered structure that keeps HTTP concerns, business logic, and persistence separate.

```text
controller/   Request handling and API contracts
service/      Business logic and orchestration
repository/   Data access via Spring Data JPA
dto/          Request and response models
entity/       Persistent domain objects
config/       Security, JWT, and CORS configuration
```

## Security Model

- JWT-based stateless authentication
- BCrypt password hashing for stored credentials
- Protected routes for all non-auth endpoints
- CORS configuration for frontend deployment domains
- Validation and exception handling for safer API behavior

## API Surface

### Authentication

- `POST /api/auth/register`
- `POST /api/auth/login`

### Job Applications

- `GET /api/jobs`
- `GET /api/jobs/{id}`
- `POST /api/jobs`
- `PUT /api/jobs/{id}`
- `DELETE /api/jobs/{id}`

### Interview Rounds

- `GET /api/jobs/{jobId}/rounds`
- `POST /api/jobs/{jobId}/rounds`
- `PUT /api/jobs/{jobId}/rounds/{roundId}`
- `DELETE /api/jobs/{jobId}/rounds/{roundId}`

### Analytics

- `GET /api/analytics`

## Data Model

### Core Entities

- `User`: account credentials and ownership of job applications
- `JobApplication`: company, role, status, applied date, and lifecycle metadata
- `InterviewRound`: round name, date, feedback, and result for each application

### Supported Statuses

- `APPLIED`
- `OA`
- `INTERVIEW`
- `HR`
- `OFFERED`
- `REJECTED`

## Local Development

### Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Setup

```bash
git clone https://github.com/Anirudh1307/interview-tracker-backend.git
cd interview-tracker-backend
```

Create a PostgreSQL database named `interview_tracker`, then configure environment values or application properties for your local instance.

Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

The API runs at `http://localhost:8080`.

## Production Deployment

The backend is prepared for Render deployment with Neon PostgreSQL.

### Core Environment Variables

```env
JDBC_DATABASE_URL=jdbc:postgresql://host.neon.tech/db?sslmode=require
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
JWT_SECRET=your_secure_secret
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
SPRING_PROFILES_ACTIVE=prod
```

### Deployment Notes

- `render.yaml` provides deployment configuration
- `Dockerfile` supports container-based deployment
- Production settings are separated in `application-prod.properties`

## Backend Strengths

- Clear layered architecture that is easy to maintain and discuss
- Secure authentication and environment-aware deployment setup
- Practical analytics support for dashboard-driven product features
- Solid portfolio value through real deployment, API design, and cloud database integration

## Related Documentation

- Frontend README: [../frontend/README.md](../frontend/README.md)
- Root project README: [../README.md](../README.md)
