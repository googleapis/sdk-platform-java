package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class ListImportJobsKeyRingNameIterateAll {

  public static void main(String[] args) throws Exception {
    listImportJobsKeyRingNameIterateAll();
  }

  public static void listImportJobsKeyRingNameIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      KeyRingName parent = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]");
      for (ImportJob element : keyManagementServiceClient.listImportJobs(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
