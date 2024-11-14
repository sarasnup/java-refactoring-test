# Use a Java runtime as a base image
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY build/libs/java-refactoring-test-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
