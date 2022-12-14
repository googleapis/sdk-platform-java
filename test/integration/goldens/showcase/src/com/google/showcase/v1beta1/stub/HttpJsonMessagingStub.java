/*
 * Copyright 2022 Google LLC
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

package com.google.showcase.v1beta1.stub;

import static com.google.showcase.v1beta1.MessagingClient.ListBlurbsPagedResponse;
import static com.google.showcase.v1beta1.MessagingClient.ListRoomsPagedResponse;

import com.google.api.core.BetaApi;
import com.google.api.core.InternalApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.HttpJsonCallSettings;
import com.google.api.gax.httpjson.HttpJsonOperationSnapshot;
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.gax.httpjson.ProtoMessageRequestFormatter;
import com.google.api.gax.httpjson.ProtoMessageResponseParser;
import com.google.api.gax.httpjson.ProtoRestSerializer;
import com.google.api.gax.httpjson.longrunning.stub.HttpJsonOperationsStub;
import com.google.api.gax.longrunning.OperationSnapshot;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.longrunning.Operation;
import com.google.protobuf.Empty;
import com.google.protobuf.TypeRegistry;
import com.google.showcase.v1beta1.Blurb;
import com.google.showcase.v1beta1.CreateBlurbRequest;
import com.google.showcase.v1beta1.CreateRoomRequest;
import com.google.showcase.v1beta1.DeleteBlurbRequest;
import com.google.showcase.v1beta1.DeleteRoomRequest;
import com.google.showcase.v1beta1.GetBlurbRequest;
import com.google.showcase.v1beta1.GetRoomRequest;
import com.google.showcase.v1beta1.ListBlurbsRequest;
import com.google.showcase.v1beta1.ListBlurbsResponse;
import com.google.showcase.v1beta1.ListRoomsRequest;
import com.google.showcase.v1beta1.ListRoomsResponse;
import com.google.showcase.v1beta1.Room;
import com.google.showcase.v1beta1.SearchBlurbsMetadata;
import com.google.showcase.v1beta1.SearchBlurbsRequest;
import com.google.showcase.v1beta1.SearchBlurbsResponse;
import com.google.showcase.v1beta1.StreamBlurbsRequest;
import com.google.showcase.v1beta1.StreamBlurbsResponse;
import com.google.showcase.v1beta1.UpdateBlurbRequest;
import com.google.showcase.v1beta1.UpdateRoomRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * REST stub implementation for the Messaging service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class HttpJsonMessagingStub extends MessagingStub {
  private static final TypeRegistry typeRegistry =
      TypeRegistry.newBuilder()
          .add(SearchBlurbsResponse.getDescriptor())
          .add(SearchBlurbsMetadata.getDescriptor())
          .build();

  private static final ApiMethodDescriptor<CreateRoomRequest, Room> createRoomMethodDescriptor =
      ApiMethodDescriptor.<CreateRoomRequest, Room>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Messaging/CreateRoom")
          .setHttpMethod("POST")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<CreateRoomRequest>newBuilder()
                  .setPath(
                      "/v1beta1/rooms",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<CreateRoomRequest> serializer =
                            ProtoRestSerializer.create();
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<CreateRoomRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create()
                              .toBody("*", request.toBuilder().build(), true))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Room>newBuilder()
                  .setDefaultInstance(Room.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<GetRoomRequest, Room> getRoomMethodDescriptor =
      ApiMethodDescriptor.<GetRoomRequest, Room>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Messaging/GetRoom")
          .setHttpMethod("GET")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<GetRoomRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{name=rooms/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<GetRoomRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "name", request.getName());
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<GetRoomRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(request -> null)
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Room>newBuilder()
                  .setDefaultInstance(Room.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<UpdateRoomRequest, Room> updateRoomMethodDescriptor =
      ApiMethodDescriptor.<UpdateRoomRequest, Room>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Messaging/UpdateRoom")
          .setHttpMethod("PATCH")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<UpdateRoomRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{room.name=rooms/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<UpdateRoomRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "room.name", request.getRoom().getName());
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<UpdateRoomRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "updateMask", request.getUpdateMask());
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create().toBody("room", request.getRoom(), true))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Room>newBuilder()
                  .setDefaultInstance(Room.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<DeleteRoomRequest, Empty> deleteRoomMethodDescriptor =
      ApiMethodDescriptor.<DeleteRoomRequest, Empty>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Messaging/DeleteRoom")
          .setHttpMethod("DELETE")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<DeleteRoomRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{name=rooms/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<DeleteRoomRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "name", request.getName());
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<DeleteRoomRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(request -> null)
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Empty>newBuilder()
                  .setDefaultInstance(Empty.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<ListRoomsRequest, ListRoomsResponse>
      listRoomsMethodDescriptor =
          ApiMethodDescriptor.<ListRoomsRequest, ListRoomsResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Messaging/ListRooms")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<ListRoomsRequest>newBuilder()
                      .setPath(
                          "/v1beta1/rooms",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<ListRoomsRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<ListRoomsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "pageSize", request.getPageSize());
                            serializer.putQueryParam(fields, "pageToken", request.getPageToken());
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<ListRoomsResponse>newBuilder()
                      .setDefaultInstance(ListRoomsResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<CreateBlurbRequest, Blurb> createBlurbMethodDescriptor =
      ApiMethodDescriptor.<CreateBlurbRequest, Blurb>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Messaging/CreateBlurb")
          .setHttpMethod("POST")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<CreateBlurbRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{parent=rooms/*}/blurbs",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<CreateBlurbRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "parent", request.getParent());
                        return fields;
                      })
                  .setAdditionalPaths("/v1beta1/{parent=users/*/profile}/blurbs")
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<CreateBlurbRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create()
                              .toBody("*", request.toBuilder().clearParent().build(), true))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Blurb>newBuilder()
                  .setDefaultInstance(Blurb.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<GetBlurbRequest, Blurb> getBlurbMethodDescriptor =
      ApiMethodDescriptor.<GetBlurbRequest, Blurb>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Messaging/GetBlurb")
          .setHttpMethod("GET")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<GetBlurbRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{name=rooms/*/blurbs/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<GetBlurbRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "name", request.getName());
                        return fields;
                      })
                  .setAdditionalPaths("/v1beta1/{name=users/*/profile/blurbs/*}")
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<GetBlurbRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(request -> null)
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Blurb>newBuilder()
                  .setDefaultInstance(Blurb.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<UpdateBlurbRequest, Blurb> updateBlurbMethodDescriptor =
      ApiMethodDescriptor.<UpdateBlurbRequest, Blurb>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Messaging/UpdateBlurb")
          .setHttpMethod("PATCH")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<UpdateBlurbRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{blurb.name=rooms/*/blurbs/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<UpdateBlurbRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "blurb.name", request.getBlurb().getName());
                        return fields;
                      })
                  .setAdditionalPaths("/v1beta1/{blurb.name=users/*/profile/blurbs/*}")
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<UpdateBlurbRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "updateMask", request.getUpdateMask());
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create().toBody("blurb", request.getBlurb(), true))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Blurb>newBuilder()
                  .setDefaultInstance(Blurb.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<DeleteBlurbRequest, Empty> deleteBlurbMethodDescriptor =
      ApiMethodDescriptor.<DeleteBlurbRequest, Empty>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Messaging/DeleteBlurb")
          .setHttpMethod("DELETE")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<DeleteBlurbRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{name=rooms/*/blurbs/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<DeleteBlurbRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "name", request.getName());
                        return fields;
                      })
                  .setAdditionalPaths("/v1beta1/{name=users/*/profile/blurbs/*}")
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<DeleteBlurbRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                        return fields;
                      })
                  .setRequestBodyExtractor(request -> null)
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<Empty>newBuilder()
                  .setDefaultInstance(Empty.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<ListBlurbsRequest, ListBlurbsResponse>
      listBlurbsMethodDescriptor =
          ApiMethodDescriptor.<ListBlurbsRequest, ListBlurbsResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Messaging/ListBlurbs")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<ListBlurbsRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{parent=rooms/*}/blurbs",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<ListBlurbsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "parent", request.getParent());
                            return fields;
                          })
                      .setAdditionalPaths("/v1beta1/{parent=users/*/profile}/blurbs")
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<ListBlurbsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "pageSize", request.getPageSize());
                            serializer.putQueryParam(fields, "pageToken", request.getPageToken());
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<ListBlurbsResponse>newBuilder()
                      .setDefaultInstance(ListBlurbsResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private static final ApiMethodDescriptor<SearchBlurbsRequest, Operation>
      searchBlurbsMethodDescriptor =
          ApiMethodDescriptor.<SearchBlurbsRequest, Operation>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Messaging/SearchBlurbs")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<SearchBlurbsRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{parent=rooms/*}/blurbs:search",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<SearchBlurbsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "parent", request.getParent());
                            return fields;
                          })
                      .setAdditionalPaths("/v1beta1/{parent=users/*/profile}/blurbs:search")
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<SearchBlurbsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("*", request.toBuilder().clearParent().build(), true))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<Operation>newBuilder()
                      .setDefaultInstance(Operation.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .setOperationSnapshotFactory(
                  (SearchBlurbsRequest request, Operation response) ->
                      HttpJsonOperationSnapshot.create(response))
              .build();

  private static final ApiMethodDescriptor<StreamBlurbsRequest, StreamBlurbsResponse>
      streamBlurbsMethodDescriptor =
          ApiMethodDescriptor.<StreamBlurbsRequest, StreamBlurbsResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Messaging/StreamBlurbs")
              .setHttpMethod("POST")
              .setType(ApiMethodDescriptor.MethodType.SERVER_STREAMING)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<StreamBlurbsRequest>newBuilder()
                      .setPath(
                          "/v1beta1/{name=rooms/*}/blurbs:stream",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<StreamBlurbsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putPathParam(fields, "name", request.getName());
                            return fields;
                          })
                      .setAdditionalPaths("/v1beta1/{name=users/*/profile}/blurbs:stream")
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<StreamBlurbsRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "$alt", "json;enum-encoding=int");
                            return fields;
                          })
                      .setRequestBodyExtractor(
                          request ->
                              ProtoRestSerializer.create()
                                  .toBody("*", request.toBuilder().clearName().build(), true))
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<StreamBlurbsResponse>newBuilder()
                      .setDefaultInstance(StreamBlurbsResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private final UnaryCallable<CreateRoomRequest, Room> createRoomCallable;
  private final UnaryCallable<GetRoomRequest, Room> getRoomCallable;
  private final UnaryCallable<UpdateRoomRequest, Room> updateRoomCallable;
  private final UnaryCallable<DeleteRoomRequest, Empty> deleteRoomCallable;
  private final UnaryCallable<ListRoomsRequest, ListRoomsResponse> listRoomsCallable;
  private final UnaryCallable<ListRoomsRequest, ListRoomsPagedResponse> listRoomsPagedCallable;
  private final UnaryCallable<CreateBlurbRequest, Blurb> createBlurbCallable;
  private final UnaryCallable<GetBlurbRequest, Blurb> getBlurbCallable;
  private final UnaryCallable<UpdateBlurbRequest, Blurb> updateBlurbCallable;
  private final UnaryCallable<DeleteBlurbRequest, Empty> deleteBlurbCallable;
  private final UnaryCallable<ListBlurbsRequest, ListBlurbsResponse> listBlurbsCallable;
  private final UnaryCallable<ListBlurbsRequest, ListBlurbsPagedResponse> listBlurbsPagedCallable;
  private final UnaryCallable<SearchBlurbsRequest, Operation> searchBlurbsCallable;
  private final OperationCallable<SearchBlurbsRequest, SearchBlurbsResponse, SearchBlurbsMetadata>
      searchBlurbsOperationCallable;
  private final ServerStreamingCallable<StreamBlurbsRequest, StreamBlurbsResponse>
      streamBlurbsCallable;

  private final BackgroundResource backgroundResources;
  private final HttpJsonOperationsStub httpJsonOperationsStub;
  private final HttpJsonStubCallableFactory callableFactory;

  public static final HttpJsonMessagingStub create(MessagingStubSettings settings)
      throws IOException {
    return new HttpJsonMessagingStub(settings, ClientContext.create(settings));
  }

  public static final HttpJsonMessagingStub create(ClientContext clientContext) throws IOException {
    return new HttpJsonMessagingStub(
        MessagingStubSettings.newHttpJsonBuilder().build(), clientContext);
  }

  public static final HttpJsonMessagingStub create(
      ClientContext clientContext, HttpJsonStubCallableFactory callableFactory) throws IOException {
    return new HttpJsonMessagingStub(
        MessagingStubSettings.newHttpJsonBuilder().build(), clientContext, callableFactory);
  }

  /**
   * Constructs an instance of HttpJsonMessagingStub, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected HttpJsonMessagingStub(MessagingStubSettings settings, ClientContext clientContext)
      throws IOException {
    this(settings, clientContext, new HttpJsonMessagingCallableFactory());
  }

  /**
   * Constructs an instance of HttpJsonMessagingStub, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected HttpJsonMessagingStub(
      MessagingStubSettings settings,
      ClientContext clientContext,
      HttpJsonStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;
    this.httpJsonOperationsStub =
        HttpJsonOperationsStub.create(clientContext, callableFactory, typeRegistry);

    HttpJsonCallSettings<CreateRoomRequest, Room> createRoomTransportSettings =
        HttpJsonCallSettings.<CreateRoomRequest, Room>newBuilder()
            .setMethodDescriptor(createRoomMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<GetRoomRequest, Room> getRoomTransportSettings =
        HttpJsonCallSettings.<GetRoomRequest, Room>newBuilder()
            .setMethodDescriptor(getRoomMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<UpdateRoomRequest, Room> updateRoomTransportSettings =
        HttpJsonCallSettings.<UpdateRoomRequest, Room>newBuilder()
            .setMethodDescriptor(updateRoomMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<DeleteRoomRequest, Empty> deleteRoomTransportSettings =
        HttpJsonCallSettings.<DeleteRoomRequest, Empty>newBuilder()
            .setMethodDescriptor(deleteRoomMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<ListRoomsRequest, ListRoomsResponse> listRoomsTransportSettings =
        HttpJsonCallSettings.<ListRoomsRequest, ListRoomsResponse>newBuilder()
            .setMethodDescriptor(listRoomsMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<CreateBlurbRequest, Blurb> createBlurbTransportSettings =
        HttpJsonCallSettings.<CreateBlurbRequest, Blurb>newBuilder()
            .setMethodDescriptor(createBlurbMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<GetBlurbRequest, Blurb> getBlurbTransportSettings =
        HttpJsonCallSettings.<GetBlurbRequest, Blurb>newBuilder()
            .setMethodDescriptor(getBlurbMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<UpdateBlurbRequest, Blurb> updateBlurbTransportSettings =
        HttpJsonCallSettings.<UpdateBlurbRequest, Blurb>newBuilder()
            .setMethodDescriptor(updateBlurbMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<DeleteBlurbRequest, Empty> deleteBlurbTransportSettings =
        HttpJsonCallSettings.<DeleteBlurbRequest, Empty>newBuilder()
            .setMethodDescriptor(deleteBlurbMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<ListBlurbsRequest, ListBlurbsResponse> listBlurbsTransportSettings =
        HttpJsonCallSettings.<ListBlurbsRequest, ListBlurbsResponse>newBuilder()
            .setMethodDescriptor(listBlurbsMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<SearchBlurbsRequest, Operation> searchBlurbsTransportSettings =
        HttpJsonCallSettings.<SearchBlurbsRequest, Operation>newBuilder()
            .setMethodDescriptor(searchBlurbsMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<StreamBlurbsRequest, StreamBlurbsResponse> streamBlurbsTransportSettings =
        HttpJsonCallSettings.<StreamBlurbsRequest, StreamBlurbsResponse>newBuilder()
            .setMethodDescriptor(streamBlurbsMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();

    this.createRoomCallable =
        callableFactory.createUnaryCallable(
            createRoomTransportSettings, settings.createRoomSettings(), clientContext);
    this.getRoomCallable =
        callableFactory.createUnaryCallable(
            getRoomTransportSettings, settings.getRoomSettings(), clientContext);
    this.updateRoomCallable =
        callableFactory.createUnaryCallable(
            updateRoomTransportSettings, settings.updateRoomSettings(), clientContext);
    this.deleteRoomCallable =
        callableFactory.createUnaryCallable(
            deleteRoomTransportSettings, settings.deleteRoomSettings(), clientContext);
    this.listRoomsCallable =
        callableFactory.createUnaryCallable(
            listRoomsTransportSettings, settings.listRoomsSettings(), clientContext);
    this.listRoomsPagedCallable =
        callableFactory.createPagedCallable(
            listRoomsTransportSettings, settings.listRoomsSettings(), clientContext);
    this.createBlurbCallable =
        callableFactory.createUnaryCallable(
            createBlurbTransportSettings, settings.createBlurbSettings(), clientContext);
    this.getBlurbCallable =
        callableFactory.createUnaryCallable(
            getBlurbTransportSettings, settings.getBlurbSettings(), clientContext);
    this.updateBlurbCallable =
        callableFactory.createUnaryCallable(
            updateBlurbTransportSettings, settings.updateBlurbSettings(), clientContext);
    this.deleteBlurbCallable =
        callableFactory.createUnaryCallable(
            deleteBlurbTransportSettings, settings.deleteBlurbSettings(), clientContext);
    this.listBlurbsCallable =
        callableFactory.createUnaryCallable(
            listBlurbsTransportSettings, settings.listBlurbsSettings(), clientContext);
    this.listBlurbsPagedCallable =
        callableFactory.createPagedCallable(
            listBlurbsTransportSettings, settings.listBlurbsSettings(), clientContext);
    this.searchBlurbsCallable =
        callableFactory.createUnaryCallable(
            searchBlurbsTransportSettings, settings.searchBlurbsSettings(), clientContext);
    this.searchBlurbsOperationCallable =
        callableFactory.createOperationCallable(
            searchBlurbsTransportSettings,
            settings.searchBlurbsOperationSettings(),
            clientContext,
            httpJsonOperationsStub);
    this.streamBlurbsCallable =
        callableFactory.createServerStreamingCallable(
            streamBlurbsTransportSettings, settings.streamBlurbsSettings(), clientContext);

    this.backgroundResources =
        new BackgroundResourceAggregation(clientContext.getBackgroundResources());
  }

  @InternalApi
  public static List<ApiMethodDescriptor> getMethodDescriptors() {
    List<ApiMethodDescriptor> methodDescriptors = new ArrayList<>();
    methodDescriptors.add(createRoomMethodDescriptor);
    methodDescriptors.add(getRoomMethodDescriptor);
    methodDescriptors.add(updateRoomMethodDescriptor);
    methodDescriptors.add(deleteRoomMethodDescriptor);
    methodDescriptors.add(listRoomsMethodDescriptor);
    methodDescriptors.add(createBlurbMethodDescriptor);
    methodDescriptors.add(getBlurbMethodDescriptor);
    methodDescriptors.add(updateBlurbMethodDescriptor);
    methodDescriptors.add(deleteBlurbMethodDescriptor);
    methodDescriptors.add(listBlurbsMethodDescriptor);
    methodDescriptors.add(searchBlurbsMethodDescriptor);
    methodDescriptors.add(streamBlurbsMethodDescriptor);
    return methodDescriptors;
  }

  public HttpJsonOperationsStub getHttpJsonOperationsStub() {
    return httpJsonOperationsStub;
  }

  @Override
  public UnaryCallable<CreateRoomRequest, Room> createRoomCallable() {
    return createRoomCallable;
  }

  @Override
  public UnaryCallable<GetRoomRequest, Room> getRoomCallable() {
    return getRoomCallable;
  }

  @Override
  public UnaryCallable<UpdateRoomRequest, Room> updateRoomCallable() {
    return updateRoomCallable;
  }

  @Override
  public UnaryCallable<DeleteRoomRequest, Empty> deleteRoomCallable() {
    return deleteRoomCallable;
  }

  @Override
  public UnaryCallable<ListRoomsRequest, ListRoomsResponse> listRoomsCallable() {
    return listRoomsCallable;
  }

  @Override
  public UnaryCallable<ListRoomsRequest, ListRoomsPagedResponse> listRoomsPagedCallable() {
    return listRoomsPagedCallable;
  }

  @Override
  public UnaryCallable<CreateBlurbRequest, Blurb> createBlurbCallable() {
    return createBlurbCallable;
  }

  @Override
  public UnaryCallable<GetBlurbRequest, Blurb> getBlurbCallable() {
    return getBlurbCallable;
  }

  @Override
  public UnaryCallable<UpdateBlurbRequest, Blurb> updateBlurbCallable() {
    return updateBlurbCallable;
  }

  @Override
  public UnaryCallable<DeleteBlurbRequest, Empty> deleteBlurbCallable() {
    return deleteBlurbCallable;
  }

  @Override
  public UnaryCallable<ListBlurbsRequest, ListBlurbsResponse> listBlurbsCallable() {
    return listBlurbsCallable;
  }

  @Override
  public UnaryCallable<ListBlurbsRequest, ListBlurbsPagedResponse> listBlurbsPagedCallable() {
    return listBlurbsPagedCallable;
  }

  @Override
  public UnaryCallable<SearchBlurbsRequest, Operation> searchBlurbsCallable() {
    return searchBlurbsCallable;
  }

  @Override
  public OperationCallable<SearchBlurbsRequest, SearchBlurbsResponse, SearchBlurbsMetadata>
      searchBlurbsOperationCallable() {
    return searchBlurbsOperationCallable;
  }

  @Override
  public ServerStreamingCallable<StreamBlurbsRequest, StreamBlurbsResponse> streamBlurbsCallable() {
    return streamBlurbsCallable;
  }

  @Override
  public final void close() {
    try {
      backgroundResources.close();
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new IllegalStateException("Failed to close resource", e);
    }
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
