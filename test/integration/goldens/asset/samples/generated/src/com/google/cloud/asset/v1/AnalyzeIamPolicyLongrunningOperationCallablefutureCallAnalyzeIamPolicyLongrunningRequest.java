package com.google.cloud.asset.v1.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.asset.v1.AnalyzeIamPolicyLongrunningMetadata;
import com.google.cloud.asset.v1.AnalyzeIamPolicyLongrunningRequest;
import com.google.cloud.asset.v1.AnalyzeIamPolicyLongrunningResponse;
import com.google.cloud.asset.v1.AssetServiceClient;
import com.google.cloud.asset.v1.IamPolicyAnalysisOutputConfig;
import com.google.cloud.asset.v1.IamPolicyAnalysisQuery;

public
class AnalyzeIamPolicyLongrunningOperationCallablefutureCallAnalyzeIamPolicyLongrunningRequest {

  public static void main(String[] args) throws Exception {
    analyzeIamPolicyLongrunningOperationCallablefutureCallAnalyzeIamPolicyLongrunningRequest();
  }

  public static void
      analyzeIamPolicyLongrunningOperationCallablefutureCallAnalyzeIamPolicyLongrunningRequest()
          throws Exception {
    try (AssetServiceClient assetServiceClient = AssetServiceClient.create()) {
      AnalyzeIamPolicyLongrunningRequest request =
          AnalyzeIamPolicyLongrunningRequest.newBuilder()
              .setAnalysisQuery(IamPolicyAnalysisQuery.newBuilder().build())
              .setOutputConfig(IamPolicyAnalysisOutputConfig.newBuilder().build())
              .build();
      OperationFuture<AnalyzeIamPolicyLongrunningResponse, AnalyzeIamPolicyLongrunningMetadata>
          future =
              assetServiceClient.analyzeIamPolicyLongrunningOperationCallable().futureCall(request);
      // Do something.
      AnalyzeIamPolicyLongrunningResponse response = future.get();
    }
  }
}
