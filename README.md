# Kynde Backend App

A production-ready Spring Boot 3.x backend application for a habit-tracking mobile app. This API provides comprehensive habit management, task tracking, streak calculation, and push notification features with JWT authentication.

## 🚀 Features

- **User Management**: JWT-based authentication with email/phone validation
- **Habit Management**: CRUD operations with custom scheduling (daily, weekly, custom days)
- **Task Tracking**: Automatic task generation and completion tracking
- **Streak System**: Automatic streak calculation with avatar state progression
- **Push Notifications**: Firebase Cloud Messaging integration
- **Statistics**: Comprehensive user statistics and progress tracking
- **Production Ready**: Docker containerization, database migrations, comprehensive error handling

## 🏗️ Architecture

### Tech Stack
- **Framework**: Spring Boot 3.5.4
- **Language**: Java 17
- **Database**: PostgreSQL (with H2 for development)
- **Cache**: Redis
- **Authentication**: JWT with Spring Security
- **Documentation**: OpenAPI 3 / Swagger UI
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

### Project Structure
```
src/main/java/kz/slamkulgroup/kyndebackendapp/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── enums/          # Enum definitions
├── exception/      # Exception handling
├── mapper/         # MapStruct mappers
├── repository/     # Data repositories
├── scheduler/      # Scheduled tasks
├── security/       # Security configuration
└── service/        # Business logic
```

## 🛠️ Setup & Installation

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose (for containerized setup)
- PostgreSQL (for local development)

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd KyndeBackendApp
   ```

2. **Set up PostgreSQL database**
   ```sql
   CREATE DATABASE kynde_db;
   CREATE USER kynde_user WITH PASSWORD 'kynde_password';
   GRANT ALL PRIVILEGES ON DATABASE kynde_db TO kynde_user;
   ```

3. **Configure Firebase (Optional)**
   - Download Firebase service account JSON file
   - Place it as `src/main/resources/firebase-service-account.json`

4. **Run the application**
   ```bash
   # Development mode (with H2 database)
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   
   # Production mode (with PostgreSQL)
   ./mvnw spring-boot:run
   ```

### Docker Setup

1. **Using Docker Compose (Recommended)**
   ```bash
   # Start all services
   docker-compose up -d
   
   # View logs
   docker-compose logs -f app
   
   # Stop all services
   docker-compose down
   ```

2. **Build Docker image manually**
   ```bash
   docker build -t kynde-backend .
   docker run -p 8080:8080 kynde-backend
   ```

## 📚 API Documentation

### Swagger UI
Once the application is running, access the interactive API documentation at:
- Local: http://localhost:8080/swagger-ui/index.html
- Docker: http://localhost:8080/swagger-ui/index.html

### Main Endpoints

#### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

#### User Management
- `GET /api/users/me` - Get current user profile
- `GET /api/users/stats` - Get user statistics
- `POST /api/users/fcm-token` - Update FCM token

#### Habit Management
- `GET /api/habits` - Get user habits (paginated)
- `POST /api/habits` - Create new habit
- `PUT /api/habits/{id}` - Update habit
- `DELETE /api/habits/{id}` - Delete habit

#### Task Management
- `GET /api/tasks` - Get user tasks (paginated)
- `GET /api/tasks/date/{date}` - Get tasks for specific date
- `PATCH /api/tasks/{id}/complete` - Mark task as completed
- `PATCH /api/tasks/{id}/uncomplete` - Mark task as not completed

#### Health Check
- `GET /actuator/health` - Application health status

## 🔒 Security

- **JWT Authentication**: Stateless authentication with configurable expiration
- **Password Encryption**: BCrypt hashing for secure password storage
- **Rate Limiting**: Nginx-based rate limiting for auth endpoints
- **Input Validation**: Comprehensive validation with custom error responses
- **Security Headers**: CSRF protection, XSS protection, secure cookies

## 📊 Database Schema

The application uses Flyway for database migrations. Key entities:

- **Users**: User accounts with email/phone authentication
- **Habits**: User habits with flexible scheduling configuration
- **Tasks**: Auto-generated tasks based on habit schedules
- **Streaks**: User streak tracking and statistics
- **NotificationSettings**: Push notification preferences

## 🔄 Schedulers

The application includes several background schedulers:

1. **TaskGeneratorScheduler** (00:01 daily): Generates new tasks based on habit schedules
2. **StreakCalculatorScheduler** (23:59 daily): Updates user streaks and avatar states
3. **NotificationDispatcherScheduler** (every 30 minutes): Sends pending push notifications

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw test jacoco:report

# Run integration tests only
./mvnw test -Dtest="*IntegrationTest"
```

## 🚀 Deployment

### Environment Variables

For production deployment, configure these environment variables:

```bash
# Database
DB_HOST=your-postgres-host
DB_PORT=5432
DB_NAME=kynde_prod
DB_USERNAME=your-db-user
DB_PASSWORD=your-secure-password

# Redis
REDIS_HOST=your-redis-host
REDIS_PORT=6379
REDIS_PASSWORD=your-redis-password

# JWT
JWT_SECRET=your-super-secure-jwt-secret-key-at-least-64-characters-long
JWT_EXPIRATION=86400000

# Firebase
FIREBASE_KEY_PATH=firebase-service-account.json

# Server
PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

### Production Deployment with Docker

1. **Build and push image**
   ```bash
   docker build -t your-registry/kynde-backend:latest .
   docker push your-registry/kynde-backend:latest
   ```

2. **Deploy with production compose**
   ```bash
   docker-compose -f docker-compose.yml --profile production up -d
   ```

## 📈 Monitoring

- **Health Checks**: Built-in Spring Actuator health endpoints
- **Metrics**: Application metrics via Actuator
- **Logging**: Structured logging with configurable levels
- **Database Monitoring**: HikariCP connection pool metrics

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Email: support@slamkulgroup.kz
- Website: https://www.slamkulgroup.kz

## 🔄 Version History

- **v1.0.0** - Initial release with full habit tracking functionality
- More versions coming soon...

---

Built with ❤️ by [Slamkul Group](https://www.slamkulgroup.kz)