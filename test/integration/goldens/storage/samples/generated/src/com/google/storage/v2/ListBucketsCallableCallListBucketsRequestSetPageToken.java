package com.google.storage.v2.samples;

import com.google.common.base.Strings;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.Bucket;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.ListBucketsRequest;
import com.google.storage.v2.ListBucketsResponse;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListBucketsCallableCallListBucketsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listBucketsCallableCallListBucketsRequestSetPageToken();
  }

  public static void listBucketsCallableCallListBucketsRequestSetPageToken() throws Exception {
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
      while (true) {
        ListBucketsResponse response = storageClient.listBucketsCallable().call(request);
        for (Bucket element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
