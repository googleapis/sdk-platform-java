package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class CreateImportJobKeyRingNameParentStringImportJobIdImportJobImportJob {

  public static void main(String[] args) throws Exception {
    createImportJobKeyRingNameParentStringImportJobIdImportJobImportJob();
  }

  public static void createImportJobKeyRingNameParentStringImportJobIdImportJobImportJob()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      KeyRingName parent = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]");
      String importJobId = "importJobId1449444627";
      ImportJob importJob = ImportJob.newBuilder().build();
      ImportJob response =
          keyManagementServiceClient.createImportJob(parent, importJobId, importJob);
    }
  }
}
