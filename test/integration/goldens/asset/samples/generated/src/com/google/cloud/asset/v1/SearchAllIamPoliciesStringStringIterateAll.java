package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicySearchResult;

public class SearchAllIamPoliciesStringStringIterateAll {

  public static void main(String[] args) throws Exception {
    searchAllIamPoliciesStringStringIterateAll();
  }

  public static void searchAllIamPoliciesStringStringIterateAll() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      String scope = "scope109264468";
      String query = "query107944136";
      for (IamPolicySearchResult element :
          assetServiceClient.searchAllIamPolicies(scope, query).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
