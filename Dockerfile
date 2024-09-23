FROM amazoncorretto:21

CMD ["./mvnw", "clean", "install"]

COPY target/ai-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]