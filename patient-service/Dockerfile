FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
# Download dependencies to cache them in a separate layer until the pom.xml changes
RUN mvn dependency:go-offline -B
COPY src ./src
# create a single jar file with all dependencies included
RUN mvn clean package

FROM eclipse-temurin:21-jdk-jammy AS runner
WORKDIR /app

# Copy the built jar file from the builder stage to the runner stage
COPY --from=builder /app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]
