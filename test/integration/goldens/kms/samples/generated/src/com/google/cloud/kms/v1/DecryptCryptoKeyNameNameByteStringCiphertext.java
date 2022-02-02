package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.DecryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

public class DecryptCryptoKeyNameNameByteStringCiphertext {

  public static void main(String[] args) throws Exception {
    decryptCryptoKeyNameNameByteStringCiphertext();
  }

  public static void decryptCryptoKeyNameNameByteStringCiphertext() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyName name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      ByteString ciphertext = ByteString.EMPTY;
      DecryptResponse response = keyManagementServiceClient.decrypt(name, ciphertext);
    }
  }
}
