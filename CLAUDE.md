# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.5.4 application written in Java 17. The project uses Maven as the build tool and follows standard Spring Boot conventions.

**Package Structure:**
- Main package: `kz.slamkulgroup.kyndebackendapp`
- Main application class: `KyndeBackendAppApplication.java`
- Standard Maven directory layout with `src/main/java` and `src/test/java`

## Development Commands

**Build and Run:**
```bash
# Build the project
./mvnw clean compile

# Run the application
./mvnw spring-boot:run

# Package the application
./mvnw clean package

# Build and run tests
./mvnw clean test

# Run a specific test
./mvnw test -Dtest=KyndeBackendAppApplicationTests

# Clean build artifacts
./mvnw clean
```

**Maven Wrapper:**
- Use `./mvnw` (Unix/macOS) or `mvnw.cmd` (Windows) instead of `mvn`
- The Maven wrapper ensures consistent Maven version across environments

## Architecture Notes

- **Framework:** Spring Boot 3.5.4 with Java 17
- **Build Tool:** Maven with Spring Boot Maven Plugin
- **Application Type:** Standard Spring Boot application with `@SpringBootApplication` annotation
- **Configuration:** Application properties in `src/main/resources/application.properties`
- **Testing:** JUnit 5 with Spring Boot Test framework

## Key Files

- `pom.xml`: Maven configuration with Spring Boot dependencies
- `src/main/java/kz/slamkulgroup/kyndebackendapp/KyndeBackendAppApplication.java`: Main application entry point
- `src/main/resources/application.properties`: Application configuration
- `src/test/java/kz/slamkulgroup/kyndebackendapp/KyndeBackendAppApplicationTests.java`: Basic context loading test