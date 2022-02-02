package com.google.storage.v2.samples;

import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class GetHmacKeyStringAccessIdStringProject {

  public static void main(String[] args) throws Exception {
    getHmacKeyStringAccessIdStringProject();
  }

  public static void getHmacKeyStringAccessIdStringProject() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String accessId = "accessId-2146437729";
      String project = ProjectName.of("[PROJECT]").toString();
      HmacKeyMetadata response = storageClient.getHmacKey(accessId, project);
    }
  }
}
