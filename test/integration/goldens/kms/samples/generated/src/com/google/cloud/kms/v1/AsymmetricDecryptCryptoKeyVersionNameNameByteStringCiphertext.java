package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.AsymmetricDecryptResponse;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

public class AsymmetricDecryptCryptoKeyVersionNameNameByteStringCiphertext {

  public static void main(String[] args) throws Exception {
    asymmetricDecryptCryptoKeyVersionNameNameByteStringCiphertext();
  }

  public static void asymmetricDecryptCryptoKeyVersionNameNameByteStringCiphertext()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyVersionName name =
          CryptoKeyVersionName.of(
              "[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]", "[CRYPTO_KEY_VERSION]");
      ByteString ciphertext = ByteString.EMPTY;
      AsymmetricDecryptResponse response =
          keyManagementServiceClient.asymmetricDecrypt(name, ciphertext);
    }
  }
}
