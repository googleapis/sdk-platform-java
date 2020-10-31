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

package com.google.logging.v2;

import static com.google.logging.v2.ConfigServiceV2Client.ListBucketsPagedResponse;
import static com.google.logging.v2.ConfigServiceV2Client.ListExclusionsPagedResponse;
import static com.google.logging.v2.ConfigServiceV2Client.ListSinksPagedResponse;

import com.google.api.core.ApiFunction;
import com.google.api.core.BetaApi;
import com.google.api.gax.core.GoogleCredentialsProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.ClientSettings;
import com.google.api.gax.rpc.PagedCallSettings;
import com.google.api.gax.rpc.StubSettings;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.logging.v2.stub.ConfigServiceV2StubSettings;
import com.google.protobuf.Empty;
import java.io.IOException;
import java.util.List;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Settings class to configure an instance of {@link ConfigServiceV2Client}.
 *
 * <p>The default instance has everything set to sensible defaults:
 *
 * <ul>
 *   <li>The default service address (logging.googleapis.com) and default port (443) are used.
 *   <li>Credentials are acquired automatically through Application Default Credentials.
 *   <li>Retries are configured for idempotent methods but not for non-idempotent methods.
 * </ul>
 *
 * <p>The builder of this class is recursive, so contained classes are themselves builders. When
 * build() is called, the tree of builders is called to create the complete settings object.
 *
 * <p>For example, to set the total timeout of listBuckets to 30 seconds:
 */
@Generated("by gapic-generator-java")
public class ConfigServiceV2Settings extends ClientSettings<ConfigServiceV2Settings> {

  /** Returns the object with the settings used for calls to listBuckets. */
  public PagedCallSettings<ListBucketsRequest, ListBucketsResponse, ListBucketsPagedResponse>
      listBucketsSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).listBucketsSettings();
  }

  /** Returns the object with the settings used for calls to getBucket. */
  public UnaryCallSettings<GetBucketRequest, LogBucket> getBucketSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).getBucketSettings();
  }

  /** Returns the object with the settings used for calls to updateBucket. */
  public UnaryCallSettings<UpdateBucketRequest, LogBucket> updateBucketSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).updateBucketSettings();
  }

  /** Returns the object with the settings used for calls to listSinks. */
  public PagedCallSettings<ListSinksRequest, ListSinksResponse, ListSinksPagedResponse>
      listSinksSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).listSinksSettings();
  }

  /** Returns the object with the settings used for calls to getSink. */
  public UnaryCallSettings<GetSinkRequest, LogSink> getSinkSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).getSinkSettings();
  }

  /** Returns the object with the settings used for calls to createSink. */
  public UnaryCallSettings<CreateSinkRequest, LogSink> createSinkSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).createSinkSettings();
  }

  /** Returns the object with the settings used for calls to updateSink. */
  public UnaryCallSettings<UpdateSinkRequest, LogSink> updateSinkSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).updateSinkSettings();
  }

  /** Returns the object with the settings used for calls to deleteSink. */
  public UnaryCallSettings<DeleteSinkRequest, Empty> deleteSinkSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).deleteSinkSettings();
  }

  /** Returns the object with the settings used for calls to listExclusions. */
  public PagedCallSettings<
          ListExclusionsRequest, ListExclusionsResponse, ListExclusionsPagedResponse>
      listExclusionsSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).listExclusionsSettings();
  }

  /** Returns the object with the settings used for calls to getExclusion. */
  public UnaryCallSettings<GetExclusionRequest, LogExclusion> getExclusionSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).getExclusionSettings();
  }

  /** Returns the object with the settings used for calls to createExclusion. */
  public UnaryCallSettings<CreateExclusionRequest, LogExclusion> createExclusionSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).createExclusionSettings();
  }

  /** Returns the object with the settings used for calls to updateExclusion. */
  public UnaryCallSettings<UpdateExclusionRequest, LogExclusion> updateExclusionSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).updateExclusionSettings();
  }

  /** Returns the object with the settings used for calls to deleteExclusion. */
  public UnaryCallSettings<DeleteExclusionRequest, Empty> deleteExclusionSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).deleteExclusionSettings();
  }

  /** Returns the object with the settings used for calls to getCmekSettings. */
  public UnaryCallSettings<GetCmekSettingsRequest, CmekSettings> getCmekSettingsSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).getCmekSettingsSettings();
  }

  /** Returns the object with the settings used for calls to updateCmekSettings. */
  public UnaryCallSettings<UpdateCmekSettingsRequest, CmekSettings> updateCmekSettingsSettings() {
    return ((ConfigServiceV2StubSettings) getStubSettings()).updateCmekSettingsSettings();
  }

  public static final ConfigServiceV2Settings create(ConfigServiceV2StubSettings stub)
      throws IOException {
    return new ConfigServiceV2Settings.Builder(stub.toBuilder()).build();
  }

  /** Returns a builder for the default ExecutorProvider for this service. */
  public static InstantiatingExecutorProvider.Builder defaultExecutorProviderBuilder() {
    return ConfigServiceV2StubSettings.defaultExecutorProviderBuilder();
  }

  /** Returns the default service endpoint. */
  public static String getDefaultEndpoint() {
    return ConfigServiceV2StubSettings.getDefaultEndpoint();
  }

  /** Returns the default service scopes. */
  public static List<String> getDefaultServiceScopes() {
    return ConfigServiceV2StubSettings.getDefaultServiceScopes();
  }

  /** Returns a builder for the default credentials for this service. */
  public static GoogleCredentialsProvider.Builder defaultCredentialsProviderBuilder() {
    return ConfigServiceV2StubSettings.defaultCredentialsProviderBuilder();
  }

  /** Returns a builder for the default ChannelProvider for this service. */
  public static InstantiatingGrpcChannelProvider.Builder defaultGrpcTransportProviderBuilder() {
    return ConfigServiceV2StubSettings.defaultGrpcTransportProviderBuilder();
  }

  public static TransportChannelProvider defaultTransportChannelProvider() {
    return ConfigServiceV2StubSettings.defaultTransportChannelProvider();
  }

  @BetaApi("The surface for customizing headers is not stable yet and may change in the future.")
  public static ApiClientHeaderProvider.Builder defaultApiClientHeaderProviderBuilder() {
    return ConfigServiceV2StubSettings.defaultApiClientHeaderProviderBuilder();
  }

  /** Returns a new builder for this class. */
  public static Builder newBuilder() {
    return Builder.createDefault();
  }

  /** Returns a new builder for this class. */
  public static Builder newBuilder(ClientContext clientContext) {
    return new Builder(clientContext);
  }

  /** Returns a builder containing all the values of this settings class. */
  public Builder toBuilder() {
    return new Builder(this);
  }

  protected ConfigServiceV2Settings(Builder settingsBuilder) throws IOException {
    super(settingsBuilder);
  }

  /** Builder for ConfigServiceV2Settings. */
  public static class Builder extends ClientSettings.Builder<ConfigServiceV2Settings, Builder> {

    protected Builder() throws IOException {
      this(((ClientContext) null));
    }

    protected Builder(ClientContext clientContext) {
      super(ConfigServiceV2StubSettings.newBuilder(clientContext));
    }

    protected Builder(ConfigServiceV2Settings settings) {
      super(settings.getStubSettings().toBuilder());
    }

    protected Builder(ConfigServiceV2StubSettings.Builder stubSettings) {
      super(stubSettings);
    }

    private static Builder createDefault() {
      return new Builder(ConfigServiceV2StubSettings.newBuilder());
    }

    public ConfigServiceV2StubSettings.Builder getStubSettingsBuilder() {
      return ((ConfigServiceV2StubSettings.Builder) getStubSettings());
    }

    // NEXT_MAJOR_VER: remove 'throws Exception'.
    /**
     * Applies the given settings updater function to all of the unary API methods in this service.
     *
     * <p>Note: This method does not support applying settings to streaming methods.
     */
    public Builder applyToAllUnaryMethods(
        ApiFunction<UnaryCallSettings.Builder<?, ?>, Void> settingsUpdater) throws Exception {
      super.applyToAllUnaryMethods(
          getStubSettingsBuilder().unaryMethodSettingsBuilders(), settingsUpdater);
      return this;
    }

    /** Returns the builder for the settings used for calls to listBuckets. */
    public PagedCallSettings.Builder<
            ListBucketsRequest, ListBucketsResponse, ListBucketsPagedResponse>
        listBucketsSettings() {
      return getStubSettingsBuilder().listBucketsSettings();
    }

    /** Returns the builder for the settings used for calls to getBucket. */
    public UnaryCallSettings.Builder<GetBucketRequest, LogBucket> getBucketSettings() {
      return getStubSettingsBuilder().getBucketSettings();
    }

    /** Returns the builder for the settings used for calls to updateBucket. */
    public UnaryCallSettings.Builder<UpdateBucketRequest, LogBucket> updateBucketSettings() {
      return getStubSettingsBuilder().updateBucketSettings();
    }

    /** Returns the builder for the settings used for calls to listSinks. */
    public PagedCallSettings.Builder<ListSinksRequest, ListSinksResponse, ListSinksPagedResponse>
        listSinksSettings() {
      return getStubSettingsBuilder().listSinksSettings();
    }

    /** Returns the builder for the settings used for calls to getSink. */
    public UnaryCallSettings.Builder<GetSinkRequest, LogSink> getSinkSettings() {
      return getStubSettingsBuilder().getSinkSettings();
    }

    /** Returns the builder for the settings used for calls to createSink. */
    public UnaryCallSettings.Builder<CreateSinkRequest, LogSink> createSinkSettings() {
      return getStubSettingsBuilder().createSinkSettings();
    }

    /** Returns the builder for the settings used for calls to updateSink. */
    public UnaryCallSettings.Builder<UpdateSinkRequest, LogSink> updateSinkSettings() {
      return getStubSettingsBuilder().updateSinkSettings();
    }

    /** Returns the builder for the settings used for calls to deleteSink. */
    public UnaryCallSettings.Builder<DeleteSinkRequest, Empty> deleteSinkSettings() {
      return getStubSettingsBuilder().deleteSinkSettings();
    }

    /** Returns the builder for the settings used for calls to listExclusions. */
    public PagedCallSettings.Builder<
            ListExclusionsRequest, ListExclusionsResponse, ListExclusionsPagedResponse>
        listExclusionsSettings() {
      return getStubSettingsBuilder().listExclusionsSettings();
    }

    /** Returns the builder for the settings used for calls to getExclusion. */
    public UnaryCallSettings.Builder<GetExclusionRequest, LogExclusion> getExclusionSettings() {
      return getStubSettingsBuilder().getExclusionSettings();
    }

    /** Returns the builder for the settings used for calls to createExclusion. */
    public UnaryCallSettings.Builder<CreateExclusionRequest, LogExclusion>
        createExclusionSettings() {
      return getStubSettingsBuilder().createExclusionSettings();
    }

    /** Returns the builder for the settings used for calls to updateExclusion. */
    public UnaryCallSettings.Builder<UpdateExclusionRequest, LogExclusion>
        updateExclusionSettings() {
      return getStubSettingsBuilder().updateExclusionSettings();
    }

    /** Returns the builder for the settings used for calls to deleteExclusion. */
    public UnaryCallSettings.Builder<DeleteExclusionRequest, Empty> deleteExclusionSettings() {
      return getStubSettingsBuilder().deleteExclusionSettings();
    }

    /** Returns the builder for the settings used for calls to getCmekSettings. */
    public UnaryCallSettings.Builder<GetCmekSettingsRequest, CmekSettings>
        getCmekSettingsSettings() {
      return getStubSettingsBuilder().getCmekSettingsSettings();
    }

    /** Returns the builder for the settings used for calls to updateCmekSettings. */
    public UnaryCallSettings.Builder<UpdateCmekSettingsRequest, CmekSettings>
        updateCmekSettingsSettings() {
      return getStubSettingsBuilder().updateCmekSettingsSettings();
    }

    @Override
    public ConfigServiceV2Settings build() throws IOException {
      return new ConfigServiceV2Settings(this);
    }
  }
}
