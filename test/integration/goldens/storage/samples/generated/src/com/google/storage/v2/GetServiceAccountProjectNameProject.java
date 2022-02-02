package com.google.storage.v2.samples;

import com.google.storage.v2.ProjectName;
import com.google.storage.v2.ServiceAccount;
import com.google.storage.v2.StorageClient;

public class GetServiceAccountProjectNameProject {

  public static void main(String[] args) throws Exception {
    getServiceAccountProjectNameProject();
  }

  public static void getServiceAccountProjectNameProject() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ProjectName project = ProjectName.of("[PROJECT]");
      ServiceAccount response = storageClient.getServiceAccount(project);
    }
  }
}
