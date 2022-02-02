package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicySearchResult;
import com.google.cloud.asset.v1.SearchAllIamPoliciesRequest;
import java.util.ArrayList;

public class SearchAllIamPoliciesSearchAllIamPoliciesRequestIterateAll {

  public static void main(String[] args) throws Exception {
    searchAllIamPoliciesSearchAllIamPoliciesRequestIterateAll();
  }

  public static void searchAllIamPoliciesSearchAllIamPoliciesRequestIterateAll() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      SearchAllIamPoliciesRequest request =
          SearchAllIamPoliciesRequest.newBuilder()
              .setScope("scope109264468")
              .setQuery("query107944136")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .addAllAssetTypes(new ArrayList<String>())
              .setOrderBy("orderBy-1207110587")
              .build();
      for (IamPolicySearchResult element :
          assetServiceClient.searchAllIamPolicies(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
