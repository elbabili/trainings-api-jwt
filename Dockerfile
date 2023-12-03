FROM openjdk:8
EXPOSE 8081
ADD target/api-training.jar api-training.jar
ENTRYPOINT ["java","-jar","/api-training.jar"]