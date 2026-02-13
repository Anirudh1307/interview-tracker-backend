# Interview Tracker - Backend API

> A RESTful API for tracking job applications and interview rounds with JWT authentication

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Overview

Interview Tracker Backend is a production-ready Spring Boot REST API that enables users to manage their job application process. It provides secure authentication, CRUD operations for job applications and interview rounds, and analytics for tracking application success rates.

**Live API**: https://interview-tracker-backend-tkf4.onrender.com

**Frontend Repository**: [interview-tracker-frontend](https://github.com/Anirudh1307/interview-tracker-frontend)

---

## 🚀 Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 17** | Programming language |
| **Spring Boot 3.2.0** | Application framework |
| **Spring Security** | Authentication & authorization |
| **Spring Data JPA** | Database ORM |
| **PostgreSQL** | Relational database |
| **JWT (jjwt 0.12.3)** | Token-based authentication |
| **BCrypt** | Password hashing |
| **Maven** | Build tool |
| **Docker** | Containerization |

---

## 🏗️ Architecture

The application follows a layered architecture pattern:

```
┌─────────────────────────────────────────┐
│          REST Controllers               │  ← HTTP Endpoints
├─────────────────────────────────────────┤
│          Service Layer                  │  ← Business Logic
├─────────────────────────────────────────┤
│          Repository Layer               │  ← Data Access (JPA)
├─────────────────────────────────────────┤
│          PostgreSQL Database            │  ← Data Storage
└─────────────────────────────────────────┘
```

### Layer Responsibilities

**Controllers** (`controller/`)
- Handle HTTP requests/responses
- Validate input using `@Valid`
- Return standardized `ApiResponse<T>` wrapper

**Services** (`service/`)
- Implement business logic
- Handle transactions
- Perform data validation
- Log important operations

**Repositories** (`repository/`)
- Extend `JpaRepository`
- Define custom queries
- Handle database operations

**Entities** (`entity/`)
- Map to database tables
- Define relationships (OneToMany, ManyToOne)
- Use JPA annotations

---

## 🔐 Security Implementation

### JWT Authentication Flow

```
1. User registers → Password hashed with BCrypt → Stored in DB
2. User logs in → Credentials validated → JWT token generated
3. Client stores token → Sends in Authorization header
4. Server validates token → Extracts user info → Grants access
```

### Security Features

- ✅ **Stateless Authentication**: JWT tokens (no server-side sessions)
- ✅ **Password Hashing**: BCrypt with automatic salt generation
- ✅ **CORS Protection**: Whitelist-based origin validation
- ✅ **SQL Injection Prevention**: JPA parameterized queries
- ✅ **Token Expiration**: Configurable token lifetime (default: 24 hours)
- ✅ **Protected Routes**: All endpoints except `/api/auth/**` require authentication

### Configuration

```java
// SecurityConfig.java
- CSRF disabled (stateless API)
- CORS enabled with environment-based origins
- Session management: STATELESS
- JWT filter before UsernamePasswordAuthenticationFilter
```

---

## 📊 Database Schema

### Tables

**users**
```sql
id          BIGINT PRIMARY KEY
username    VARCHAR(50) UNIQUE NOT NULL
email       VARCHAR(100) UNIQUE NOT NULL
password    VARCHAR(255) NOT NULL (BCrypt hashed)
created_at  TIMESTAMP
```

**job_applications**
```sql
id            BIGINT PRIMARY KEY
user_id       BIGINT FOREIGN KEY → users(id)
company_name  VARCHAR(100) NOT NULL
role          VARCHAR(100) NOT NULL
applied_date  DATE NOT NULL
status        VARCHAR(20) NOT NULL
created_at    TIMESTAMP
updated_at    TIMESTAMP
```

**interview_rounds**
```sql
id                  BIGINT PRIMARY KEY
job_application_id  BIGINT FOREIGN KEY → job_applications(id)
round_name          VARCHAR(100) NOT NULL
date                DATE
feedback            TEXT
result              VARCHAR(20)
created_at          TIMESTAMP
```

### Relationships

- User → Job Applications: **One-to-Many**
- Job Application → Interview Rounds: **One-to-Many**
- Cascade delete: Deleting a job deletes its interview rounds

---

## 📡 API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | ❌ |
| POST | `/api/auth/login` | Login user | ❌ |

### Job Applications

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/jobs` | Get all user's jobs | ✅ |
| GET | `/api/jobs/{id}` | Get job by ID | ✅ |
| POST | `/api/jobs` | Create new job | ✅ |
| PUT | `/api/jobs/{id}` | Update job | ✅ |
| DELETE | `/api/jobs/{id}` | Delete job | ✅ |

### Interview Rounds

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/jobs/{jobId}/rounds` | Get all rounds for job | ✅ |
| POST | `/api/jobs/{jobId}/rounds` | Create new round | ✅ |
| PUT | `/api/jobs/{jobId}/rounds/{roundId}` | Update round | ✅ |
| DELETE | `/api/jobs/{jobId}/rounds/{roundId}` | Delete round | ✅ |

### Analytics

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/analytics` | Get dashboard statistics | ✅ |

### Request/Response Examples

**Register User**
```json
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123!"
}

Response: 200 OK
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "john_doe",
    "email": "john@example.com"
  }
}
```

**Create Job Application**
```json
POST /api/jobs
Authorization: Bearer <token>
Content-Type: application/json

{
  "companyName": "Google",
  "role": "Software Engineer",
  "appliedDate": "2024-02-13",
  "status": "APPLIED"
}

Response: 200 OK
{
  "success": true,
  "message": "Job application created successfully",
  "data": {
    "id": 1,
    "companyName": "Google",
    "role": "Software Engineer",
    "appliedDate": "2024-02-13",
    "status": "APPLIED",
    "createdAt": "2024-02-13T10:30:00"
  }
}
```

**Get Analytics**
```json
GET /api/analytics
Authorization: Bearer <token>

Response: 200 OK
{
  "success": true,
  "message": "Analytics retrieved successfully",
  "data": {
    "totalApplications": 25,
    "totalInterviews": 12,
    "totalOffers": 3,
    "offerRate": 12.0,
    "activeApplications": 15,
    "statusCounts": {
      "APPLIED": 8,
      "OA": 4,
      "INTERVIEW": 3,
      "OFFERED": 3,
      "REJECTED": 7
    },
    "recentApplications": [...],
    "monthlyTrends": [...]
  }
}
```

---

## 🛠️ Local Development

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

### Setup Steps

1. **Clone the repository**
```bash
git clone https://github.com/Anirudh1307/interview-tracker-backend.git
cd interview-tracker-backend
```

2. **Configure database**
```bash
# Create PostgreSQL database
createdb interview_tracker

# Or using psql
psql -U postgres
CREATE DATABASE interview_tracker;
```

3. **Set environment variables**
```bash
# Copy example file
cp .env.example .env

# Edit .env with your values
DATABASE_URL=jdbc:postgresql://localhost:5432/interview_tracker
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your_password
JWT_SECRET=your-secret-key-here
```

4. **Build and run**
```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

5. **Verify**
```bash
# API should be running on http://localhost:8080
curl http://localhost:8080/api/auth/login
```

---

## 🚢 Production Deployment (Render + Neon)

### Step 1: Database Setup (Neon)

1. Create account at [neon.tech](https://neon.tech)
2. Create new project: `interview-tracker`
3. Copy connection string (includes `?sslmode=require`)
4. Save: `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`

### Step 2: Backend Deployment (Render)

1. Push code to GitHub
2. Create account at [render.com](https://render.com)
3. New Web Service → Connect repository
4. Configure:
   - **Runtime**: Docker
   - **Branch**: main
   - **Instance Type**: Free

5. Add environment variables:
```
JDBC_DATABASE_URL=jdbc:postgresql://host.neon.tech/db?sslmode=require
DATABASE_USERNAME=neondb_owner
DATABASE_PASSWORD=your_password
JWT_SECRET=<generate with: openssl rand -base64 32>
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app
SPRING_PROFILES_ACTIVE=prod
```

6. Deploy → Wait 5-10 minutes → Copy backend URL

### Step 3: Update CORS

After deploying frontend, update `CORS_ALLOWED_ORIGINS` in Render with your Vercel URL.

---

## 📁 Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/interviewtracker/
│   │   │   ├── config/              # Security, JWT, CORS config
│   │   │   ├── controller/          # REST endpoints
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── enums/               # Status enums
│   │   │   ├── exception/           # Global exception handling
│   │   │   ├── repository/          # JPA repositories
│   │   │   ├── service/             # Business logic
│   │   │   └── util/                # Utility classes
│   │   └── resources/
│   │       ├── application.properties           # Dev config
│   │       └── application-prod.properties      # Prod config
│   └── test/                        # Unit tests
├── .dockerignore
├── .env.example                     # Environment template
├── .gitignore
├── Dockerfile                       # Multi-stage Docker build
├── pom.xml                          # Maven dependencies
├── README.md                        # This file
└── render.yaml                      # Render deployment config
```

---

## ⚙️ Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `JDBC_DATABASE_URL` | PostgreSQL connection string | `jdbc:postgresql://host/db?sslmode=require` |
| `DATABASE_USERNAME` | Database username | `neondb_owner` |
| `DATABASE_PASSWORD` | Database password | `your_password` |
| `JWT_SECRET` | Secret key for JWT signing | `<256-bit random string>` |
| `JWT_EXPIRATION` | Token expiration time (ms) | `86400000` (24 hours) |
| `CORS_ALLOWED_ORIGINS` | Allowed frontend origins | `https://app.vercel.app` |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `prod` |
| `PORT` | Server port (auto-set by Render) | `8080` |

**Generate JWT Secret:**
```bash
openssl rand -base64 32
```

---

## 🎯 Production Best Practices

### Implemented

- ✅ Environment-based configuration (no hardcoded secrets)
- ✅ Docker containerization for consistent deployments
- ✅ Multi-stage Docker build for smaller image size
- ✅ Global exception handling with standardized responses
- ✅ Input validation using Bean Validation annotations
- ✅ Logging for authentication and critical operations
- ✅ CORS protection with whitelist
- ✅ SQL injection prevention via JPA
- ✅ Password hashing with BCrypt
- ✅ Stateless JWT authentication
- ✅ Error messages sanitized (no stack traces in production)

### Configuration Highlights

**application-prod.properties**
```properties
# Disable SQL logging in production
spring.jpa.show-sql=false

# Hide stack traces from API responses
server.error.include-stacktrace=never

# Set appropriate log levels
logging.level.root=INFO
```

**Dockerfile**
```dockerfile
# Multi-stage build
FROM maven:3.9-eclipse-temurin-17 AS build
# ... build stage ...

FROM eclipse-temurin:17-jre-alpine
# ... runtime stage with memory limits ...
```

---

## 🔮 Future Improvements

- [ ] Add unit and integration tests (JUnit 5 + Mockito)
- [ ] Implement email verification for registration
- [ ] Add password reset functionality
- [ ] Implement rate limiting for API endpoints
- [ ] Add Redis caching for analytics
- [ ] Implement pagination for job listings
- [ ] Add file upload for resumes/documents
- [ ] Create admin dashboard for user management
- [ ] Add API versioning (v1, v2)
- [ ] Implement WebSocket for real-time notifications
- [ ] Add Swagger/OpenAPI documentation
- [ ] Implement audit logging for compliance

---

## 📝 License

This project is licensed under the MIT License.

---

## 👤 Author

**Anirudh**

- GitHub: [@Anirudh1307](https://github.com/Anirudh1307)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/your-profile)

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📞 Support

For support, email your-email@example.com or open an issue in the repository.

---

**⭐ Star this repository if you found it helpful!**
