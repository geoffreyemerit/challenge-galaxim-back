FROM maven:3.8.3-openjdk-17 AS MAVEN_BUILD
MAINTAINER PLB
COPY pom.xml /build/
WORKDIR /build/
RUN mvn dependency:go-offline
COPY src /build/src/
RUN mvn -Dmaven.test.skip  package



FROM openjdk:17-oracle
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/challenge-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "challenge-0.0.1-SNAPSHOT.jar"]
