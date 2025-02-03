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

# Create a truststore and import the CA certificate
if [ ! -f "./target/truststore.jks" ]; then
  keytool -import -file ./target/ca.crt -alias ca -keystore ./target/truststore.jks -storepass changeit -noprompt
fi

#
if [ ! -d "./target/core" ]; then
  mkdir ./target/core
  cd ./target/core
  openssl genpkey -algorithm RSA -out tls.key
  openssl req -new -key tls.key -out tls.csr -config ../../core.conf
  openssl x509 -req -in tls.csr -CA ../ca.crt -CAkey ../ca.key -CAcreateserial -out tls.crt -days 365
  rm -f tls.csr
  openssl pkcs12 -export -out keystore.p12 -name "core" -inkey tls.key -in tls.crt -certfile ../ca.crt -passout pass:changeit
  cd ../../
fi

#
if [ ! -d "./target/gateway" ]; then
  mkdir ./target/gateway
  cd ./target/gateway
  openssl genpkey -algorithm RSA -out tls.key
  openssl req -new -key tls.key -out tls.csr -config ../../gateway.conf
  openssl x509 -req -in tls.csr -CA ../ca.crt -CAkey ../ca.key -CAcreateserial -out tls.crt -days 365
  rm -f tls.csr
  openssl pkcs12 -export -out keystore.p12 -name "gateway" -inkey tls.key -in tls.crt -certfile ../ca.crt -passout pass:changeit
  cd ../../
fi

