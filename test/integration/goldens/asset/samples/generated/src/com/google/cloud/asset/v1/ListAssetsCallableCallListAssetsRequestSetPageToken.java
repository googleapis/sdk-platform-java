package com.google.cloud.asset.v1.samples;

import com.google.cloud.asset.v1.Asset;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ContentType;
import com.google.cloud.asset.v1.FeedName;
import com.google.cloud.asset.v1.ListAssetsRequest;
import com.google.cloud.asset.v1.ListAssetsResponse;
import com.google.common.base.Strings;
import com.google.protobuf.Timestamp;
import java.util.ArrayList;

public class ListAssetsCallableCallListAssetsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listAssetsCallableCallListAssetsRequestSetPageToken();
  }

  public static void listAssetsCallableCallListAssetsRequestSetPageToken() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      ListAssetsRequest request =
          ListAssetsRequest.newBuilder()
              .setParent(FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString())
              .setReadTime(Timestamp.newBuilder().build())
              .addAllAssetTypes(new ArrayList<String>())
              .setContentType(ContentType.forNumber(0))
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .addAllRelationshipTypes(new ArrayList<String>())
              .build();
      while (true) {
        ListAssetsResponse response = assetServiceClient.listAssetsCallable().call(request);
        for (Asset element : response.getResponsesList()) {
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
