FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]