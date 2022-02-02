package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.GetServiceAccountRequest;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.ServiceAccount;
import com.google.storage.v2.StorageClient;

public class GetServiceAccountCallableFutureCallGetServiceAccountRequest {

  public static void main(String[] args) throws Exception {
    getServiceAccountCallableFutureCallGetServiceAccountRequest();
  }

  public static void getServiceAccountCallableFutureCallGetServiceAccountRequest()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetServiceAccountRequest request =
          GetServiceAccountRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<ServiceAccount> future =
          storageClient.getServiceAccountCallable().futureCall(request);
      // Do something.
      ServiceAccount response = future.get();
    }
  }
}
