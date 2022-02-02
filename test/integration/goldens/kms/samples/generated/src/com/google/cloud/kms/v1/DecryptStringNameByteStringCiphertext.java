package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.DecryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

public class DecryptStringNameByteStringCiphertext {

  public static void main(String[] args) throws Exception {
    decryptStringNameByteStringCiphertext();
  }

  public static void decryptStringNameByteStringCiphertext() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      ByteString ciphertext = ByteString.EMPTY;
      DecryptResponse response = keyManagementServiceClient.decrypt(name, ciphertext);
    }
  }
}
