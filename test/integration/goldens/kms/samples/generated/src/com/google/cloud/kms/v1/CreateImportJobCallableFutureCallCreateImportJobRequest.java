package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CreateImportJobRequest;
import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class CreateImportJobCallableFutureCallCreateImportJobRequest {

  public static void main(String[] args) throws Exception {
    createImportJobCallableFutureCallCreateImportJobRequest();
  }

  public static void createImportJobCallableFutureCallCreateImportJobRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CreateImportJobRequest request =
          CreateImportJobRequest.newBuilder()
              .setParent(KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString())
              .setImportJobId("importJobId1449444627")
              .setImportJob(ImportJob.newBuilder().build())
              .build();
      ApiFuture<ImportJob> future =
          keyManagementServiceClient.createImportJobCallable().futureCall(request);
      // Do something.
      ImportJob response = future.get();
    }
  }
}
