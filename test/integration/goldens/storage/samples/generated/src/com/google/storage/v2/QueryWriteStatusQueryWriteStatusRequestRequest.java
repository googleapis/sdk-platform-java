package com.google.storage.v2.samples;

import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.QueryWriteStatusRequest;
import com.google.storage.v2.QueryWriteStatusResponse;
import com.google.storage.v2.StorageClient;

public class QueryWriteStatusQueryWriteStatusRequestRequest {

  public static void main(String[] args) throws Exception {
    queryWriteStatusQueryWriteStatusRequestRequest();
  }

  public static void queryWriteStatusQueryWriteStatusRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      QueryWriteStatusRequest request =
          QueryWriteStatusRequest.newBuilder()
              .setUploadId("uploadId1563990780")
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      QueryWriteStatusResponse response = storageClient.queryWriteStatus(request);
    }
  }
}
