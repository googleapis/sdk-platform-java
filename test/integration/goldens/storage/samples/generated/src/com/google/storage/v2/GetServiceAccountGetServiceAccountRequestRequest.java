package com.google.storage.v2.samples;

import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.GetServiceAccountRequest;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.ServiceAccount;
import com.google.storage.v2.StorageClient;

public class GetServiceAccountGetServiceAccountRequestRequest {

  public static void main(String[] args) throws Exception {
    getServiceAccountGetServiceAccountRequestRequest();
  }

  public static void getServiceAccountGetServiceAccountRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetServiceAccountRequest request =
          GetServiceAccountRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ServiceAccount response = storageClient.getServiceAccount(request);
    }
  }
}
