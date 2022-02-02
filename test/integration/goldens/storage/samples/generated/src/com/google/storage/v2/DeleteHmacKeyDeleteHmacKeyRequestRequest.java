package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.DeleteHmacKeyRequest;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class DeleteHmacKeyDeleteHmacKeyRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteHmacKeyDeleteHmacKeyRequestRequest();
  }

  public static void deleteHmacKeyDeleteHmacKeyRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      DeleteHmacKeyRequest request =
          DeleteHmacKeyRequest.newBuilder()
              .setAccessId("accessId-2146437729")
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      storageClient.deleteHmacKey(request);
    }
  }
}
