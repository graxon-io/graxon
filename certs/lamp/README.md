## Understanding Certificates and Keys
Before we dive into generating certificates, let’s clarify some terminology:

- **CA** (Certificate Authority) - An entity that issues digital certificates.
- **Certificate** - A digital form of identification, like a passport, for your application.
- **Private Key** - A secret key that is used in conjunction with a public certificate to encrypt and decrypt data.
- **CSR** (Certificate Signing Request) - A request sent from an applicant to a CA to obtain a digital identity certificate.
- **Truststore** - A repository that holds trusted certificates (usually CA certificates).
- **Keystore** - A repository that holds certificates along with their private keys.

# Generating Certificates and Keys
Here’s a simplified process to generate all the necessary files for mTLS:

## Step 1: Generate the CA Certificate

1. Create the CA’s Private Key:
```shell
openssl genrsa -out ca.key 2048
```

2. Self-Sign and Create the CA Certificate:

```shell
openssl req -x509 -new -nodes -key ca.key -sha256 -days 365 -out ca.pem -subj "/C=NL/ST=GLD/L=ARN/O=Lamp/OU=servers/CN=CA"
```
This creates a self-signed CA certificate valid for 365 days.


## Step 2: Generate Server and Client Certificates
1. Generate Private Keys:
For the core: 
```shell
openssl genrsa -out core.key 2048
```

For the node: 
```shell
openssl genrsa -out node.key 2048
```

2. Generate CSRs:

For the server: 
```shell
openssl req -new -key core.key -out core.csr -subj "/C=NL/ST=GLD/L=ARN/O=Lamp/OU=servers/CN=core"
```

For the node: 
```shell
openssl req -new -key node.key -out node.csr -subj "/C=NL/ST=GLD/L=ARN/O=Lamp/OU=servers/CN=node"
```

3. Sign CSRs with the CA Key:

For the core: 
```shell
openssl x509 -req -in core.csr -CA ca.pem -CAkey ca.key -CAcreateserial -out core.crt -days 365
```

For the node: 
```shell
openssl x509 -req -in node.csr -CA ca.pem -CAkey ca.key -CAcreateserial -out node.crt -days 365
```

## Step 3: Create PKCS#12 Keystores
Convert Certificates and Keys to PKCS#12 Format:
For the core: 
```shell
openssl pkcs12 -export -out core.p12 -name "core" -inkey core.key -in core.crt -certfile ca.pem -passout pass:changeit
```

For the node: 
```shell
openssl pkcs12 -export -out node.p12 -name "node" -inkey node.key -in node.crt -certfile ca.pem -passout pass:changeit
```

## Step 4: Create the Truststore
Import the CA Certificate into a PKCS#12 Truststore:
```shell
keytool -import -file ca.pem -alias "ca" -keystore truststore.p12 -storetype PKCS12 -storepass changeit -noprompt
```