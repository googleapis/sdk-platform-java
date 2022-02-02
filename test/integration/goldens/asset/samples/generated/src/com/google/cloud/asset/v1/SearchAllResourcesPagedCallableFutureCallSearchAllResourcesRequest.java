package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ResourceSearchResult;
import com.google.cloud.asset.v1.SearchAllResourcesRequest;
import com.google.protobuf.FieldMask;
import java.util.ArrayList;

public class SearchAllResourcesPagedCallableFutureCallSearchAllResourcesRequest {

  public static void main(String[] args) throws Exception {
    searchAllResourcesPagedCallableFutureCallSearchAllResourcesRequest();
  }

  public static void searchAllResourcesPagedCallableFutureCallSearchAllResourcesRequest()
      throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      SearchAllResourcesRequest request =
          SearchAllResourcesRequest.newBuilder()
              .setScope("scope109264468")
              .setQuery("query107944136")
              .addAllAssetTypes(new ArrayList<String>())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setOrderBy("orderBy-1207110587")
              .setReadMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<ResourceSearchResult> future =
          assetServiceClient.searchAllResourcesPagedCallable().futureCall(request);
      // Do something.
      for (ResourceSearchResult element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
