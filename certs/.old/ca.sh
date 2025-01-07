#!/bin/sh
set -o errexit

#Generate the CA Key and Certificate
openssl genpkey -algorithm RSA -out ./target/ca.key
openssl req -new -x509 -days 1825 -key ./target/ca.key -out ./target/ca.crt -config ./ca.conf