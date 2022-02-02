package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicySearchResult;
import com.google.cloud.asset.v1.SearchAllIamPoliciesRequest;
import com.google.cloud.asset.v1.SearchAllIamPoliciesResponse;
import com.google.common.base.Strings;
import java.util.ArrayList;

public class SearchAllIamPoliciesCallableCallSearchAllIamPoliciesRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    searchAllIamPoliciesCallableCallSearchAllIamPoliciesRequestSetPageToken();
  }

  public static void searchAllIamPoliciesCallableCallSearchAllIamPoliciesRequestSetPageToken()
      throws Exception {
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
      while (true) {
        SearchAllIamPoliciesResponse response =
            assetServiceClient.searchAllIamPoliciesCallable().call(request);
        for (IamPolicySearchResult element : response.getResponsesList()) {
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
