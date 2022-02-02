package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.CreateHmacKeyRequest;
import com.google.storage.v2.CreateHmacKeyResponse;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateHmacKeyCallableFutureCallCreateHmacKeyRequest {

  public static void main(String[] args) throws Exception {
    createHmacKeyCallableFutureCallCreateHmacKeyRequest();
  }

  public static void createHmacKeyCallableFutureCallCreateHmacKeyRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      CreateHmacKeyRequest request =
          CreateHmacKeyRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setServiceAccountEmail("serviceAccountEmail1825953988")
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<CreateHmacKeyResponse> future =
          storageClient.createHmacKeyCallable().futureCall(request);
      // Do something.
      CreateHmacKeyResponse response = future.get();
    }
  }
}
