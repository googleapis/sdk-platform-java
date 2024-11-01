# Generating certificates and keys for testing mTLS-S2A

Create root CA

```
openssl req -x509 -sha256 -days 7305 -newkey rsa:2048 -keyout root_key.pem -out
root_cert.pem
```

Generate private key

```
openssl genrsa -out client_key.pem 2048
```

Generate CSR (set Common Name to localhost, leave all
other fields blank)

```
openssl req -key client_key.pem -new -out client.csr -config config.cnf
```

Sign CSR for client

```
openssl x509 -req -CA root_cert.pem -CAkey root_key.pem -in client.csr -out client_cert.pem -days 7305
```
