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

package com.google.cloud.compute.v1.stub;

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.httpjson.ApiMessage;
import com.google.api.gax.httpjson.HttpJsonCallSettings;
import com.google.api.gax.httpjson.HttpJsonCallableFactory;
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.gax.rpc.BatchingCallSettings;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.OperationCallSettings;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.PagedCallSettings;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.api.gax.rpc.UnaryCallable;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * REST callable factory implementation for the RegionOperations service API.
 *
 * <p>This class is for advanced usage.
 */
@Generated("by gapic-generator-java")
@BetaApi
public class HttpJsonRegionOperationsCallableFactory
    implements HttpJsonStubCallableFactory<ApiMessage, BackgroundResource> {

  @Override
  public <RequestT, ResponseT> UnaryCallable<RequestT, ResponseT> createUnaryCallable(
      HttpJsonCallSettings<RequestT, ResponseT> httpJsonCallSettings,
      UnaryCallSettings<RequestT, ResponseT> callSettings,
      ClientContext clientContext) {
    return HttpJsonCallableFactory.createUnaryCallable(
        httpJsonCallSettings, callSettings, clientContext);
  }

  @Override
  public <RequestT, ResponseT, PagedListResponseT>
      UnaryCallable<RequestT, PagedListResponseT> createPagedCallable(
          HttpJsonCallSettings<RequestT, ResponseT> httpJsonCallSettings,
          PagedCallSettings<RequestT, ResponseT, PagedListResponseT> callSettings,
          ClientContext clientContext) {
    return HttpJsonCallableFactory.createPagedCallable(
        httpJsonCallSettings, callSettings, clientContext);
  }

  @Override
  public <RequestT, ResponseT> UnaryCallable<RequestT, ResponseT> createBatchingCallable(
      HttpJsonCallSettings<RequestT, ResponseT> httpJsonCallSettings,
      BatchingCallSettings<RequestT, ResponseT> callSettings,
      ClientContext clientContext) {
    return HttpJsonCallableFactory.createBatchingCallable(
        httpJsonCallSettings, callSettings, clientContext);
  }

  @BetaApi(
      "The surface for long-running operations is not stable yet and may change in the future.")
  @Override
  public <RequestT, ResponseT, MetadataT>
      OperationCallable<RequestT, ResponseT, MetadataT> createOperationCallable(
          HttpJsonCallSettings<RequestT, ApiMessage> httpJsonCallSettings,
          OperationCallSettings<RequestT, ResponseT, MetadataT> callSettings,
          ClientContext clientContext,
          BackgroundResource operationsStub) {
    return null;
  }
}
