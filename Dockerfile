# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src

# Copy pom trước để tận dụng layer cache cho dependencies
COPY pom.xml .
COPY common/pom.xml common/
COPY app/pom.xml app/
COPY server/pom.xml server/
RUN mvn -q -B -ntp dependency:go-offline || true

COPY common common
COPY app app
COPY server server
RUN mvn -q -B -ntp -pl server -am package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/server/target/anni-server.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
