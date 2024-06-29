# Build
FROM gradle:8.8.0-jdk17-alpine AS build
WORKDIR /usr/app
COPY build.gradle .
COPY src ./src
RUN gradle build

# Package
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /usr/app
EXPOSE 8080
ENV ARTIFACT_NAME=management-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app
COPY --from=build $APP_HOME/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/usr/app/app.jar"]