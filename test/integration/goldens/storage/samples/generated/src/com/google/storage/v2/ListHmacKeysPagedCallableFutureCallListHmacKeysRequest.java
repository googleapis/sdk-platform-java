package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ListHmacKeysRequest;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListHmacKeysPagedCallableFutureCallListHmacKeysRequest {

  public static void main(String[] args) throws Exception {
    listHmacKeysPagedCallableFutureCallListHmacKeysRequest();
  }

  public static void listHmacKeysPagedCallableFutureCallListHmacKeysRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ListHmacKeysRequest request =
          ListHmacKeysRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setServiceAccountEmail("serviceAccountEmail1825953988")
              .setShowDeletedKeys(true)
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<HmacKeyMetadata> future =
          storageClient.listHmacKeysPagedCallable().futureCall(request);
      // Do something.
      for (HmacKeyMetadata element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
