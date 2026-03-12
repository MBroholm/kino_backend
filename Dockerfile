# ---------- Build Stage ----------
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copy only the pom first to leverage Docker layer caching
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Copy the rest of the source
COPY src ./src

# Build the application
RUN mvn -B -q clean package -DskipTests


# ---------- Runtime Stage ----------
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]