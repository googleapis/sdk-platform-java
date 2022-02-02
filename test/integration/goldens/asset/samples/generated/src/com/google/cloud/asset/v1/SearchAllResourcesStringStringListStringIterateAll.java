package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ResourceSearchResult;
import java.util.ArrayList;
import java.util.List;

public class SearchAllResourcesStringStringListStringIterateAll {

  public static void main(String[] args) throws Exception {
    searchAllResourcesStringStringListStringIterateAll();
  }

  public static void searchAllResourcesStringStringListStringIterateAll() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      String scope = "scope109264468";
      String query = "query107944136";
      List<String> assetTypes = new ArrayList<>();
      for (ResourceSearchResult element :
          assetServiceClient.searchAllResources(scope, query, assetTypes).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
