/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.asset.v1.samples;

// [START REGION TAG]
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
// [END REGION TAG]