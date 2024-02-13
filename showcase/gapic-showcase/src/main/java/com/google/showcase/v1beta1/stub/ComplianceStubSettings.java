/*
 * Copyright 2024 Google LLC
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

import static com.google.showcase.v1beta1.ComplianceClient.ListLocationsPagedResponse;

import com.google.api.core.ApiFunction;
import com.google.api.core.ApiFuture;
import com.google.api.core.BetaApi;
import com.google.api.gax.core.GaxProperties;
import com.google.api.gax.core.GoogleCredentialsProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.httpjson.GaxHttpJsonProperties;
import com.google.api.gax.httpjson.HttpJsonTransportChannel;
import com.google.api.gax.httpjson.InstantiatingHttpJsonChannelProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.ApiCallContext;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.PageContext;
import com.google.api.gax.rpc.PagedCallSettings;
import com.google.api.gax.rpc.PagedListDescriptor;
import com.google.api.gax.rpc.PagedListResponseFactory;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.StubSettings;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.showcase.v1beta1.EnumRequest;
import com.google.showcase.v1beta1.EnumResponse;
import com.google.showcase.v1beta1.RepeatRequest;
import com.google.showcase.v1beta1.RepeatResponse;
import java.io.IOException;
import java.util.List;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Settings class to configure an instance of {@link ComplianceStub}.
 *
 * <p>The default instance has everything set to sensible defaults:
 *
 * <ul>
 *   <li>The default service address (localhost) and default port (7469) are used.
 *   <li>Credentials are acquired automatically through Application Default Credentials.
 *   <li>Retries are configured for idempotent methods but not for non-idempotent methods.
 * </ul>
 *
 * <p>The builder of this class is recursive, so contained classes are themselves builders. When
 * build() is called, the tree of builders is called to create the complete settings object.
 *
 * <p>For example, to set the total timeout of repeatDataBody to 30 seconds:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * ComplianceStubSettings.Builder complianceSettingsBuilder = ComplianceStubSettings.newBuilder();
 * complianceSettingsBuilder
 *     .repeatDataBodySettings()
 *     .setRetrySettings(
 *         complianceSettingsBuilder
 *             .repeatDataBodySettings()
 *             .getRetrySettings()
 *             .toBuilder()
 *             .setTotalTimeout(Duration.ofSeconds(30))
 *             .build());
 * ComplianceStubSettings complianceSettings = complianceSettingsBuilder.build();
 * }</pre>
 */
@BetaApi
@Generated("by gapic-generator-java")
public class ComplianceStubSettings extends StubSettings<ComplianceStubSettings> {
  /** The default scopes of the service. */
  private static final ImmutableList<String> DEFAULT_SERVICE_SCOPES =
      ImmutableList.<String>builder().build();

  private final UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataBodySettings;
  private final UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyInfoSettings;
  private final UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataQuerySettings;
  private final UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataSimplePathSettings;
  private final UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataPathResourceSettings;
  private final UnaryCallSettings<RepeatRequest, RepeatResponse>
      repeatDataPathTrailingResourceSettings;
  private final UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyPutSettings;
  private final UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyPatchSettings;
  private final UnaryCallSettings<EnumRequest, EnumResponse> getEnumSettings;
  private final UnaryCallSettings<EnumResponse, EnumResponse> verifyEnumSettings;
  private final PagedCallSettings<
          ListLocationsRequest, ListLocationsResponse, ListLocationsPagedResponse>
      listLocationsSettings;
  private final UnaryCallSettings<GetLocationRequest, Location> getLocationSettings;
  private final UnaryCallSettings<SetIamPolicyRequest, Policy> setIamPolicySettings;
  private final UnaryCallSettings<GetIamPolicyRequest, Policy> getIamPolicySettings;
  private final UnaryCallSettings<TestIamPermissionsRequest, TestIamPermissionsResponse>
      testIamPermissionsSettings;

  private static final PagedListDescriptor<ListLocationsRequest, ListLocationsResponse, Location>
      LIST_LOCATIONS_PAGE_STR_DESC =
          new PagedListDescriptor<ListLocationsRequest, ListLocationsResponse, Location>() {
            @Override
            public String emptyToken() {
              return "";
            }

            @Override
            public ListLocationsRequest injectToken(ListLocationsRequest payload, String token) {
              return ListLocationsRequest.newBuilder(payload).setPageToken(token).build();
            }

            @Override
            public ListLocationsRequest injectPageSize(ListLocationsRequest payload, int pageSize) {
              return ListLocationsRequest.newBuilder(payload).setPageSize(pageSize).build();
            }

            @Override
            public Integer extractPageSize(ListLocationsRequest payload) {
              return payload.getPageSize();
            }

            @Override
            public String extractNextToken(ListLocationsResponse payload) {
              return payload.getNextPageToken();
            }

            @Override
            public Iterable<Location> extractResources(ListLocationsResponse payload) {
              return payload.getLocationsList() == null
                  ? ImmutableList.<Location>of()
                  : payload.getLocationsList();
            }
          };

  private static final PagedListResponseFactory<
          ListLocationsRequest, ListLocationsResponse, ListLocationsPagedResponse>
      LIST_LOCATIONS_PAGE_STR_FACT =
          new PagedListResponseFactory<
              ListLocationsRequest, ListLocationsResponse, ListLocationsPagedResponse>() {
            @Override
            public ApiFuture<ListLocationsPagedResponse> getFuturePagedResponse(
                UnaryCallable<ListLocationsRequest, ListLocationsResponse> callable,
                ListLocationsRequest request,
                ApiCallContext context,
                ApiFuture<ListLocationsResponse> futureResponse) {
              PageContext<ListLocationsRequest, ListLocationsResponse, Location> pageContext =
                  PageContext.create(callable, LIST_LOCATIONS_PAGE_STR_DESC, request, context);
              return ListLocationsPagedResponse.createAsync(pageContext, futureResponse);
            }
          };

  /** Returns the object with the settings used for calls to repeatDataBody. */
  public UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataBodySettings() {
    return repeatDataBodySettings;
  }

  /** Returns the object with the settings used for calls to repeatDataBodyInfo. */
  public UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyInfoSettings() {
    return repeatDataBodyInfoSettings;
  }

  /** Returns the object with the settings used for calls to repeatDataQuery. */
  public UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataQuerySettings() {
    return repeatDataQuerySettings;
  }

  /** Returns the object with the settings used for calls to repeatDataSimplePath. */
  public UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataSimplePathSettings() {
    return repeatDataSimplePathSettings;
  }

  /** Returns the object with the settings used for calls to repeatDataPathResource. */
  public UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataPathResourceSettings() {
    return repeatDataPathResourceSettings;
  }

  /** Returns the object with the settings used for calls to repeatDataPathTrailingResource. */
  public UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataPathTrailingResourceSettings() {
    return repeatDataPathTrailingResourceSettings;
  }

  /** Returns the object with the settings used for calls to repeatDataBodyPut. */
  public UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyPutSettings() {
    return repeatDataBodyPutSettings;
  }

  /** Returns the object with the settings used for calls to repeatDataBodyPatch. */
  public UnaryCallSettings<RepeatRequest, RepeatResponse> repeatDataBodyPatchSettings() {
    return repeatDataBodyPatchSettings;
  }

  /** Returns the object with the settings used for calls to getEnum. */
  public UnaryCallSettings<EnumRequest, EnumResponse> getEnumSettings() {
    return getEnumSettings;
  }

  /** Returns the object with the settings used for calls to verifyEnum. */
  public UnaryCallSettings<EnumResponse, EnumResponse> verifyEnumSettings() {
    return verifyEnumSettings;
  }

  /** Returns the object with the settings used for calls to listLocations. */
  public PagedCallSettings<ListLocationsRequest, ListLocationsResponse, ListLocationsPagedResponse>
      listLocationsSettings() {
    return listLocationsSettings;
  }

  /** Returns the object with the settings used for calls to getLocation. */
  public UnaryCallSettings<GetLocationRequest, Location> getLocationSettings() {
    return getLocationSettings;
  }

  /** Returns the object with the settings used for calls to setIamPolicy. */
  public UnaryCallSettings<SetIamPolicyRequest, Policy> setIamPolicySettings() {
    return setIamPolicySettings;
  }

  /** Returns the object with the settings used for calls to getIamPolicy. */
  public UnaryCallSettings<GetIamPolicyRequest, Policy> getIamPolicySettings() {
    return getIamPolicySettings;
  }

  /** Returns the object with the settings used for calls to testIamPermissions. */
  public UnaryCallSettings<TestIamPermissionsRequest, TestIamPermissionsResponse>
      testIamPermissionsSettings() {
    return testIamPermissionsSettings;
  }

  public ComplianceStub createStub() throws IOException {
    if (getTransportChannelProvider()
        .getTransportName()
        .equals(GrpcTransportChannel.getGrpcTransportName())) {
      return GrpcComplianceStub.create(this);
    }
    if (getTransportChannelProvider()
        .getTransportName()
        .equals(HttpJsonTransportChannel.getHttpJsonTransportName())) {
      return HttpJsonComplianceStub.create(this);
    }
    throw new UnsupportedOperationException(
        String.format(
            "Transport not supported: %s", getTransportChannelProvider().getTransportName()));
  }

  /** Returns the endpoint set by the user or the the service's default endpoint. */
  @Override
  public String getEndpoint() {
    if (super.getEndpoint() != null) {
      return super.getEndpoint();
    }
    return getDefaultEndpoint();
  }

  /** Returns a builder for the default ExecutorProvider for this service. */
  public static InstantiatingExecutorProvider.Builder defaultExecutorProviderBuilder() {
    return InstantiatingExecutorProvider.newBuilder();
  }

  /** Returns the default service endpoint. */
  public static String getDefaultEndpoint() {
    return "localhost:7469";
  }

  /** Returns the default mTLS service endpoint. */
  public static String getDefaultMtlsEndpoint() {
    return "localhost:7469";
  }

  /** Returns the default service scopes. */
  public static List<String> getDefaultServiceScopes() {
    return DEFAULT_SERVICE_SCOPES;
  }

  /** Returns a builder for the default credentials for this service. */
  public static GoogleCredentialsProvider.Builder defaultCredentialsProviderBuilder() {
    return GoogleCredentialsProvider.newBuilder()
        .setScopesToApply(DEFAULT_SERVICE_SCOPES)
        .setUseJwtAccessWithScope(true);
  }

  /** Returns a builder for the default gRPC ChannelProvider for this service. */
  public static InstantiatingGrpcChannelProvider.Builder defaultGrpcTransportProviderBuilder() {
    return InstantiatingGrpcChannelProvider.newBuilder()
        .setMaxInboundMessageSize(Integer.MAX_VALUE);
  }

  /** Returns a builder for the default REST ChannelProvider for this service. */
  @BetaApi
  public static InstantiatingHttpJsonChannelProvider.Builder
      defaultHttpJsonTransportProviderBuilder() {
    return InstantiatingHttpJsonChannelProvider.newBuilder();
  }

  public static TransportChannelProvider defaultTransportChannelProvider() {
    return defaultGrpcTransportProviderBuilder().build();
  }

  public static ApiClientHeaderProvider.Builder defaultGrpcApiClientHeaderProviderBuilder() {
    return ApiClientHeaderProvider.newBuilder()
        .setGeneratedLibToken(
            "gapic", GaxProperties.getLibraryVersion(ComplianceStubSettings.class))
        .setTransportToken(
            GaxGrpcProperties.getGrpcTokenName(), GaxGrpcProperties.getGrpcVersion());
  }

  public static ApiClientHeaderProvider.Builder defaultHttpJsonApiClientHeaderProviderBuilder() {
    return ApiClientHeaderProvider.newBuilder()
        .setGeneratedLibToken(
            "gapic", GaxProperties.getLibraryVersion(ComplianceStubSettings.class))
        .setTransportToken(
            GaxHttpJsonProperties.getHttpJsonTokenName(),
            GaxHttpJsonProperties.getHttpJsonVersion());
  }

  public static ApiClientHeaderProvider.Builder defaultApiClientHeaderProviderBuilder() {
    return ComplianceStubSettings.defaultGrpcApiClientHeaderProviderBuilder();
  }

  /** Returns a new gRPC builder for this class. */
  public static Builder newBuilder() {
    return Builder.createDefault();
  }

  /** Returns a new REST builder for this class. */
  public static Builder newHttpJsonBuilder() {
    return Builder.createHttpJsonDefault();
  }

  /** Returns a new builder for this class. */
  public static Builder newBuilder(ClientContext clientContext) {
    return new Builder(clientContext);
  }

  /** Returns a builder containing all the values of this settings class. */
  public Builder toBuilder() {
    return new Builder(this);
  }

  protected ComplianceStubSettings(Builder settingsBuilder) throws IOException {
    super(settingsBuilder);

    repeatDataBodySettings = settingsBuilder.repeatDataBodySettings().build();
    repeatDataBodyInfoSettings = settingsBuilder.repeatDataBodyInfoSettings().build();
    repeatDataQuerySettings = settingsBuilder.repeatDataQuerySettings().build();
    repeatDataSimplePathSettings = settingsBuilder.repeatDataSimplePathSettings().build();
    repeatDataPathResourceSettings = settingsBuilder.repeatDataPathResourceSettings().build();
    repeatDataPathTrailingResourceSettings =
        settingsBuilder.repeatDataPathTrailingResourceSettings().build();
    repeatDataBodyPutSettings = settingsBuilder.repeatDataBodyPutSettings().build();
    repeatDataBodyPatchSettings = settingsBuilder.repeatDataBodyPatchSettings().build();
    getEnumSettings = settingsBuilder.getEnumSettings().build();
    verifyEnumSettings = settingsBuilder.verifyEnumSettings().build();
    listLocationsSettings = settingsBuilder.listLocationsSettings().build();
    getLocationSettings = settingsBuilder.getLocationSettings().build();
    setIamPolicySettings = settingsBuilder.setIamPolicySettings().build();
    getIamPolicySettings = settingsBuilder.getIamPolicySettings().build();
    testIamPermissionsSettings = settingsBuilder.testIamPermissionsSettings().build();
  }

  /** Builder for ComplianceStubSettings. */
  public static class Builder extends StubSettings.Builder<ComplianceStubSettings, Builder> {
    private final ImmutableList<UnaryCallSettings.Builder<?, ?>> unaryMethodSettingsBuilders;
    private final UnaryCallSettings.Builder<RepeatRequest, RepeatResponse> repeatDataBodySettings;
    private final UnaryCallSettings.Builder<RepeatRequest, RepeatResponse>
        repeatDataBodyInfoSettings;
    private final UnaryCallSettings.Builder<RepeatRequest, RepeatResponse> repeatDataQuerySettings;
    private final UnaryCallSettings.Builder<RepeatRequest, RepeatResponse>
        repeatDataSimplePathSettings;
    private final UnaryCallSettings.Builder<RepeatRequest, RepeatResponse>
        repeatDataPathResourceSettings;
    private final UnaryCallSettings.Builder<RepeatRequest, RepeatResponse>
        repeatDataPathTrailingResourceSettings;
    private final UnaryCallSettings.Builder<RepeatRequest, RepeatResponse>
        repeatDataBodyPutSettings;
    private final UnaryCallSettings.Builder<RepeatRequest, RepeatResponse>
        repeatDataBodyPatchSettings;
    private final UnaryCallSettings.Builder<EnumRequest, EnumResponse> getEnumSettings;
    private final UnaryCallSettings.Builder<EnumResponse, EnumResponse> verifyEnumSettings;
    private final PagedCallSettings.Builder<
            ListLocationsRequest, ListLocationsResponse, ListLocationsPagedResponse>
        listLocationsSettings;
    private final UnaryCallSettings.Builder<GetLocationRequest, Location> getLocationSettings;
    private final UnaryCallSettings.Builder<SetIamPolicyRequest, Policy> setIamPolicySettings;
    private final UnaryCallSettings.Builder<GetIamPolicyRequest, Policy> getIamPolicySettings;
    private final UnaryCallSettings.Builder<TestIamPermissionsRequest, TestIamPermissionsResponse>
        testIamPermissionsSettings;
    private static final ImmutableMap<String, ImmutableSet<StatusCode.Code>>
        RETRYABLE_CODE_DEFINITIONS;

    static {
      ImmutableMap.Builder<String, ImmutableSet<StatusCode.Code>> definitions =
          ImmutableMap.builder();
      definitions.put("no_retry_codes", ImmutableSet.copyOf(Lists.<StatusCode.Code>newArrayList()));
      RETRYABLE_CODE_DEFINITIONS = definitions.build();
    }

    private static final ImmutableMap<String, RetrySettings> RETRY_PARAM_DEFINITIONS;

    static {
      ImmutableMap.Builder<String, RetrySettings> definitions = ImmutableMap.builder();
      RetrySettings settings = null;
      settings = RetrySettings.newBuilder().setRpcTimeoutMultiplier(1.0).build();
      definitions.put("no_retry_params", settings);
      RETRY_PARAM_DEFINITIONS = definitions.build();
    }

    protected Builder() {
      this(((ClientContext) null));
    }

    protected Builder(ClientContext clientContext) {
      super(clientContext);

      repeatDataBodySettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      repeatDataBodyInfoSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      repeatDataQuerySettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      repeatDataSimplePathSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      repeatDataPathResourceSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      repeatDataPathTrailingResourceSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      repeatDataBodyPutSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      repeatDataBodyPatchSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      getEnumSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      verifyEnumSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      listLocationsSettings = PagedCallSettings.newBuilder(LIST_LOCATIONS_PAGE_STR_FACT);
      getLocationSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      setIamPolicySettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      getIamPolicySettings = UnaryCallSettings.newUnaryCallSettingsBuilder();
      testIamPermissionsSettings = UnaryCallSettings.newUnaryCallSettingsBuilder();

      unaryMethodSettingsBuilders =
          ImmutableList.<UnaryCallSettings.Builder<?, ?>>of(
              repeatDataBodySettings,
              repeatDataBodyInfoSettings,
              repeatDataQuerySettings,
              repeatDataSimplePathSettings,
              repeatDataPathResourceSettings,
              repeatDataPathTrailingResourceSettings,
              repeatDataBodyPutSettings,
              repeatDataBodyPatchSettings,
              getEnumSettings,
              verifyEnumSettings,
              listLocationsSettings,
              getLocationSettings,
              setIamPolicySettings,
              getIamPolicySettings,
              testIamPermissionsSettings);
      initDefaults(this);
    }

    protected Builder(ComplianceStubSettings settings) {
      super(settings);

      repeatDataBodySettings = settings.repeatDataBodySettings.toBuilder();
      repeatDataBodyInfoSettings = settings.repeatDataBodyInfoSettings.toBuilder();
      repeatDataQuerySettings = settings.repeatDataQuerySettings.toBuilder();
      repeatDataSimplePathSettings = settings.repeatDataSimplePathSettings.toBuilder();
      repeatDataPathResourceSettings = settings.repeatDataPathResourceSettings.toBuilder();
      repeatDataPathTrailingResourceSettings =
          settings.repeatDataPathTrailingResourceSettings.toBuilder();
      repeatDataBodyPutSettings = settings.repeatDataBodyPutSettings.toBuilder();
      repeatDataBodyPatchSettings = settings.repeatDataBodyPatchSettings.toBuilder();
      getEnumSettings = settings.getEnumSettings.toBuilder();
      verifyEnumSettings = settings.verifyEnumSettings.toBuilder();
      listLocationsSettings = settings.listLocationsSettings.toBuilder();
      getLocationSettings = settings.getLocationSettings.toBuilder();
      setIamPolicySettings = settings.setIamPolicySettings.toBuilder();
      getIamPolicySettings = settings.getIamPolicySettings.toBuilder();
      testIamPermissionsSettings = settings.testIamPermissionsSettings.toBuilder();

      unaryMethodSettingsBuilders =
          ImmutableList.<UnaryCallSettings.Builder<?, ?>>of(
              repeatDataBodySettings,
              repeatDataBodyInfoSettings,
              repeatDataQuerySettings,
              repeatDataSimplePathSettings,
              repeatDataPathResourceSettings,
              repeatDataPathTrailingResourceSettings,
              repeatDataBodyPutSettings,
              repeatDataBodyPatchSettings,
              getEnumSettings,
              verifyEnumSettings,
              listLocationsSettings,
              getLocationSettings,
              setIamPolicySettings,
              getIamPolicySettings,
              testIamPermissionsSettings);
    }

    private static Builder createDefault() {
      Builder builder = new Builder(((ClientContext) null));

      builder.setTransportChannelProvider(defaultTransportChannelProvider());
      builder.setCredentialsProvider(defaultCredentialsProviderBuilder().build());
      builder.setInternalHeaderProvider(defaultApiClientHeaderProviderBuilder().build());
      builder.setMtlsEndpoint(getDefaultMtlsEndpoint());
      builder.setSwitchToMtlsEndpointAllowed(true);

      return initDefaults(builder);
    }

    private static Builder createHttpJsonDefault() {
      Builder builder = new Builder(((ClientContext) null));

      builder.setTransportChannelProvider(defaultHttpJsonTransportProviderBuilder().build());
      builder.setCredentialsProvider(defaultCredentialsProviderBuilder().build());
      builder.setInternalHeaderProvider(defaultHttpJsonApiClientHeaderProviderBuilder().build());
      builder.setMtlsEndpoint(getDefaultMtlsEndpoint());
      builder.setSwitchToMtlsEndpointAllowed(true);

      return initDefaults(builder);
    }

    private static Builder initDefaults(Builder builder) {
      builder
          .repeatDataBodySettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .repeatDataBodyInfoSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .repeatDataQuerySettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .repeatDataSimplePathSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .repeatDataPathResourceSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .repeatDataPathTrailingResourceSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .repeatDataBodyPutSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .repeatDataBodyPatchSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .getEnumSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .verifyEnumSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .listLocationsSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .getLocationSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .setIamPolicySettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .getIamPolicySettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      builder
          .testIamPermissionsSettings()
          .setRetryableCodes(RETRYABLE_CODE_DEFINITIONS.get("no_retry_codes"))
          .setRetrySettings(RETRY_PARAM_DEFINITIONS.get("no_retry_params"));

      return builder;
    }

    /**
     * Applies the given settings updater function to all of the unary API methods in this service.
     *
     * <p>Note: This method does not support applying settings to streaming methods.
     */
    public Builder applyToAllUnaryMethods(
        ApiFunction<UnaryCallSettings.Builder<?, ?>, Void> settingsUpdater) {
      super.applyToAllUnaryMethods(unaryMethodSettingsBuilders, settingsUpdater);
      return this;
    }

    public ImmutableList<UnaryCallSettings.Builder<?, ?>> unaryMethodSettingsBuilders() {
      return unaryMethodSettingsBuilders;
    }

    /** Returns the builder for the settings used for calls to repeatDataBody. */
    public UnaryCallSettings.Builder<RepeatRequest, RepeatResponse> repeatDataBodySettings() {
      return repeatDataBodySettings;
    }

    /** Returns the builder for the settings used for calls to repeatDataBodyInfo. */
    public UnaryCallSettings.Builder<RepeatRequest, RepeatResponse> repeatDataBodyInfoSettings() {
      return repeatDataBodyInfoSettings;
    }

    /** Returns the builder for the settings used for calls to repeatDataQuery. */
    public UnaryCallSettings.Builder<RepeatRequest, RepeatResponse> repeatDataQuerySettings() {
      return repeatDataQuerySettings;
    }

    /** Returns the builder for the settings used for calls to repeatDataSimplePath. */
    public UnaryCallSettings.Builder<RepeatRequest, RepeatResponse> repeatDataSimplePathSettings() {
      return repeatDataSimplePathSettings;
    }

    /** Returns the builder for the settings used for calls to repeatDataPathResource. */
    public UnaryCallSettings.Builder<RepeatRequest, RepeatResponse>
        repeatDataPathResourceSettings() {
      return repeatDataPathResourceSettings;
    }

    /** Returns the builder for the settings used for calls to repeatDataPathTrailingResource. */
    public UnaryCallSettings.Builder<RepeatRequest, RepeatResponse>
        repeatDataPathTrailingResourceSettings() {
      return repeatDataPathTrailingResourceSettings;
    }

    /** Returns the builder for the settings used for calls to repeatDataBodyPut. */
    public UnaryCallSettings.Builder<RepeatRequest, RepeatResponse> repeatDataBodyPutSettings() {
      return repeatDataBodyPutSettings;
    }

    /** Returns the builder for the settings used for calls to repeatDataBodyPatch. */
    public UnaryCallSettings.Builder<RepeatRequest, RepeatResponse> repeatDataBodyPatchSettings() {
      return repeatDataBodyPatchSettings;
    }

    /** Returns the builder for the settings used for calls to getEnum. */
    public UnaryCallSettings.Builder<EnumRequest, EnumResponse> getEnumSettings() {
      return getEnumSettings;
    }

    /** Returns the builder for the settings used for calls to verifyEnum. */
    public UnaryCallSettings.Builder<EnumResponse, EnumResponse> verifyEnumSettings() {
      return verifyEnumSettings;
    }

    /** Returns the builder for the settings used for calls to listLocations. */
    public PagedCallSettings.Builder<
            ListLocationsRequest, ListLocationsResponse, ListLocationsPagedResponse>
        listLocationsSettings() {
      return listLocationsSettings;
    }

    /** Returns the builder for the settings used for calls to getLocation. */
    public UnaryCallSettings.Builder<GetLocationRequest, Location> getLocationSettings() {
      return getLocationSettings;
    }

    /** Returns the builder for the settings used for calls to setIamPolicy. */
    public UnaryCallSettings.Builder<SetIamPolicyRequest, Policy> setIamPolicySettings() {
      return setIamPolicySettings;
    }

    /** Returns the builder for the settings used for calls to getIamPolicy. */
    public UnaryCallSettings.Builder<GetIamPolicyRequest, Policy> getIamPolicySettings() {
      return getIamPolicySettings;
    }

    /** Returns the builder for the settings used for calls to testIamPermissions. */
    public UnaryCallSettings.Builder<TestIamPermissionsRequest, TestIamPermissionsResponse>
        testIamPermissionsSettings() {
      return testIamPermissionsSettings;
    }

    /** Returns the endpoint set by the user or the the service's default endpoint. */
    @Override
    public String getEndpoint() {
      if (super.getEndpoint() != null) {
        return super.getEndpoint();
      }
      return getDefaultEndpoint();
    }

    @Override
    public ComplianceStubSettings build() throws IOException {
      return new ComplianceStubSettings(this);
    }
  }
}
