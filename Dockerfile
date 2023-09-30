FROM openjdk:21-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:21-jdk-slim
WORKDIR weather
COPY --from=build target/*.jar weather.jar
ENTRYPOINT ["java", "-jar", "weather.jar"]