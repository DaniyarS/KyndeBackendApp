# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a production-ready Spring Boot 3.5.4 habit-tracking backend application written in Java 17. The project provides comprehensive habit management, task tracking, streak calculation, and push notification features with JWT authentication.

**Main Technologies:**
- Spring Boot 3.5.4 with Spring Security, Spring Data JPA
- PostgreSQL database with Flyway migrations
- Redis for caching and rate limiting
- Firebase Cloud Messaging for push notifications
- JWT authentication with MapStruct for DTO mapping
- OpenAPI/Swagger documentation
- Docker containerization

**Package Structure:**
```
src/main/java/kz/slamkulgroup/kyndebackendapp/
├── config/          # Configuration classes (Security, Firebase, OpenAPI)
├── controller/      # REST controllers (Auth, User, Habit, Task)
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities (User, Habit, Task, Streak, NotificationSetting)
├── enums/          # Enum definitions (AvatarState, ScheduleType)
├── exception/      # Global exception handling
├── mapper/         # MapStruct mappers
├── repository/     # JPA repositories with custom queries
├── scheduler/      # Background schedulers
├── security/       # JWT security implementation
└── service/        # Business logic services
```

## Development Commands

**Build and Run:**
```bash
# Clean and compile
./mvnw clean compile

# Run with development profile (H2 database)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Run with default profile (PostgreSQL)
./mvnw spring-boot:run

# Package the application
./mvnw clean package

# Run tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=UserServiceTest

# Skip tests during build
./mvnw clean package -DskipTests
```

**Docker Commands:**
```bash
# Start all services (PostgreSQL, Redis, App)
docker-compose up -d

# View application logs
docker-compose logs -f app

# Stop all services
docker-compose down

# Rebuild and restart
docker-compose up -d --build

# Start with production profile
docker-compose --profile production up -d
```

**Database Management:**
```bash
# Run Flyway migrations manually
./mvnw flyway:migrate

# Check migration status
./mvnw flyway:info

# Clean database (development only)
./mvnw flyway:clean
```

## Environment Profiles

- **dev**: H2 in-memory database, detailed logging, all actuator endpoints
- **docker**: PostgreSQL with Docker Compose configuration
- **staging**: PostgreSQL with environment variables, moderate logging
- **prod**: PostgreSQL with production optimizations, minimal logging

## Key Endpoints

**Authentication:**
- POST `/api/auth/register` - User registration
- POST `/api/auth/login` - User login

**User Management:**
- GET `/api/users/me` - Get current user
- GET `/api/users/stats` - Get user statistics
- POST `/api/users/fcm-token` - Update FCM token

**Habit Management:**
- GET `/api/habits` - Get habits (paginated)
- POST `/api/habits` - Create habit
- PUT `/api/habits/{id}` - Update habit
- DELETE `/api/habits/{id}` - Delete habit

**Task Management:**
- GET `/api/tasks` - Get tasks (paginated)
- GET `/api/tasks/date/{date}` - Get tasks for date
- PATCH `/api/tasks/{id}/complete` - Complete task

**Documentation:**
- GET `/swagger-ui/index.html` - Swagger UI
- GET `/actuator/health` - Health check

## Important Configuration

**Database:** Uses PostgreSQL with HikariCP connection pooling. Flyway handles schema migrations.

**Security:** JWT-based authentication with bcrypt password hashing. All endpoints except `/api/auth/**` require authentication.

**Schedulers:** 
- Task generation: Daily at 00:01
- Streak calculation: Daily at 23:59  
- Notifications: Every 30 minutes

**Firebase:** Optional FCM integration for push notifications. Requires `firebase-service-account.json` in resources.

## Testing Strategy

- Unit tests for services and repositories
- Integration tests with Testcontainers
- Security tests for authentication
- Repository tests with H2 database

## Common Development Tasks

**Adding new endpoints:** Create controller, service, and repository layers following existing patterns.

**Database changes:** Create new Flyway migration in `src/main/resources/db/migration/`

**Adding new entities:** Create entity, repository, service, DTO, and mapper following the established structure.

**Environment setup:** Use appropriate profile (`dev` for local development, `docker` for containerized development).