package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.Bucket;
import com.google.storage.v2.CreateBucketRequest;
import com.google.storage.v2.PredefinedBucketAcl;
import com.google.storage.v2.PredefinedObjectAcl;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateBucketCallableFutureCallCreateBucketRequest {

  public static void main(String[] args) throws Exception {
    createBucketCallableFutureCallCreateBucketRequest();
  }

  public static void createBucketCallableFutureCallCreateBucketRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      CreateBucketRequest request =
          CreateBucketRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setBucket(Bucket.newBuilder().build())
              .setBucketId("bucketId-1603305307")
              .setPredefinedAcl(PredefinedBucketAcl.forNumber(0))
              .setPredefinedDefaultObjectAcl(PredefinedObjectAcl.forNumber(0))
              .build();
      ApiFuture<Bucket> future = storageClient.createBucketCallable().futureCall(request);
      // Do something.
      Bucket response = future.get();
    }
  }
}
