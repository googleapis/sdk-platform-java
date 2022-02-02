package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.Empty;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.DeleteHmacKeyRequest;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class DeleteHmacKeyCallableFutureCallDeleteHmacKeyRequest {

  public static void main(String[] args) throws Exception {
    deleteHmacKeyCallableFutureCallDeleteHmacKeyRequest();
  }

  public static void deleteHmacKeyCallableFutureCallDeleteHmacKeyRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      DeleteHmacKeyRequest request =
          DeleteHmacKeyRequest.newBuilder()
              .setAccessId("accessId-2146437729")
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<Empty> future = storageClient.deleteHmacKeyCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
