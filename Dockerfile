FROM --platform=linux/arm64 eclipse-temurin:21-jdk AS builder
LABEL authors="yeong"
ARG JAR_FILE=event-service.jar

WORKDIR /app

ARG GITHUB_ACTOR
ARG GITHUB_TOKEN

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

ARG ARTIFACT_NAME=event-service
ARG VERSION=0.0.1-SNAPSHOT
ARG FINAL_JAR_FILE=${ARTIFACT_NAME}-${VERSION}.jar

RUN mv target/${FINAL_JAR_FILE} /app/app.jar


FROM eclipse-temurin:21-jre-alpine

EXPOSE 8085

COPY --from=builder /app/app.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]