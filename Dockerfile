# Backend build stage
FROM maven:3.9.8-eclipse-temurin-17-alpine AS builder
WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder --chown=appuser:appgroup /app/target/task-manager-0.0.1-SNAPSHOT.jar ./app.jar
RUN apk add --no-cache curl
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE 8081
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8081/api/auth/csrf >/dev/null 2>&1 || exit 1
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
