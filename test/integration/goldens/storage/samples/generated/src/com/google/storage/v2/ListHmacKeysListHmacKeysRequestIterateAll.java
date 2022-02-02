package com.google.storage.v2.samples;

import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ListHmacKeysRequest;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListHmacKeysListHmacKeysRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listHmacKeysListHmacKeysRequestIterateAll();
  }

  public static void listHmacKeysListHmacKeysRequestIterateAll() throws Exception {
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
      for (HmacKeyMetadata element : storageClient.listHmacKeys(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
