package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CreateImportJobRequest;
import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class CreateImportJobCreateImportJobRequestRequest {

  public static void main(String[] args) throws Exception {
    createImportJobCreateImportJobRequestRequest();
  }

  public static void createImportJobCreateImportJobRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CreateImportJobRequest request =
          CreateImportJobRequest.newBuilder()
              .setParent(KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString())
              .setImportJobId("importJobId1449444627")
              .setImportJob(ImportJob.newBuilder().build())
              .build();
      ImportJob response = keyManagementServiceClient.createImportJob(request);
    }
  }
}
