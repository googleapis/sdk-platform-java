package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.QueryWriteStatusRequest;
import com.google.storage.v2.QueryWriteStatusResponse;
import com.google.storage.v2.StorageClient;

public class QueryWriteStatusCallableFutureCallQueryWriteStatusRequest {

  public static void main(String[] args) throws Exception {
    queryWriteStatusCallableFutureCallQueryWriteStatusRequest();
  }

  public static void queryWriteStatusCallableFutureCallQueryWriteStatusRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      QueryWriteStatusRequest request =
          QueryWriteStatusRequest.newBuilder()
              .setUploadId("uploadId1563990780")
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<QueryWriteStatusResponse> future =
          storageClient.queryWriteStatusCallable().futureCall(request);
      // Do something.
      QueryWriteStatusResponse response = future.get();
    }
  }
}
