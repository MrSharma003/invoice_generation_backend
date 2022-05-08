FROM openjdk:8-jdk-alpine
ADD target/invoiceWebService.jar invoiceWebService.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "invoiceWebService.jar"]