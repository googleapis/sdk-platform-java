package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ResourceSearchResult;
import com.google.cloud.asset.v1.SearchAllResourcesRequest;
import com.google.protobuf.FieldMask;
import java.util.ArrayList;

public class SearchAllResourcesSearchAllResourcesRequestIterateAll {

  public static void main(String[] args) throws Exception {
    searchAllResourcesSearchAllResourcesRequestIterateAll();
  }

  public static void searchAllResourcesSearchAllResourcesRequestIterateAll() throws Exception {
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
      for (ResourceSearchResult element :
          assetServiceClient.searchAllResources(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
