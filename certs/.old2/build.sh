#!/bin/sh
set -o errexit

#
if [ ! -d "./target" ]; then
  mkdir ./target
fi

#Generate the CA Key and Certificate
if [ ! -f "./target/ca.key" ]; then
    openssl genpkey -algorithm RSA -out ./target/ca.key
    openssl req -new -x509 -days 1825 -key ./target/ca.key -out ./target/ca.crt -config ./ca.conf
fi

# Generate CA keystores
if [ ! -f "./target/keystore.p12" ]; then
  openssl pkcs12 -export -in ./target/ca.crt -inkey ./target/ca.key -out ./target/keystore.p12 -name ca -password pass:changeit
fi

# Create a truststore and import the CA certificate
if [ ! -f "./target/truststore.jks" ]; then
  keytool -import -file ./target/ca.crt -alias ca -keystore ./target/truststore.jks -storepass changeit -noprompt
fi