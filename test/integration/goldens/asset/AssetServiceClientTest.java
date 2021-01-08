/*
 * Copyright 2020 Google LLC
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

package com.google.cloud.asset.v1beta1;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.testing.LocalChannelProvider;
import com.google.api.gax.grpc.testing.MockGrpcService;
import com.google.api.gax.grpc.testing.MockServiceHelper;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.api.gax.rpc.StatusCode;
import com.google.longrunning.Operation;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.annotation.Generated;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Generated("by gapic-generator-java")
public class AssetServiceClientTest {
  private static MockServiceHelper mockServiceHelper;
  private AssetServiceClient client;
  private LocalChannelProvider channelProvider;
  private static MockAssetService mockAssetService;

  @BeforeClass
  public static void startStaticServer() {
    mockAssetService = new MockAssetService();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(), Arrays.<MockGrpcService>asList(mockAssetService));
    mockServiceHelper.start();
  }

  @AfterClass
  public static void stopServer() {
    mockServiceHelper.stop();
  }

  @Before
  public void setUp() throws IOException {
    mockServiceHelper.reset();
    channelProvider = mockServiceHelper.createChannelProvider();
    AssetServiceSettings settings =
        AssetServiceSettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = AssetServiceClient.create(settings);
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }

  @Test
  public void exportAssetsTest() throws Exception {
    ExportAssetsResponse expectedResponse =
        ExportAssetsResponse.newBuilder()
            .setReadTime(Timestamp.newBuilder().build())
            .setOutputConfig(OutputConfig.newBuilder().build())
            .build();
    Operation resultOperation =
        Operation.newBuilder()
            .setName("exportAssetsTest")
            .setDone(true)
            .setResponse(Any.pack(expectedResponse))
            .build();
    mockAssetService.addResponse(resultOperation);

    ExportAssetsRequest request =
        ExportAssetsRequest.newBuilder()
            .setParent("ExportAssetsRequest-846449128".toString())
            .setReadTime(Timestamp.newBuilder().build())
            .addAllAssetTypes(new ArrayList<String>())
            .setOutputConfig(OutputConfig.newBuilder().build())
            .build();

    ExportAssetsResponse actualResponse = client.exportAssetsAsync(request).get();
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockAssetService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    ExportAssetsRequest actualRequest = ((ExportAssetsRequest) actualRequests.get(0));

    Assert.assertEquals(request.getParent(), actualRequest.getParent());
    Assert.assertEquals(request.getReadTime(), actualRequest.getReadTime());
    Assert.assertEquals(request.getAssetTypesList(), actualRequest.getAssetTypesList());
    Assert.assertEquals(request.getContentType(), actualRequest.getContentType());
    Assert.assertEquals(request.getOutputConfig(), actualRequest.getOutputConfig());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void exportAssetsExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockAssetService.addException(exception);

    try {
      ExportAssetsRequest request =
          ExportAssetsRequest.newBuilder()
              .setParent("ExportAssetsRequest-846449128".toString())
              .setReadTime(Timestamp.newBuilder().build())
              .addAllAssetTypes(new ArrayList<String>())
              .setOutputConfig(OutputConfig.newBuilder().build())
              .build();
      client.exportAssetsAsync(request).get();
      Assert.fail("No exception raised");
    } catch (ExecutionException e) {
      Assert.assertEquals(InvalidArgumentException.class, e.getCause().getClass());
      InvalidArgumentException apiException = ((InvalidArgumentException) e.getCause());
      Assert.assertEquals(StatusCode.Code.INVALID_ARGUMENT, apiException.getStatusCode().getCode());
    }
  }

  @Test
  public void batchGetAssetsHistoryTest() throws Exception {
    BatchGetAssetsHistoryResponse expectedResponse =
        BatchGetAssetsHistoryResponse.newBuilder()
            .addAllAssets(new ArrayList<TemporalAsset>())
            .build();
    mockAssetService.addResponse(expectedResponse);

    BatchGetAssetsHistoryRequest request =
        BatchGetAssetsHistoryRequest.newBuilder()
            .setParent("BatchGetAssetsHistoryRequest1575208378".toString())
            .addAllAssetNames(new ArrayList<String>())
            .setReadTimeWindow(TimeWindow.newBuilder().build())
            .build();

    BatchGetAssetsHistoryResponse actualResponse = client.batchGetAssetsHistory(request);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockAssetService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    BatchGetAssetsHistoryRequest actualRequest =
        ((BatchGetAssetsHistoryRequest) actualRequests.get(0));

    Assert.assertEquals(request.getParent(), actualRequest.getParent());
    Assert.assertEquals(request.getAssetNamesList(), actualRequest.getAssetNamesList());
    Assert.assertEquals(request.getContentType(), actualRequest.getContentType());
    Assert.assertEquals(request.getReadTimeWindow(), actualRequest.getReadTimeWindow());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void batchGetAssetsHistoryExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockAssetService.addException(exception);

    try {
      BatchGetAssetsHistoryRequest request =
          BatchGetAssetsHistoryRequest.newBuilder()
              .setParent("BatchGetAssetsHistoryRequest1575208378".toString())
              .addAllAssetNames(new ArrayList<String>())
              .setReadTimeWindow(TimeWindow.newBuilder().build())
              .build();
      client.batchGetAssetsHistory(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }
}
