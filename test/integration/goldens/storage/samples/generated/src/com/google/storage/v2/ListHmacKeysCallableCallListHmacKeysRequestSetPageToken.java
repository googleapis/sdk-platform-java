package com.google.storage.v2.samples;

import com.google.common.base.Strings;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ListHmacKeysRequest;
import com.google.storage.v2.ListHmacKeysResponse;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListHmacKeysCallableCallListHmacKeysRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listHmacKeysCallableCallListHmacKeysRequestSetPageToken();
  }

  public static void listHmacKeysCallableCallListHmacKeysRequestSetPageToken() throws Exception {
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
      while (true) {
        ListHmacKeysResponse response = storageClient.listHmacKeysCallable().call(request);
        for (HmacKeyMetadata element : response.getResponsesList()) {
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
