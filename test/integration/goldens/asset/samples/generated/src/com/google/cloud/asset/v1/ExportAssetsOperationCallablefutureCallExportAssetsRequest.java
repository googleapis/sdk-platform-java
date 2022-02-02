package com.google.cloud.asset.v1.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.ContentType;
import com.google.cloud.asset.v1.ExportAssetsRequest;
import com.google.cloud.asset.v1.ExportAssetsResponse;
import com.google.cloud.asset.v1.FeedName;
import com.google.cloud.asset.v1.OutputConfig;
import com.google.protobuf.Timestamp;
import java.util.ArrayList;

public class ExportAssetsOperationCallablefutureCallExportAssetsRequest {

  public static void main(String[] args) throws Exception {
    exportAssetsOperationCallablefutureCallExportAssetsRequest();
  }

  public static void exportAssetsOperationCallablefutureCallExportAssetsRequest() throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      ExportAssetsRequest request =
          ExportAssetsRequest.newBuilder()
              .setParent(FeedName.ofProjectFeedName("[PROJECT]", "[FEED]").toString())
              .setReadTime(Timestamp.newBuilder().build())
              .addAllAssetTypes(new ArrayList<String>())
              .setContentType(ContentType.forNumber(0))
              .setOutputConfig(OutputConfig.newBuilder().build())
              .addAllRelationshipTypes(new ArrayList<String>())
              .build();
      OperationFuture<ExportAssetsResponse, ExportAssetsRequest> future =
          assetServiceClient.exportAssetsOperationCallable().futureCall(request);
      // Do something.
      ExportAssetsResponse response = future.get();
    }
  }
}
