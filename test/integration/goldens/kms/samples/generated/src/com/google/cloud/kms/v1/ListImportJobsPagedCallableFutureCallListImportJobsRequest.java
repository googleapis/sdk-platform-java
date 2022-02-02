package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;
import com.google.cloud.kms.v1.ListImportJobsRequest;

public class ListImportJobsPagedCallableFutureCallListImportJobsRequest {

  public static void main(String[] args) throws Exception {
    listImportJobsPagedCallableFutureCallListImportJobsRequest();
  }

  public static void listImportJobsPagedCallableFutureCallListImportJobsRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      ListImportJobsRequest request =
          ListImportJobsRequest.newBuilder()
              .setParent(KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setFilter("filter-1274492040")
              .setOrderBy("orderBy-1207110587")
              .build();
      ApiFuture<ImportJob> future =
          keyManagementServiceClient.listImportJobsPagedCallable().futureCall(request);
      // Do something.
      for (ImportJob element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
