FROM eclipse-temurin:17-jre-ubi9-minimal
VOLUME /tmp
EXPOSE 8080
ADD target/rest-api-interview-0.0.1-SNAPSHOT.jar rest-api-interview.jar
ENTRYPOINT [ "sh", "-c", "java -jar /rest-api-interview.jar" ]