FROM openjdk:8-jre
MAINTAINER liron.li liron.li@outlook.com

COPY target/api-gateway-zuul-1.0-SNAPSHOT.jar /api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]