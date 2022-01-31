FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY ./wait-for-it.sh .
EXPOSE 8080
CMD ["java","-jar","/app.jar"]
