FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/reporting-1.0-SNAPSHOT-spring-boot.jar

WORKDIR /opt/app

COPY ${JAR_FILE} reporting.jar

ENTRYPOINT ["java","-jar","reporting.jar"]
