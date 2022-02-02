package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.ImportCryptoKeyVersionRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class ImportCryptoKeyVersionCallableFutureCallImportCryptoKeyVersionRequest {

  public static void main(String[] args) throws Exception {
    importCryptoKeyVersionCallableFutureCallImportCryptoKeyVersionRequest();
  }

  public static void importCryptoKeyVersionCallableFutureCallImportCryptoKeyVersionRequest()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ImportCryptoKeyVersionRequest request =
          ImportCryptoKeyVersionRequest.newBuilder()
              .setParent(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setImportJob("importJob-208547368")
              .build();
      ApiFuture<CryptoKeyVersion> future =
          keyManagementServiceClient.importCryptoKeyVersionCallable().futureCall(request);
      // Do something.
      CryptoKeyVersion response = future.get();
    }
  }
}
