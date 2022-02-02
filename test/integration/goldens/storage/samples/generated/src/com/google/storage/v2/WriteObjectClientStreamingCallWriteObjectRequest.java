package com.google.storage.v2.samples;

import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.ObjectChecksums;
import com.google.storage.v2.StorageClient;
import com.google.storage.v2.WriteObjectRequest;
import com.google.storage.v2.WriteObjectResponse;

public class WriteObjectClientStreamingCallWriteObjectRequest {

  public static void main(String[] args) throws Exception {
    writeObjectClientStreamingCallWriteObjectRequest();
  }

  public static void writeObjectClientStreamingCallWriteObjectRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ApiStreamObserver<WriteObjectRequest> responseObserver =
          new ApiStreamObserver<WriteObjectRequest>() {
            @Override
            public void onNext(WriteObjectResponse response) {
              // Do something when a response is received.
            }

            @Override
            public void onError(Throwable t) {
              // Add error-handling
            }

            @Override
            public void onCompleted() {
              // Do something when complete.
            }
          };
      ApiStreamObserver<WriteObjectRequest> requestObserver =
          storageClient.writeObject().clientStreamingCall(responseObserver);
      WriteObjectRequest request =
          WriteObjectRequest.newBuilder()
              .setWriteOffset(-1559543565)
              .setObjectChecksums(ObjectChecksums.newBuilder().build())
              .setFinishWrite(true)
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      requestObserver.onNext(request);
    }
  }
}
