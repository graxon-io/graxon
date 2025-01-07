#
FROM eclipse-temurin:21.0.4_7-jre-alpine

#
EXPOSE 8801

#
WORKDIR /home

#
COPY /target/admin-*-SNAPSHOT.jar /home/app.jar

#
CMD ["java", "-jar", "/home/app.jar"]