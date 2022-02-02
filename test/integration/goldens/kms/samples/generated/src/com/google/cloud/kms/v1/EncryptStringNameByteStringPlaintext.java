package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.EncryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

public class EncryptStringNameByteStringPlaintext {

  public static void main(String[] args) throws Exception {
    encryptStringNameByteStringPlaintext();
  }

  public static void encryptStringNameByteStringPlaintext() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      ByteString plaintext = ByteString.EMPTY;
      EncryptResponse response = keyManagementServiceClient.encrypt(name, plaintext);
    }
  }
}
