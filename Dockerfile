FROM openjdk:8-jdk-alpine
ADD target/invoice-web-service.jar invoice-web-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "invoice-web-service.jar"]