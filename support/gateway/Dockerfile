#
FROM eclipse-temurin:21.0.4_7-jre-alpine

#
EXPOSE 8800

# Copy the CA certificate to the container
COPY cluster-ca.crt /usr/local/share/ca-certificates/cluster-ca.crt

# Update the CA certificates
RUN update-ca-certificates

# Import the CA certificate into the Java truststore
RUN keytool -importcert -noprompt -trustcacerts -alias cluster-ca -file /usr/local/share/ca-certificates/cluster-ca.crt -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit

#
WORKDIR /home

#
COPY /target/gateway-*-SNAPSHOT.jar /home/app.jar

#
CMD ["java", "-jar", "/home/app.jar"]