#!/bin/sh
set -o errexit

#
if [ ! -d "./target" ]; then
  mkdir ./target
fi

#Generate the CA Key and Certificate
if [ ! -f "./target/ca.key" ]; then
    openssl genpkey -algorithm RSA -out ./target/ca.key
    openssl req -new -x509 -days 1825 -key ./target/ca.key -out ./target/ca.crt -extensions v3_req -config ./ca.conf
fi

# Create a truststore and import the CA certificate
if [ ! -f "./target/truststore.jks" ]; then
  keytool -import -file ./target/ca.crt -alias ca -keystore ./target/truststore.jks -storepass changeit -noprompt
fi

# function to generate TLS certificates and keystore
generate_tls_certificates() {
  local file=$1
  local name=${file%.conf}
  local target_dir="./target/$name"
  local days=365

  #
  echo "building: $name"

  #
  if [ ! -d "$target_dir" ]; then
    mkdir -p "$target_dir"

    # generate private key
    openssl genpkey -algorithm RSA -out "$target_dir/tls.key"

    # generate certificate signing request (CSR)
    openssl req -new -key "$target_dir/tls.key" -out "$target_dir/tls.csr" -extensions v3_req -config $file

    # generate self-signed certificate
    openssl x509 -req -in "$target_dir/tls.csr" -CA "./target/ca.crt" -CAkey "./target/ca.key" -CAcreateserial -out "$target_dir/tls.crt" -days "$days" -extfile $file -extensions v3_req

    # remove CSR
    rm -f "$target_dir/tls.csr"

    # generate keystore
    openssl pkcs12 -export -out "$target_dir/keystore.p12" -name "$name" -inkey "$target_dir/tls.key" -in "$target_dir/tls.crt" -certfile "./target/ca.crt" -passout "pass:changeit"
  fi
}

#
for conf_file in *.conf; do
  if [ "$conf_file" != "ca.conf" ]; then
    generate_tls_certificates $conf_file
  fi
done

# inject all tls.crt file in truststore
find . -type f -name "tls.crt" | while read crt_file; do
  dirname=$(dirname "$crt_file")
  basename=$(basename "$dirname")
  keytool -import -file $crt_file -alias $basename -keystore ./target/truststore.jks -storepass changeit -noprompt
done
