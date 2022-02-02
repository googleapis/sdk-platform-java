package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class DeleteHmacKeyStringAccessIdStringProject {

  public static void main(String[] args) throws Exception {
    deleteHmacKeyStringAccessIdStringProject();
  }

  public static void deleteHmacKeyStringAccessIdStringProject() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String accessId = "accessId-2146437729";
      String project = ProjectName.of("[PROJECT]").toString();
      storageClient.deleteHmacKey(accessId, project);
    }
  }
}
