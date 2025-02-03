#
FROM eclipse-temurin:21.0.4_7-jre-alpine

#
EXPOSE 8800

#
WORKDIR /home

#
COPY /target/gateway-*-SNAPSHOT.jar /home/app.jar

#
CMD ["java", "-jar", "/home/app.jar"]