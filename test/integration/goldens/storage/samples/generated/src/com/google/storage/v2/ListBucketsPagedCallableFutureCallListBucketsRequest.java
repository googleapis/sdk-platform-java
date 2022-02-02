package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.Bucket;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.ListBucketsRequest;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListBucketsPagedCallableFutureCallListBucketsRequest {

  public static void main(String[] args) throws Exception {
    listBucketsPagedCallableFutureCallListBucketsRequest();
  }

  public static void listBucketsPagedCallableFutureCallListBucketsRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ListBucketsRequest request =
          ListBucketsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setPrefix("prefix-980110702")
              .setReadMask(FieldMask.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<Bucket> future = storageClient.listBucketsPagedCallable().futureCall(request);
      // Do something.
      for (Bucket element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
