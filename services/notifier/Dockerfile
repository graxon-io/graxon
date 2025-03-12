#
FROM eclipse-temurin:21.0.4_7-jre-alpine

#
EXPOSE 8800
EXPOSE 8810

#
WORKDIR /home

#
COPY infrastructure/target/graxon-notifier-*-SNAPSHOT.jar /home/app.jar

#
CMD ["java", "-jar", "/home/app.jar"]