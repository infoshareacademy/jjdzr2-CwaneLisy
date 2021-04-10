FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=webApp/target/webApp-1.0-SNAPSHOT-spring-boot.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
