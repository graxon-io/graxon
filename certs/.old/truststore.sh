#!/bin/sh
set -o errexit

# Create a truststore and import the CA certificate
keytool -import -file ./target/ca.crt -alias ca -keystore ./target/truststore.jks -storepass changeit -noprompt