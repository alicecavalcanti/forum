
FROM openjdk:21-slim
WORKDIR /app
COPY /target/forum-0.0.1-SNAPSHOT.jar /app/forum.jar
ENTRYPOINT ["java", "-jar", "forum.jar"]