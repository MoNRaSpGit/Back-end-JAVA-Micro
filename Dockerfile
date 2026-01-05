# ===== Build stage: compila el jar dentro de Docker =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -DskipTests clean package

# ===== Run stage: ejecuta el jar =====
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
