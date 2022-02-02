package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.GetHmacKeyRequest;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class GetHmacKeyCallableFutureCallGetHmacKeyRequest {

  public static void main(String[] args) throws Exception {
    getHmacKeyCallableFutureCallGetHmacKeyRequest();
  }

  public static void getHmacKeyCallableFutureCallGetHmacKeyRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetHmacKeyRequest request =
          GetHmacKeyRequest.newBuilder()
              .setAccessId("accessId-2146437729")
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<HmacKeyMetadata> future = storageClient.getHmacKeyCallable().futureCall(request);
      // Do something.
      HmacKeyMetadata response = future.get();
    }
  }
}
