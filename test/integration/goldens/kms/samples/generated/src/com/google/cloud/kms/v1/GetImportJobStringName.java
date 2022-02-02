package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.ImportJobName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetImportJobStringName {

  public static void main(String[] args) throws Exception {
    getImportJobStringName();
  }

  public static void getImportJobStringName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name =
          ImportJobName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[IMPORT_JOB]").toString();
      ImportJob response = keyManagementServiceClient.getImportJob(name);
    }
  }
}
