FROM openjdk:20-ea-17-slim
ARG JAR_FILE=clients/app-external-api/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]