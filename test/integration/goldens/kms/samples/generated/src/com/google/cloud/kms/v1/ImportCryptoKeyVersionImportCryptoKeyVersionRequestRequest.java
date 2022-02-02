package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.ImportCryptoKeyVersionRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class ImportCryptoKeyVersionImportCryptoKeyVersionRequestRequest {

  public static void main(String[] args) throws Exception {
    importCryptoKeyVersionImportCryptoKeyVersionRequestRequest();
  }

  public static void importCryptoKeyVersionImportCryptoKeyVersionRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ImportCryptoKeyVersionRequest request =
          ImportCryptoKeyVersionRequest.newBuilder()
              .setParent(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setImportJob("importJob-208547368")
              .build();
      CryptoKeyVersion response = keyManagementServiceClient.importCryptoKeyVersion(request);
    }
  }
}
