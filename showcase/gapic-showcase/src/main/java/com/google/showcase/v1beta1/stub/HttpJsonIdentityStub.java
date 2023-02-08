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

import static com.google.showcase.v1beta1.IdentityClient.ListUsersPagedResponse;

import com.google.api.core.BetaApi;
import com.google.api.core.InternalApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.HttpJsonCallSettings;
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.gax.httpjson.ProtoMessageRequestFormatter;
import com.google.api.gax.httpjson.ProtoMessageResponseParser;
import com.google.api.gax.httpjson.ProtoRestSerializer;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.protobuf.Empty;
import com.google.protobuf.TypeRegistry;
import com.google.showcase.v1beta1.CreateUserRequest;
import com.google.showcase.v1beta1.DeleteUserRequest;
import com.google.showcase.v1beta1.GetUserRequest;
import com.google.showcase.v1beta1.ListUsersRequest;
import com.google.showcase.v1beta1.ListUsersResponse;
import com.google.showcase.v1beta1.UpdateUserRequest;
import com.google.showcase.v1beta1.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * REST stub implementation for the Identity service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class HttpJsonIdentityStub extends IdentityStub {
  private static final TypeRegistry typeRegistry = TypeRegistry.newBuilder().build();

  private static final ApiMethodDescriptor<CreateUserRequest, User> createUserMethodDescriptor =
      ApiMethodDescriptor.<CreateUserRequest, User>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Identity/CreateUser")
          .setHttpMethod("POST")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<CreateUserRequest>newBuilder()
                  .setPath(
                      "/v1beta1/users",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<CreateUserRequest> serializer =
                            ProtoRestSerializer.create();
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<CreateUserRequest> serializer =
                            ProtoRestSerializer.create();
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create()
                              .toBody("*", request.toBuilder().build(), false))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<User>newBuilder()
                  .setDefaultInstance(User.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<GetUserRequest, User> getUserMethodDescriptor =
      ApiMethodDescriptor.<GetUserRequest, User>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Identity/GetUser")
          .setHttpMethod("GET")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<GetUserRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{name=users/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<GetUserRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "name", request.getName());
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<GetUserRequest> serializer =
                            ProtoRestSerializer.create();
                        return fields;
                      })
                  .setRequestBodyExtractor(request -> null)
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<User>newBuilder()
                  .setDefaultInstance(User.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<UpdateUserRequest, User> updateUserMethodDescriptor =
      ApiMethodDescriptor.<UpdateUserRequest, User>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Identity/UpdateUser")
          .setHttpMethod("PATCH")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<UpdateUserRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{user.name=users/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<UpdateUserRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "user.name", request.getUser().getName());
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<UpdateUserRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putQueryParam(fields, "updateMask", request.getUpdateMask());
                        return fields;
                      })
                  .setRequestBodyExtractor(
                      request ->
                          ProtoRestSerializer.create().toBody("user", request.getUser(), false))
                  .build())
          .setResponseParser(
              ProtoMessageResponseParser.<User>newBuilder()
                  .setDefaultInstance(User.getDefaultInstance())
                  .setDefaultTypeRegistry(typeRegistry)
                  .build())
          .build();

  private static final ApiMethodDescriptor<DeleteUserRequest, Empty> deleteUserMethodDescriptor =
      ApiMethodDescriptor.<DeleteUserRequest, Empty>newBuilder()
          .setFullMethodName("google.showcase.v1beta1.Identity/DeleteUser")
          .setHttpMethod("DELETE")
          .setType(ApiMethodDescriptor.MethodType.UNARY)
          .setRequestFormatter(
              ProtoMessageRequestFormatter.<DeleteUserRequest>newBuilder()
                  .setPath(
                      "/v1beta1/{name=users/*}",
                      request -> {
                        Map<String, String> fields = new HashMap<>();
                        ProtoRestSerializer<DeleteUserRequest> serializer =
                            ProtoRestSerializer.create();
                        serializer.putPathParam(fields, "name", request.getName());
                        return fields;
                      })
                  .setQueryParamsExtractor(
                      request -> {
                        Map<String, List<String>> fields = new HashMap<>();
                        ProtoRestSerializer<DeleteUserRequest> serializer =
                            ProtoRestSerializer.create();
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

  private static final ApiMethodDescriptor<ListUsersRequest, ListUsersResponse>
      listUsersMethodDescriptor =
          ApiMethodDescriptor.<ListUsersRequest, ListUsersResponse>newBuilder()
              .setFullMethodName("google.showcase.v1beta1.Identity/ListUsers")
              .setHttpMethod("GET")
              .setType(ApiMethodDescriptor.MethodType.UNARY)
              .setRequestFormatter(
                  ProtoMessageRequestFormatter.<ListUsersRequest>newBuilder()
                      .setPath(
                          "/v1beta1/users",
                          request -> {
                            Map<String, String> fields = new HashMap<>();
                            ProtoRestSerializer<ListUsersRequest> serializer =
                                ProtoRestSerializer.create();
                            return fields;
                          })
                      .setQueryParamsExtractor(
                          request -> {
                            Map<String, List<String>> fields = new HashMap<>();
                            ProtoRestSerializer<ListUsersRequest> serializer =
                                ProtoRestSerializer.create();
                            serializer.putQueryParam(fields, "pageSize", request.getPageSize());
                            serializer.putQueryParam(fields, "pageToken", request.getPageToken());
                            return fields;
                          })
                      .setRequestBodyExtractor(request -> null)
                      .build())
              .setResponseParser(
                  ProtoMessageResponseParser.<ListUsersResponse>newBuilder()
                      .setDefaultInstance(ListUsersResponse.getDefaultInstance())
                      .setDefaultTypeRegistry(typeRegistry)
                      .build())
              .build();

  private final UnaryCallable<CreateUserRequest, User> createUserCallable;
  private final UnaryCallable<GetUserRequest, User> getUserCallable;
  private final UnaryCallable<UpdateUserRequest, User> updateUserCallable;
  private final UnaryCallable<DeleteUserRequest, Empty> deleteUserCallable;
  private final UnaryCallable<ListUsersRequest, ListUsersResponse> listUsersCallable;
  private final UnaryCallable<ListUsersRequest, ListUsersPagedResponse> listUsersPagedCallable;

  private final BackgroundResource backgroundResources;
  private final HttpJsonStubCallableFactory callableFactory;

  public static final HttpJsonIdentityStub create(IdentityStubSettings settings)
      throws IOException {
    return new HttpJsonIdentityStub(settings, ClientContext.create(settings));
  }

  public static final HttpJsonIdentityStub create(ClientContext clientContext) throws IOException {
    return new HttpJsonIdentityStub(
        IdentityStubSettings.newHttpJsonBuilder().build(), clientContext);
  }

  public static final HttpJsonIdentityStub create(
      ClientContext clientContext, HttpJsonStubCallableFactory callableFactory) throws IOException {
    return new HttpJsonIdentityStub(
        IdentityStubSettings.newHttpJsonBuilder().build(), clientContext, callableFactory);
  }

  /**
   * Constructs an instance of HttpJsonIdentityStub, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected HttpJsonIdentityStub(IdentityStubSettings settings, ClientContext clientContext)
      throws IOException {
    this(settings, clientContext, new HttpJsonIdentityCallableFactory());
  }

  /**
   * Constructs an instance of HttpJsonIdentityStub, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected HttpJsonIdentityStub(
      IdentityStubSettings settings,
      ClientContext clientContext,
      HttpJsonStubCallableFactory callableFactory)
      throws IOException {
    this.callableFactory = callableFactory;

    HttpJsonCallSettings<CreateUserRequest, User> createUserTransportSettings =
        HttpJsonCallSettings.<CreateUserRequest, User>newBuilder()
            .setMethodDescriptor(createUserMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<GetUserRequest, User> getUserTransportSettings =
        HttpJsonCallSettings.<GetUserRequest, User>newBuilder()
            .setMethodDescriptor(getUserMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<UpdateUserRequest, User> updateUserTransportSettings =
        HttpJsonCallSettings.<UpdateUserRequest, User>newBuilder()
            .setMethodDescriptor(updateUserMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<DeleteUserRequest, Empty> deleteUserTransportSettings =
        HttpJsonCallSettings.<DeleteUserRequest, Empty>newBuilder()
            .setMethodDescriptor(deleteUserMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();
    HttpJsonCallSettings<ListUsersRequest, ListUsersResponse> listUsersTransportSettings =
        HttpJsonCallSettings.<ListUsersRequest, ListUsersResponse>newBuilder()
            .setMethodDescriptor(listUsersMethodDescriptor)
            .setTypeRegistry(typeRegistry)
            .build();

    this.createUserCallable =
        callableFactory.createUnaryCallable(
            createUserTransportSettings, settings.createUserSettings(), clientContext);
    this.getUserCallable =
        callableFactory.createUnaryCallable(
            getUserTransportSettings, settings.getUserSettings(), clientContext);
    this.updateUserCallable =
        callableFactory.createUnaryCallable(
            updateUserTransportSettings, settings.updateUserSettings(), clientContext);
    this.deleteUserCallable =
        callableFactory.createUnaryCallable(
            deleteUserTransportSettings, settings.deleteUserSettings(), clientContext);
    this.listUsersCallable =
        callableFactory.createUnaryCallable(
            listUsersTransportSettings, settings.listUsersSettings(), clientContext);
    this.listUsersPagedCallable =
        callableFactory.createPagedCallable(
            listUsersTransportSettings, settings.listUsersSettings(), clientContext);

    this.backgroundResources =
        new BackgroundResourceAggregation(clientContext.getBackgroundResources());
  }

  @InternalApi
  public static List<ApiMethodDescriptor> getMethodDescriptors() {
    List<ApiMethodDescriptor> methodDescriptors = new ArrayList<>();
    methodDescriptors.add(createUserMethodDescriptor);
    methodDescriptors.add(getUserMethodDescriptor);
    methodDescriptors.add(updateUserMethodDescriptor);
    methodDescriptors.add(deleteUserMethodDescriptor);
    methodDescriptors.add(listUsersMethodDescriptor);
    return methodDescriptors;
  }

  @Override
  public UnaryCallable<CreateUserRequest, User> createUserCallable() {
    return createUserCallable;
  }

  @Override
  public UnaryCallable<GetUserRequest, User> getUserCallable() {
    return getUserCallable;
  }

  @Override
  public UnaryCallable<UpdateUserRequest, User> updateUserCallable() {
    return updateUserCallable;
  }

  @Override
  public UnaryCallable<DeleteUserRequest, Empty> deleteUserCallable() {
    return deleteUserCallable;
  }

  @Override
  public UnaryCallable<ListUsersRequest, ListUsersResponse> listUsersCallable() {
    return listUsersCallable;
  }

  @Override
  public UnaryCallable<ListUsersRequest, ListUsersPagedResponse> listUsersPagedCallable() {
    return listUsersPagedCallable;
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
