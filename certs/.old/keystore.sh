#!/bin/sh
set -o errexit

# Generate server private key
openssl genpkey -algorithm RSA -out ./target/node.key

# Generate server certificate signing request (CSR)
openssl req -new -key ./target/node.key -out ./target/node.csr -config node.conf

# Generate server certificate by signing that with CA certificate and CA key and using servercsr.conf
openssl x509 -req -days 365 -in ./target/node.csr -CA ./target/ca.crt -CAkey ./target/ca.key -CAcreateserial -out ./target/node.crt -extensions req_ext -extfile node.conf

# Create server keystore, here also we'll use CA cert to ensure "root" certificate chain is present
openssl pkcs12 -export -in ./target/node.crt -inkey ./target/node.key -out ./target/keystore.p12 -name node -CAfile ./target/ca.crt -caname root -chain -password pass:changeit
