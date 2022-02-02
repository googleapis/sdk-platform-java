package com.google.cloud.kms.v1.samples;

import com.google.api.resourcenames.ResourceName;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.EncryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

public class EncryptResourceNameNameByteStringPlaintext {

  public static void main(String[] args) throws Exception {
    encryptResourceNameNameByteStringPlaintext();
  }

  public static void encryptResourceNameNameByteStringPlaintext() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ResourceName name = CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      ByteString plaintext = ByteString.EMPTY;
      EncryptResponse response = keyManagementServiceClient.encrypt(name, plaintext);
    }
  }
}
