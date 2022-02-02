package com.google.storage.v2.samples;

import com.google.api.gax.rpc.ServerStream;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.ReadObjectRequest;
import com.google.storage.v2.ReadObjectResponse;
import com.google.storage.v2.StorageClient;

public class ReadObjectCallableCallReadObjectRequest {

  public static void main(String[] args) throws Exception {
    readObjectCallableCallReadObjectRequest();
  }

  public static void readObjectCallableCallReadObjectRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ReadObjectRequest request =
          ReadObjectRequest.newBuilder()
              .setBucket("bucket-1378203158")
              .setObject("object-1023368385")
              .setGeneration(305703192)
              .setReadOffset(-715377828)
              .setReadLimit(-164298798)
              .setIfGenerationMatch(-1086241088)
              .setIfGenerationNotMatch(1475720404)
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .setReadMask(FieldMask.newBuilder().build())
              .build();
      ServerStream<ReadObjectResponse> stream = storageClient.readObjectCallable().call(request);
      for (ReadObjectResponse response : stream) {
        // Do something when a response is received.
      }
    }
  }
}
