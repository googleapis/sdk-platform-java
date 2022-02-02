package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class DeleteHmacKeyStringAccessIdProjectNameProject {

  public static void main(String[] args) throws Exception {
    deleteHmacKeyStringAccessIdProjectNameProject();
  }

  public static void deleteHmacKeyStringAccessIdProjectNameProject() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String accessId = "accessId-2146437729";
      ProjectName project = ProjectName.of("[PROJECT]");
      storageClient.deleteHmacKey(accessId, project);
    }
  }
}
