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

package com.google.cloud.asset.v1.stub;

import static com.google.cloud.asset.v1.AssetServiceClient.SearchAllIamPoliciesPagedResponse;
import static com.google.cloud.asset.v1.AssetServiceClient.SearchAllResourcesPagedResponse;

import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.asset.v1.BatchGetAssetsHistoryRequest;
import com.google.cloud.asset.v1.BatchGetAssetsHistoryResponse;
import com.google.cloud.asset.v1.CreateFeedRequest;
import com.google.cloud.asset.v1.DeleteFeedRequest;
import com.google.cloud.asset.v1.ExportAssetsRequest;
import com.google.cloud.asset.v1.ExportAssetsResponse;
import com.google.cloud.asset.v1.Feed;
import com.google.cloud.asset.v1.GetFeedRequest;
import com.google.cloud.asset.v1.ListFeedsRequest;
import com.google.cloud.asset.v1.ListFeedsResponse;
import com.google.cloud.asset.v1.SearchAllIamPoliciesRequest;
import com.google.cloud.asset.v1.SearchAllIamPoliciesResponse;
import com.google.cloud.asset.v1.SearchAllResourcesRequest;
import com.google.cloud.asset.v1.SearchAllResourcesResponse;
import com.google.cloud.asset.v1.UpdateFeedRequest;
import com.google.longrunning.Operation;
import com.google.longrunning.stub.GrpcOperationsStub;
import com.google.protobuf.Empty;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * gRPC stub implementation for the AssetService service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@Generated("by gapic-generator-java")
public class GrpcAssetServiceStub extends AssetServiceStub {
  private static final MethodDescriptor<ExportAssetsRequest, Operation>
      exportAssetsMethodDescriptor =
          MethodDescriptor.<ExportAssetsRequest, Operation>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.cloud.asset.v1.AssetService/ExportAssets")
              .setRequestMarshaller(ProtoUtils.marshaller(ExportAssetsRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(Operation.getDefaultInstance()))
              .build();
  private static final MethodDescriptor<BatchGetAssetsHistoryRequest, BatchGetAssetsHistoryResponse>
      batchGetAssetsHistoryMethodDescriptor =
          MethodDescriptor.<BatchGetAssetsHistoryRequest, BatchGetAssetsHistoryResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.cloud.asset.v1.AssetService/BatchGetAssetsHistory")
              .setRequestMarshaller(
                  ProtoUtils.marshaller(BatchGetAssetsHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(
                  ProtoUtils.marshaller(BatchGetAssetsHistoryResponse.getDefaultInstance()))
              .build();
  private static final MethodDescriptor<CreateFeedRequest, Feed> createFeedMethodDescriptor =
      MethodDescriptor.<CreateFeedRequest, Feed>newBuilder()
          .setType(MethodDescriptor.MethodType.UNARY)
          .setFullMethodName("google.cloud.asset.v1.AssetService/CreateFeed")
          .setRequestMarshaller(ProtoUtils.marshaller(CreateFeedRequest.getDefaultInstance()))
          .setResponseMarshaller(ProtoUtils.marshaller(Feed.getDefaultInstance()))
          .build();
  private static final MethodDescriptor<GetFeedRequest, Feed> getFeedMethodDescriptor =
      MethodDescriptor.<GetFeedRequest, Feed>newBuilder()
          .setType(MethodDescriptor.MethodType.UNARY)
          .setFullMethodName("google.cloud.asset.v1.AssetService/GetFeed")
          .setRequestMarshaller(ProtoUtils.marshaller(GetFeedRequest.getDefaultInstance()))
          .setResponseMarshaller(ProtoUtils.marshaller(Feed.getDefaultInstance()))
          .build();
  private static final MethodDescriptor<ListFeedsRequest, ListFeedsResponse>
      listFeedsMethodDescriptor =
          MethodDescriptor.<ListFeedsRequest, ListFeedsResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.cloud.asset.v1.AssetService/ListFeeds")
              .setRequestMarshaller(ProtoUtils.marshaller(ListFeedsRequest.getDefaultInstance()))
              .setResponseMarshaller(ProtoUtils.marshaller(ListFeedsResponse.getDefaultInstance()))
              .build();
  private static final MethodDescriptor<UpdateFeedRequest, Feed> updateFeedMethodDescriptor =
      MethodDescriptor.<UpdateFeedRequest, Feed>newBuilder()
          .setType(MethodDescriptor.MethodType.UNARY)
          .setFullMethodName("google.cloud.asset.v1.AssetService/UpdateFeed")
          .setRequestMarshaller(ProtoUtils.marshaller(UpdateFeedRequest.getDefaultInstance()))
          .setResponseMarshaller(ProtoUtils.marshaller(Feed.getDefaultInstance()))
          .build();
  private static final MethodDescriptor<DeleteFeedRequest, Empty> deleteFeedMethodDescriptor =
      MethodDescriptor.<DeleteFeedRequest, Empty>newBuilder()
          .setType(MethodDescriptor.MethodType.UNARY)
          .setFullMethodName("google.cloud.asset.v1.AssetService/DeleteFeed")
          .setRequestMarshaller(ProtoUtils.marshaller(DeleteFeedRequest.getDefaultInstance()))
          .setResponseMarshaller(ProtoUtils.marshaller(Empty.getDefaultInstance()))
          .build();
  private static final MethodDescriptor<SearchAllResourcesRequest, SearchAllResourcesResponse>
      searchAllResourcesMethodDescriptor =
          MethodDescriptor.<SearchAllResourcesRequest, SearchAllResourcesResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.cloud.asset.v1.AssetService/SearchAllResources")
              .setRequestMarshaller(
                  ProtoUtils.marshaller(SearchAllResourcesRequest.getDefaultInstance()))
              .setResponseMarshaller(
                  ProtoUtils.marshaller(SearchAllResourcesResponse.getDefaultInstance()))
              .build();
  private static final MethodDescriptor<SearchAllIamPoliciesRequest, SearchAllIamPoliciesResponse>
      searchAllIamPoliciesMethodDescriptor =
          MethodDescriptor.<SearchAllIamPoliciesRequest, SearchAllIamPoliciesResponse>newBuilder()
              .setType(MethodDescriptor.MethodType.UNARY)
              .setFullMethodName("google.cloud.asset.v1.AssetService/SearchAllIamPolicies")
              .setRequestMarshaller(
                  ProtoUtils.marshaller(SearchAllIamPoliciesRequest.getDefaultInstance()))
              .setResponseMarshaller(
                  ProtoUtils.marshaller(SearchAllIamPoliciesResponse.getDefaultInstance()))
              .build();
  private final UnaryCallable<ExportAssetsRequest, Operation> exportAssetsCallable;
  private final OperationCallable<ExportAssetsRequest, ExportAssetsResponse, ExportAssetsRequest>
      exportAssetsOperationCallable;
  private final UnaryCallable<BatchGetAssetsHistoryRequest, BatchGetAssetsHistoryResponse>
      batchGetAssetsHistoryCallable;
  private final UnaryCallable<CreateFeedRequest, Feed> createFeedCallable;
  private final UnaryCallable<GetFeedRequest, Feed> getFeedCallable;
  private final UnaryCallable<ListFeedsRequest, ListFeedsResponse> listFeedsCallable;
  private final UnaryCallable<UpdateFeedRequest, Feed> updateFeedCallable;
  private final UnaryCallable<DeleteFeedRequest, Empty> deleteFeedCallable;
  private final UnaryCallable<SearchAllResourcesRequest, SearchAllResourcesResponse>
      searchAllResourcesCallable;
  private final UnaryCallable<SearchAllResourcesRequest, SearchAllResourcesPagedResponse>
      searchAllResourcesPagedCallable;
  private final UnaryCallable<SearchAllIamPoliciesRequest, SearchAllIamPoliciesResponse>
      searchAllIamPoliciesCallable;
  private final UnaryCallable<SearchAllIamPoliciesRequest, SearchAllIamPoliciesPagedResponse>
      searchAllIamPoliciesPagedCallable;
  private final BackgroundResource backgroundResources;
  private final GrpcOperationsStub operationsStub;
  private final GrpcStubCallableFactory callableFactory;

  public static final GrpcAssetServiceStub create(AssetServiceStubSettings settings)
      throws IOException {
    return new GrpcAssetServiceStub(settings, ClientContext.create(settings));
  }

  public static final GrpcAssetServiceStub create(ClientContext clientContext) throws IOException {
    return new GrpcAssetServiceStub(AssetServiceStubSettings.newBuilder().build(), clientContext);
  }

  public static final GrpcAssetServiceStub create(
      ClientContext clientContext, GrpcStubCallableFactory callableFactory) throws IOException {
    return new GrpcAssetServiceStub(
        AssetServiceStubSettings.newBuilder().build(), clientContext, callableFactory);
  }

  protected GrpcAssetServiceStub(AssetServiceStubSettings settings, ClientContext clientContext)
      throws IOException {
    this(settings, clientContext, new GrpcAssetServiceCallableFactory());
  }

  protected GrpcAssetServiceStub(
      AssetServiceStubSettings settings,
      ClientContext clientContext,
      GrpcStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;
    this.operationsStub = GrpcOperationsStub.create(clientContext, callableFactory);
    GrpcCallSettings<ExportAssetsRequest, Operation> exportAssetsTransportSettings =
        GrpcCallSettings.<ExportAssetsRequest, Operation>newBuilder()
            .setMethodDescriptor(exportAssetsMethodDescriptor)
            .build();
    GrpcCallSettings<BatchGetAssetsHistoryRequest, BatchGetAssetsHistoryResponse>
        batchGetAssetsHistoryTransportSettings =
            GrpcCallSettings
                .<BatchGetAssetsHistoryRequest, BatchGetAssetsHistoryResponse>newBuilder()
                .setMethodDescriptor(batchGetAssetsHistoryMethodDescriptor)
                .build();
    GrpcCallSettings<CreateFeedRequest, Feed> createFeedTransportSettings =
        GrpcCallSettings.<CreateFeedRequest, Feed>newBuilder()
            .setMethodDescriptor(createFeedMethodDescriptor)
            .build();
    GrpcCallSettings<GetFeedRequest, Feed> getFeedTransportSettings =
        GrpcCallSettings.<GetFeedRequest, Feed>newBuilder()
            .setMethodDescriptor(getFeedMethodDescriptor)
            .build();
    GrpcCallSettings<ListFeedsRequest, ListFeedsResponse> listFeedsTransportSettings =
        GrpcCallSettings.<ListFeedsRequest, ListFeedsResponse>newBuilder()
            .setMethodDescriptor(listFeedsMethodDescriptor)
            .build();
    GrpcCallSettings<UpdateFeedRequest, Feed> updateFeedTransportSettings =
        GrpcCallSettings.<UpdateFeedRequest, Feed>newBuilder()
            .setMethodDescriptor(updateFeedMethodDescriptor)
            .build();
    GrpcCallSettings<DeleteFeedRequest, Empty> deleteFeedTransportSettings =
        GrpcCallSettings.<DeleteFeedRequest, Empty>newBuilder()
            .setMethodDescriptor(deleteFeedMethodDescriptor)
            .build();
    GrpcCallSettings<SearchAllResourcesRequest, SearchAllResourcesResponse>
        searchAllResourcesTransportSettings =
            GrpcCallSettings.<SearchAllResourcesRequest, SearchAllResourcesResponse>newBuilder()
                .setMethodDescriptor(searchAllResourcesMethodDescriptor)
                .build();
    GrpcCallSettings<SearchAllIamPoliciesRequest, SearchAllIamPoliciesResponse>
        searchAllIamPoliciesTransportSettings =
            GrpcCallSettings.<SearchAllIamPoliciesRequest, SearchAllIamPoliciesResponse>newBuilder()
                .setMethodDescriptor(searchAllIamPoliciesMethodDescriptor)
                .build();
    this.exportAssetsCallable =
        callableFactory.createUnaryCallable(
            exportAssetsTransportSettings, settings.exportAssetsSettings(), clientContext);
    this.exportAssetsOperationCallable =
        callableFactory.createOperationCallable(
            exportAssetsTransportSettings,
            settings.exportAssetsOperationSettings(),
            clientContext,
            operationsStub);
    this.batchGetAssetsHistoryCallable =
        callableFactory.createUnaryCallable(
            batchGetAssetsHistoryTransportSettings,
            settings.batchGetAssetsHistorySettings(),
            clientContext);
    this.createFeedCallable =
        callableFactory.createUnaryCallable(
            createFeedTransportSettings, settings.createFeedSettings(), clientContext);
    this.getFeedCallable =
        callableFactory.createUnaryCallable(
            getFeedTransportSettings, settings.getFeedSettings(), clientContext);
    this.listFeedsCallable =
        callableFactory.createUnaryCallable(
            listFeedsTransportSettings, settings.listFeedsSettings(), clientContext);
    this.updateFeedCallable =
        callableFactory.createUnaryCallable(
            updateFeedTransportSettings, settings.updateFeedSettings(), clientContext);
    this.deleteFeedCallable =
        callableFactory.createUnaryCallable(
            deleteFeedTransportSettings, settings.deleteFeedSettings(), clientContext);
    this.searchAllResourcesCallable =
        callableFactory.createUnaryCallable(
            searchAllResourcesTransportSettings,
            settings.searchAllResourcesSettings(),
            clientContext);
    this.searchAllResourcesPagedCallable =
        callableFactory.createPagedCallable(
            searchAllResourcesTransportSettings,
            settings.searchAllResourcesSettings(),
            clientContext);
    this.searchAllIamPoliciesCallable =
        callableFactory.createUnaryCallable(
            searchAllIamPoliciesTransportSettings,
            settings.searchAllIamPoliciesSettings(),
            clientContext);
    this.searchAllIamPoliciesPagedCallable =
        callableFactory.createPagedCallable(
            searchAllIamPoliciesTransportSettings,
            settings.searchAllIamPoliciesSettings(),
            clientContext);
    this.backgroundResources =
        new BackgroundResourceAggregation(clientContext.getBackgroundResources());
  }

  public GrpcOperationsStub getOperationsStub() {
    return operationsStub;
  }

  public UnaryCallable<ExportAssetsRequest, Operation> exportAssetsCallable() {
    return exportAssetsCallable;
  }

  public OperationCallable<ExportAssetsRequest, ExportAssetsResponse, ExportAssetsRequest>
      exportAssetsOperationCallable() {
    return exportAssetsOperationCallable;
  }

  public UnaryCallable<BatchGetAssetsHistoryRequest, BatchGetAssetsHistoryResponse>
      batchGetAssetsHistoryCallable() {
    return batchGetAssetsHistoryCallable;
  }

  public UnaryCallable<CreateFeedRequest, Feed> createFeedCallable() {
    return createFeedCallable;
  }

  public UnaryCallable<GetFeedRequest, Feed> getFeedCallable() {
    return getFeedCallable;
  }

  public UnaryCallable<ListFeedsRequest, ListFeedsResponse> listFeedsCallable() {
    return listFeedsCallable;
  }

  public UnaryCallable<UpdateFeedRequest, Feed> updateFeedCallable() {
    return updateFeedCallable;
  }

  public UnaryCallable<DeleteFeedRequest, Empty> deleteFeedCallable() {
    return deleteFeedCallable;
  }

  public UnaryCallable<SearchAllResourcesRequest, SearchAllResourcesResponse>
      searchAllResourcesCallable() {
    return searchAllResourcesCallable;
  }

  public UnaryCallable<SearchAllResourcesRequest, SearchAllResourcesPagedResponse>
      searchAllResourcesPagedCallable() {
    return searchAllResourcesPagedCallable;
  }

  public UnaryCallable<SearchAllIamPoliciesRequest, SearchAllIamPoliciesResponse>
      searchAllIamPoliciesCallable() {
    return searchAllIamPoliciesCallable;
  }

  public UnaryCallable<SearchAllIamPoliciesRequest, SearchAllIamPoliciesPagedResponse>
      searchAllIamPoliciesPagedCallable() {
    return searchAllIamPoliciesPagedCallable;
  }

  @Override
  public final void close() {
    shutdown();
  }

  @Override
  public void shutdown() {
    backgroundResources.shutdown();
  }

  @Override
  public boolean isShutdown() {
    return backgroundResources.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return backgroundResources.isTerminated();
  }

  @Override
  public void shutdownNow() {
    backgroundResources.shutdownNow();
  }

  @Override
  public boolean awaitTermination(long duration, TimeUnit unit) throws InterruptedException {
    return backgroundResources.awaitTermination(duration, unit);
  }
}
