package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicySearchResult;
import com.google.cloud.asset.v1.SearchAllIamPoliciesRequest;
import java.util.ArrayList;

public class SearchAllIamPoliciesPagedCallableFutureCallSearchAllIamPoliciesRequest {

  public static void main(String[] args) throws Exception {
    searchAllIamPoliciesPagedCallableFutureCallSearchAllIamPoliciesRequest();
  }

  public static void searchAllIamPoliciesPagedCallableFutureCallSearchAllIamPoliciesRequest()
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
      ApiFuture<IamPolicySearchResult> future =
          assetServiceClient.searchAllIamPoliciesPagedCallable().futureCall(request);
      // Do something.
      for (IamPolicySearchResult element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
