package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.Object;
import com.google.storage.v2.PredefinedObjectAcl;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.UpdateObjectRequest;

public class UpdateObjectCallableFutureCallUpdateObjectRequest {

  public static void main(String[] args) throws Exception {
    updateObjectCallableFutureCallUpdateObjectRequest();
  }

  public static void updateObjectCallableFutureCallUpdateObjectRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      UpdateObjectRequest request =
          UpdateObjectRequest.newBuilder()
              .setObject(Object.newBuilder().build())
              .setIfGenerationMatch(-1086241088)
              .setIfGenerationNotMatch(1475720404)
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setPredefinedAcl(PredefinedObjectAcl.forNumber(0))
              .setUpdateMask(FieldMask.newBuilder().build())
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<Object> future = storageClient.updateObjectCallable().futureCall(request);
      // Do something.
      Object response = future.get();
    }
  }
}
