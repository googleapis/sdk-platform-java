package com.google.storage.v2.samples;

import com.google.storage.v2.ProjectName;
import com.google.storage.v2.ServiceAccount;
import com.google.storage.v2.StorageClient;

public class GetServiceAccountStringProject {

  public static void main(String[] args) throws Exception {
    getServiceAccountStringProject();
  }

  public static void getServiceAccountStringProject() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String project = ProjectName.of("[PROJECT]").toString();
      ServiceAccount response = storageClient.getServiceAccount(project);
    }
  }
}
