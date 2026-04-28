# Project Instructions for GitHub Copilot

This is a Spring Boot 3 Java 17 REST API project.

## Coding Standards

- Always follow SOLID principles
- All services must have JUnit 5 tests
- All REST endpoints must have Swagger/OpenAPI documentation
- Use records for DTOs when possible (Java 17+)
- Handle all exceptions with @ControllerAdvice
- Never expose JPA entities directly in REST responses — use DTOs

## Stack

- Java 17, Spring Boot 3, Spring Data JPA
- H2 (dev), PostgreSQL (prod), Maven
- JUnit 5, Mockito, Swagger/OpenAPI
