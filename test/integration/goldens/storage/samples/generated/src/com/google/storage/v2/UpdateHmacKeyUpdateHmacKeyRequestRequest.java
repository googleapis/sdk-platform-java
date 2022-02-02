package com.google.storage.v2.samples;

import com.google.protobuf.FieldMask;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.UpdateHmacKeyRequest;

public class UpdateHmacKeyUpdateHmacKeyRequestRequest {

  public static void main(String[] args) throws Exception {
    updateHmacKeyUpdateHmacKeyRequestRequest();
  }

  public static void updateHmacKeyUpdateHmacKeyRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      UpdateHmacKeyRequest request =
          UpdateHmacKeyRequest.newBuilder()
              .setHmacKey(HmacKeyMetadata.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      HmacKeyMetadata response = storageClient.updateHmacKey(request);
    }
  }
}
