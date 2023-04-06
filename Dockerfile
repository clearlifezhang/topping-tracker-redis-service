FROM openjdk:17-slim
WORKDIR /app
COPY target/topping-tracker-redis-service-0.0.1-SNAPSHOT.jar /app/data-process-redis-service.jar
EXPOSE 6060
CMD ["java", "-jar", "/app/data-process-redis-service.jar"]

