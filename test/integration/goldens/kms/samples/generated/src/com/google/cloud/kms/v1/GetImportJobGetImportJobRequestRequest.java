package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.GetImportJobRequest;
import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.ImportJobName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetImportJobGetImportJobRequestRequest {

  public static void main(String[] args) throws Exception {
    getImportJobGetImportJobRequestRequest();
  }

  public static void getImportJobGetImportJobRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetImportJobRequest request =
          GetImportJobRequest.newBuilder()
              .setName(
                  ImportJobName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[IMPORT_JOB]")
                      .toString())
              .build();
      ImportJob response = keyManagementServiceClient.getImportJob(request);
    }
  }
}
