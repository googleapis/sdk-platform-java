package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.ImportJobName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetImportJobImportJobNameName {

  public static void main(String[] args) throws Exception {
    getImportJobImportJobNameName();
  }

  public static void getImportJobImportJobNameName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ImportJobName name =
          ImportJobName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[IMPORT_JOB]");
      ImportJob response = keyManagementServiceClient.getImportJob(name);
    }
  }
}
