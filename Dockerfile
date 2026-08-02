# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Gradle 관련 파일을 먼저 복사해 의존성 레이어 캐시 활용
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

# 나머지 소스 복사
COPY src src

# 실행 가능한 Spring Boot JAR 생성
# 테스트까지 실행하려면 -x test를 제거
RUN ./gradlew clean bootJar -x test --no-daemon


# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# root가 아닌 사용자로 실행
RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=builder /app/build/libs/*.jar app.jar

RUN chown spring:spring /app/app.jar

USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]