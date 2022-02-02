package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.UpdateHmacKeyRequest;

public class UpdateHmacKeyCallableFutureCallUpdateHmacKeyRequest {

  public static void main(String[] args) throws Exception {
    updateHmacKeyCallableFutureCallUpdateHmacKeyRequest();
  }

  public static void updateHmacKeyCallableFutureCallUpdateHmacKeyRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      UpdateHmacKeyRequest request =
          UpdateHmacKeyRequest.newBuilder()
              .setHmacKey(HmacKeyMetadata.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<HmacKeyMetadata> future = storageClient.updateHmacKeyCallable().futureCall(request);
      // Do something.
      HmacKeyMetadata response = future.get();
    }
  }
}
