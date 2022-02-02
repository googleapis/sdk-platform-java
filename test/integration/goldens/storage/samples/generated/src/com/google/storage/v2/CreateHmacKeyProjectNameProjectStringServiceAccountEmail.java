package com.google.storage.v2.samples;

import com.google.storage.v2.CreateHmacKeyResponse;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateHmacKeyProjectNameProjectStringServiceAccountEmail {

  public static void main(String[] args) throws Exception {
    createHmacKeyProjectNameProjectStringServiceAccountEmail();
  }

  public static void createHmacKeyProjectNameProjectStringServiceAccountEmail() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ProjectName project = ProjectName.of("[PROJECT]");
      String serviceAccountEmail = "serviceAccountEmail1825953988";
      CreateHmacKeyResponse response = storageClient.createHmacKey(project, serviceAccountEmail);
    }
  }
}
