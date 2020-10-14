FROM openjdk:12-alpine
COPY target/order-service-0.0.1-SNAPSHOT.jar /demo.jar
CMD ["java","-jar","demo.jar"]