package com.google.storage.v2.samples;

import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.GetHmacKeyRequest;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class GetHmacKeyGetHmacKeyRequestRequest {

  public static void main(String[] args) throws Exception {
    getHmacKeyGetHmacKeyRequestRequest();
  }

  public static void getHmacKeyGetHmacKeyRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetHmacKeyRequest request =
          GetHmacKeyRequest.newBuilder()
              .setAccessId("accessId-2146437729")
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      HmacKeyMetadata response = storageClient.getHmacKey(request);
    }
  }
}
