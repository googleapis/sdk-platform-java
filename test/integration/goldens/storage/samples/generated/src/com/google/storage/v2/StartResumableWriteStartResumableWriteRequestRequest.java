package com.google.storage.v2.samples;

import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.StartResumableWriteRequest;
import com.google.storage.v2.StartResumableWriteResponse;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.WriteObjectSpec;

public class StartResumableWriteStartResumableWriteRequestRequest {

  public static void main(String[] args) throws Exception {
    startResumableWriteStartResumableWriteRequestRequest();
  }

  public static void startResumableWriteStartResumableWriteRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      StartResumableWriteRequest request =
          StartResumableWriteRequest.newBuilder()
              .setWriteObjectSpec(WriteObjectSpec.newBuilder().build())
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      StartResumableWriteResponse response = storageClient.startResumableWrite(request);
    }
  }
}
