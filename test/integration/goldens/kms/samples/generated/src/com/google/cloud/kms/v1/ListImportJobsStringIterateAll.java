package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class ListImportJobsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listImportJobsStringIterateAll();
  }

  public static void listImportJobsStringIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String parent = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString();
      for (ImportJob element : keyManagementServiceClient.listImportJobs(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
