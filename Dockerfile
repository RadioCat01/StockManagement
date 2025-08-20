FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:9.0.108-jre17-temurin-noble
RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/Stockmgt-2.7.18.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
## Set JVM options to reduce memory usage
#ENV JAVA_OPTS="-Xmx128m -Xms64m -XX:MaxMetaspaceSize=32m -XX:ReservedCodeCacheSize=32m -Xss256k"

