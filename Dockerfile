# Paso 1: Compilar la aplicación con Maven usando Java 25
FROM maven:3-eclipse-temurin-25 AS build
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Correr la aplicación con el entorno de ejecución de Java 25
FROM eclipse-temurin:25-jre-alpine
COPY --from=build /target/practicas-inscripcion-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]