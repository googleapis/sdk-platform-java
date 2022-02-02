package com.google.storage.v2.samples;

import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class GetHmacKeyStringAccessIdProjectNameProject {

  public static void main(String[] args) throws Exception {
    getHmacKeyStringAccessIdProjectNameProject();
  }

  public static void getHmacKeyStringAccessIdProjectNameProject() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String accessId = "accessId-2146437729";
      ProjectName project = ProjectName.of("[PROJECT]");
      HmacKeyMetadata response = storageClient.getHmacKey(accessId, project);
    }
  }
}
