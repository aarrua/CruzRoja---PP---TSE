FROM maven:3.9.6-eclipse-temurin-25-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jre-alpine
COPY --from=build /target/practicas-inscripcion-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
