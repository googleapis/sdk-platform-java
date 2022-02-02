package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class CreateImportJobStringParentStringImportJobIdImportJobImportJob {

  public static void main(String[] args) throws Exception {
    createImportJobStringParentStringImportJobIdImportJobImportJob();
  }

  public static void createImportJobStringParentStringImportJobIdImportJobImportJob()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String parent = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString();
      String importJobId = "importJobId1449444627";
      ImportJob importJob = ImportJob.newBuilder().build();
      ImportJob response =
          keyManagementServiceClient.createImportJob(parent, importJobId, importJob);
    }
  }
}
