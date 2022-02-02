package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.GetImportJobRequest;
import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.ImportJobName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetImportJobCallableFutureCallGetImportJobRequest {

  public static void main(String[] args) throws Exception {
    getImportJobCallableFutureCallGetImportJobRequest();
  }

  public static void getImportJobCallableFutureCallGetImportJobRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetImportJobRequest request =
          GetImportJobRequest.newBuilder()
              .setName(
                  ImportJobName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[IMPORT_JOB]")
                      .toString())
              .build();
      ApiFuture<ImportJob> future =
          keyManagementServiceClient.getImportJobCallable().futureCall(request);
      // Do something.
      ImportJob response = future.get();
    }
  }
}
