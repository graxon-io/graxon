#
FROM eclipse-temurin:21.0.4_7-jre-alpine

#
EXPOSE 8800
EXPOSE 8802

#
WORKDIR /home

#
COPY infrastructure/target/graxon-core-*-SNAPSHOT.jar /home/app.jar

#
CMD ["java", "-jar", "/home/app.jar"]