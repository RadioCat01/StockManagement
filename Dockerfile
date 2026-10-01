# Stage 1: Build the WAR application
FROM maven:3.9.3-eclipse-temurin-17-alpine AS builder
WORKDIR /app

# Cache Maven dependencies
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

# Copy source and build WAR
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

# Stage 2: Deploy on minimal Tomcat runtime
FROM tomcat:9.0-jre17-temurin-jammy
WORKDIR /usr/local/tomcat

# Remove default webapps to reduce footprint and attack surface
RUN rm -rf webapps/* webapps.dist/*

# Create non-root user for secure execution
RUN groupadd -r tomcat && useradd -r -g tomcat -d /usr/local/tomcat tomcat \
    && chown -R tomcat:tomcat /usr/local/tomcat

# Deploy WAR at root context
COPY --from=builder --chown=tomcat:tomcat /app/target/Stockmgt-2.7.18.war webapps/ROOT.war

USER tomcat:tomcat

EXPOSE 8080

# JVM tuning: container-aware heap sizing for resource efficiency
ENV CATALINA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

CMD ["catalina.sh", "run"]
