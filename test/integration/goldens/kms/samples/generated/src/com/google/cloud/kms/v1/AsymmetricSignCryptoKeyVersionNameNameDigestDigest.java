package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.AsymmetricSignResponse;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.Digest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class AsymmetricSignCryptoKeyVersionNameNameDigestDigest {

  public static void main(String[] args) throws Exception {
    asymmetricSignCryptoKeyVersionNameNameDigestDigest();
  }

  public static void asymmetricSignCryptoKeyVersionNameNameDigestDigest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyVersionName name =
          CryptoKeyVersionName.of(
              "[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]", "[CRYPTO_KEY_VERSION]");
      Digest digest = Digest.newBuilder().build();
      AsymmetricSignResponse response = keyManagementServiceClient.asymmetricSign(name, digest);
    }
  }
}
