package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.GetObjectRequest;
import com.google.storage.v2.Object;
import com.google.storage.v2.StorageClient;

public class GetObjectCallableFutureCallGetObjectRequest {

  public static void main(String[] args) throws Exception {
    getObjectCallableFutureCallGetObjectRequest();
  }

  public static void getObjectCallableFutureCallGetObjectRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetObjectRequest request =
          GetObjectRequest.newBuilder()
              .setBucket("bucket-1378203158")
              .setObject("object-1023368385")
              .setGeneration(305703192)
              .setIfGenerationMatch(-1086241088)
              .setIfGenerationNotMatch(1475720404)
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .setReadMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Object> future = storageClient.getObjectCallable().futureCall(request);
      // Do something.
      Object response = future.get();
    }
  }
}
