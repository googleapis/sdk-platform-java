package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.ComposeObjectRequest;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.Object;
import com.google.storage.v2.PredefinedObjectAcl;
import com.google.storage.v2.StorageClient;
import java.util.ArrayList;

public class ComposeObjectCallableFutureCallComposeObjectRequest {

  public static void main(String[] args) throws Exception {
    composeObjectCallableFutureCallComposeObjectRequest();
  }

  public static void composeObjectCallableFutureCallComposeObjectRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ComposeObjectRequest request =
          ComposeObjectRequest.newBuilder()
              .setDestination(Object.newBuilder().build())
              .addAllSourceObjects(new ArrayList<ComposeObjectRequest.SourceObject>())
              .setDestinationPredefinedAcl(PredefinedObjectAcl.forNumber(0))
              .setIfGenerationMatch(-1086241088)
              .setIfMetagenerationMatch(1043427781)
              .setKmsKey(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<Object> future = storageClient.composeObjectCallable().futureCall(request);
      // Do something.
      Object response = future.get();
    }
  }
}
