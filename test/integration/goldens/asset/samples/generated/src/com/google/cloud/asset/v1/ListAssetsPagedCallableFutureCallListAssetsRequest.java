package com.google.cloud.asset.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.asset.v1.Asset;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ContentType;
import com.google.cloud.asset.v1.FeedName;
import com.google.cloud.asset.v1.ListAssetsRequest;
import com.google.protobuf.Timestamp;
import java.util.ArrayList;

public class ListAssetsPagedCallableFutureCallListAssetsRequest {

  public static void main(String[] args) throws Exception {
    listAssetsPagedCallableFutureCallListAssetsRequest();
  }

  public static void listAssetsPagedCallableFutureCallListAssetsRequest() throws Exception {
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
      ApiFuture<Asset> future = assetServiceClient.listAssetsPagedCallable().futureCall(request);
      // Do something.
      for (Asset element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
