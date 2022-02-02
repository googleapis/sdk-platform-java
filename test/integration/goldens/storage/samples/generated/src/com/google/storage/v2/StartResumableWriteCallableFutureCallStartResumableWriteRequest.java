package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.StartResumableWriteRequest;
import com.google.storage.v2.StartResumableWriteResponse;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.WriteObjectSpec;

public class StartResumableWriteCallableFutureCallStartResumableWriteRequest {

  public static void main(String[] args) throws Exception {
    startResumableWriteCallableFutureCallStartResumableWriteRequest();
  }

  public static void startResumableWriteCallableFutureCallStartResumableWriteRequest()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      StartResumableWriteRequest request =
          StartResumableWriteRequest.newBuilder()
              .setWriteObjectSpec(WriteObjectSpec.newBuilder().build())
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<StartResumableWriteResponse> future =
          storageClient.startResumableWriteCallable().futureCall(request);
      // Do something.
      StartResumableWriteResponse response = future.get();
    }
  }
}
