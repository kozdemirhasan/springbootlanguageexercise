#FROM maven:3.5.2-jdk-8-alpine AS Build
#COPY . /tmp
#WORKDIR /tmp
#RUN mvn clean package -DskipTests=true

#FROM openjdk:8-jdk-alpine AS Run
#COPY --from=Build /tmp/target/helloDocker-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT java -jar /app.jar

FROM openjdk:17-jdk-alpine
COPY target/springbootlanguageexcercise-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
