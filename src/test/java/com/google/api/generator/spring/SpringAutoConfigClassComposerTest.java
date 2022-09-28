// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.spring;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.Transport;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.protoparser.ServiceConfigParser;
import com.google.api.generator.spring.composer.SpringAutoConfigClassComposer;
import com.google.api.generator.spring.composer.SpringPropertiesClassComposer;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class SpringAutoConfigClassComposerTest {
  private ServiceDescriptor echoService;
  private FileDescriptor echoFileDescriptor;

  @Before
  public void setUp() {
    echoFileDescriptor = EchoOuterClass.getDescriptor();

    ServiceDescriptor serviceDescriptor = echoFileDescriptor.getServices().get(0);
    // Assert.assertEquals(serviceDescriptor.getName(), "Bookshop");
    echoService = echoFileDescriptor.getServices().get(0);
    assertEquals(echoService.getName(), "Echo");
  }

  @Test
  public void generateAutoConfigClasses() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);

    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<Service> services =
        Parser.parseService(
            echoFileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    String jsonFilename = "retrying_grpc_service_config.json";
    Path jsonPath = Paths.get(TestProtoLoader.instance().getTestFilesDirectory(), jsonFilename);
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(serviceConfigOpt.isPresent());
    GapicServiceConfig serviceConfig = serviceConfigOpt.get();

    GapicContext context =
        GapicContext.builder()
            .setMessages(messageTypes)
            .setResourceNames(resourceNames)
            .setServices(services)
            .setHelperResourceNames(outputResourceNames)
            .setTransport(Transport.GRPC)
            .setServiceConfig(serviceConfig)
            .build();

    Service echoProtoService = services.get(0);
    GapicClass clazz = SpringAutoConfigClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_AUTOCONFIG_CLASS_STRING, visitor.write());
  }

  @Test
  public void generatePropertiesTest() {

    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);

    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<Service> services =
        Parser.parseService(
            echoFileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    String jsonFilename = "showcase_grpc_service_config.json";
    Path jsonPath = Paths.get(TestProtoLoader.instance().getTestFilesDirectory(), jsonFilename);
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(serviceConfigOpt.isPresent());
    GapicServiceConfig serviceConfig = serviceConfigOpt.get();

    GapicContext context =
        GapicContext.builder()
            .setMessages(messageTypes)
            .setResourceNames(resourceNames)
            .setServices(services)
            .setHelperResourceNames(outputResourceNames)
            .setTransport(Transport.GRPC)
            .setServiceConfig(serviceConfig)
            .build();

    Service echoProtoService = services.get(0);

    GapicClass clazz = SpringPropertiesClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_PROPERTY_CLASS_STRING, visitor.write());
  }

  protected static final String EXPECTED_AUTOCONFIG_CLASS_STRING =
      "package com.google.showcase.v1beta1.spring;\n"
          + "\n"
          + "import com.google.api.gax.core.CredentialsProvider;\n"
          + "import com.google.api.gax.core.ExecutorProvider;\n"
          + "import com.google.api.gax.httpjson.InstantiatingHttpJsonChannelProvider;\n"
          + "import com.google.api.gax.retrying.RetrySettings;\n"
          + "import com.google.api.gax.rpc.TransportChannelProvider;\n"
          + "import com.google.cloud.spring.core.DefaultCredentialsProvider;\n"
          + "import com.google.common.collect.ImmutableMap;\n"
          + "import com.google.showcase.v1beta1.EchoClient;\n"
          + "import com.google.showcase.v1beta1.EchoSettings;\n"
          + "import java.io.IOException;\n"
          + "import javax.annotation.Generated;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;\n"
          + "import org.springframework.boot.context.properties.EnableConfigurationProperties;\n"
          + "import org.springframework.context.annotation.Bean;\n"
          + "import org.springframework.context.annotation.Configuration;\n"
          + "\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "@Configuration(proxyBeanMethods = false)\n"
          + "@ConditionalOnClass(EchoClient.class)\n"
          + "@ConditionalOnProperty(\n"
          + "    value = \"spring.cloud.gcp.autoconfig.showcase.echo.enabled\",\n"
          + "    matchIfMissing = false)\n"
          + "@EnableConfigurationProperties(EchoSpringProperties.class)\n"
          + "public class EchoSpringAutoConfiguration {\n"
          + "  private final EchoSpringProperties clientProperties;\n"
          + "  private static final ImmutableMap<String, RetrySettings> RETRY_PARAM_DEFINITIONS;\n"
          + "\n"
          + "  static {\n"
          + "    ImmutableMap.Builder<String, RetrySettings> definitions = ImmutableMap.builder();\n"
          + "    RetrySettings settings = null;\n"
          + "    settings = RetrySettings.newBuilder().setRpcTimeoutMultiplier(1.0).build();\n"
          + "    definitions.put(\"no_retry_params\", settings);\n"
          + "    RETRY_PARAM_DEFINITIONS = definitions.build();\n"
          + "  }\n"
          + "\n"
          + "  protected EchoSpringAutoConfiguration("
          + "EchoSpringProperties clientProperties) {\n"
          + "    this.clientProperties = clientProperties;\n"
          + "  }\n"
          + "\n"
          + "  @Bean\n"
          + "  @ConditionalOnMissingBean\n"
          + "  public CredentialsProvider googleCredentials() throws IOException {\n"
          + "    return ((CredentialsProvider) new DefaultCredentialsProvider(this.clientProperties));\n"
          + "  }\n"
          + "\n"
          + "  @Bean\n"
          + "  @ConditionalOnMissingBean\n"
          + "  public TransportChannelProvider defaultEchoTransportChannelProvider() {\n"
          + "    return EchoSettings.defaultTransportChannelProvider();\n"
          + "  }\n"
          + "\n"
          + "  @Bean\n"
          + "  @ConditionalOnMissingBean\n"
          + "  public EchoClient echoClient(\n"
          + "      @Qualifier(\"googleCredentials\") CredentialsProvider credentialsProvider,\n"
          + "      @Qualifier(\"defaultEchoTransportChannelProvider\")\n"
          + "          TransportChannelProvider defaultTransportChannelProvider)\n"
          + "      throws IOException {\n"
          + "    EchoSettings.Builder clientSettingsBuilder =\n"
          + "        EchoSettings.newBuilder()\n"
          + "            .setCredentialsProvider(credentialsProvider)\n"
          + "            .setTransportChannelProvider(defaultTransportChannelProvider)\n"
          + "            .setHeaderProvider();\n"
          + "    if (this.clientProperties.getQuotaProjectId() != null) {\n"
          + "      clientSettingsBuilder.setQuotaProjectId(this.clientProperties.getQuotaProjectId());\n"
          + "    }\n"
          + "    if (this.clientProperties.getExecutorThreadCount() != null) {\n"
          + "      ExecutorProvider executorProvider =\n"
          + "          EchoSettings.defaultExecutorProviderBuilder()\n"
          + "              .setExecutorThreadCount(this.clientProperties.getExecutorThreadCount())\n"
          + "              .build();\n"
          + "      clientSettingsBuilder.setBackgroundExecutorProvider(executorProvider);\n"
          + "    }\n"
          + "    if (this.clientProperties.getUseRest()) {\n"
          + "      clientSettingsBuilder.setTransportChannelProvider(\n"
          + "          EchoSettings.defaultHttpJsonTransportProviderBuilder().build());\n"
          + "    }\n"
          + "    RetrySettings.Builder echoRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.echoSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getEchoRpcTimeoutMultiplier() != null) {\n"
          + "      echoRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getEchoRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder.echoSettings().setRetrySettings(echoRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder expandRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.expandSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getExpandRpcTimeoutMultiplier() != null) {\n"
          + "      expandRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getExpandRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder.expandSettings().setRetrySettings(expandRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder collectRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.collectSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getCollectRpcTimeoutMultiplier() != null) {\n"
          + "      collectRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getCollectRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder.collectSettings().setRetrySettings(collectRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder chatRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.chatSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getChatRpcTimeoutMultiplier() != null) {\n"
          + "      chatRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getChatRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder.chatSettings().setRetrySettings(chatRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder chatAgainRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.chatAgainSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getChatAgainRpcTimeoutMultiplier() != null) {\n"
          + "      chatAgainRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getChatAgainRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder\n"
          + "        .chatAgainSettings()\n"
          + "        .setRetrySettings(chatAgainRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder pagedExpandRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.pagedExpandSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getPagedExpandRpcTimeoutMultiplier() != null) {\n"
          + "      pagedExpandRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getPagedExpandRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder\n"
          + "        .pagedExpandSettings()\n"
          + "        .setRetrySettings(pagedExpandRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder simplePagedExpandRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.simplePagedExpandSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getSimplePagedExpandRpcTimeoutMultiplier() != null) {\n"
          + "      simplePagedExpandRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getSimplePagedExpandRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder\n"
          + "        .simplePagedExpandSettings()\n"
          + "        .setRetrySettings(simplePagedExpandRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder waitRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.waitSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getWaitRpcTimeoutMultiplier() != null) {\n"
          + "      waitRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getWaitRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder.waitSettings().setRetrySettings(waitRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder blockRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.blockSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getBlockRpcTimeoutMultiplier() != null) {\n"
          + "      blockRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getBlockRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder.blockSettings().setRetrySettings(blockRetrySettingBuilder.build());\n"
          + "    RetrySettings.Builder collideNameRetrySettingBuilder =\n"
          + "        clientSettingsBuilder.collideNameSettings().getRetrySettings().toBuilder();\n"
          + "    if (this.clientProperties.getCollideNameRpcTimeoutMultiplier() != null) {\n"
          + "      collideNameRetrySettingBuilder.setRpcTimeoutMultiplier(\n"
          + "          this.clientProperties.getCollideNameRpcTimeoutMultiplier());\n"
          + "    }\n"
          + "    clientSettingsBuilder\n"
          + "        .collideNameSettings()\n"
          + "        .setRetrySettings(collideNameRetrySettingBuilder.build());\n"
          + "    return EchoClient.create(clientSettingsBuilder.build());\n"
          + "  }\n"
          + "}\n";

  protected static final String EXPECTED_PROPERTY_CLASS_STRING =
      "package com.google.showcase.v1beta1.spring;\n"
          + "\n"
          + "import com.google.cloud.spring.core.Credentials;\n"
          + "import com.google.cloud.spring.core.CredentialsSupplier;\n"
          + "import org.springframework.boot.context.properties.ConfigurationProperties;\n"
          + "import org.threeten.bp.Duration;\n"
          + "\n"
          + "@ConfigurationProperties(\"spring.cloud.gcp.autoconfig.showcase.echo\")\n"
          + "public class EchoSpringProperties implements CredentialsSupplier {\n"
          + "  @NestedConfigurationProperty\n"
          + "  private final Credentials credentials =\n"
          + "      new Credentials(\"https://www.googleapis.com/auth/cloud-platform\");\n"
          + "\n"
          + "  private String quotaProjectId;\n"
          + "  private Integer executorThreadCount;\n"
          + "  private boolean useRest;\n"
          // retry settings per method, no default set
          // checks for null in autoconfig class when using
          + "  private Duration echoInitialRetryDelay;\n"
          + "  private Double echoRetryDelayMultiplier;\n"
          + "  private Duration echoMaxRetryDelay;\n"
          + "  private Duration echoInitialRpcTimeout;\n"
          + "  private Double echoRpcTimeoutMultiplier;\n"
          + "  private Duration echoMaxRpcTimeout;\n"
          + "  private Duration echoTotalTimeout;\n"
          + "  private Duration expandInitialRetryDelay;\n"
          + "  private Double expandRetryDelayMultiplier;\n"
          + "  private Duration expandMaxRetryDelay;\n"
          + "  private Duration expandInitialRpcTimeout;\n"
          + "  private Double expandRpcTimeoutMultiplier;\n"
          + "  private Duration expandMaxRpcTimeout;\n"
          + "  private Duration expandTotalTimeout;\n"
          + "  private Duration collectInitialRpcTimeout;\n"
          + "  private Double collectRpcTimeoutMultiplier;\n"
          + "  private Duration collectMaxRpcTimeout;\n"
          + "  private Duration collectTotalTimeout;\n"
          + "  private Duration chatInitialRpcTimeout;\n"
          + "  private Double chatRpcTimeoutMultiplier;\n"
          + "  private Duration chatMaxRpcTimeout;\n"
          + "  private Duration chatTotalTimeout;\n"
          + "  private Duration chatAgainInitialRpcTimeout;\n"
          + "  private Double chatAgainRpcTimeoutMultiplier;\n"
          + "  private Duration chatAgainMaxRpcTimeout;\n"
          + "  private Duration chatAgainTotalTimeout;\n"
          + "  private Duration pagedExpandInitialRetryDelay;\n"
          + "  private Double pagedExpandRetryDelayMultiplier;\n"
          + "  private Duration pagedExpandMaxRetryDelay;\n"
          + "  private Duration pagedExpandInitialRpcTimeout;\n"
          + "  private Double pagedExpandRpcTimeoutMultiplier;\n"
          + "  private Duration pagedExpandMaxRpcTimeout;\n"
          + "  private Duration pagedExpandTotalTimeout;\n"
          + "  private Duration simplePagedExpandInitialRpcTimeout;\n"
          + "  private Double simplePagedExpandRpcTimeoutMultiplier;\n"
          + "  private Duration simplePagedExpandMaxRpcTimeout;\n"
          + "  private Duration simplePagedExpandTotalTimeout;\n"
          + "  private Duration waitInitialRpcTimeout;\n"
          + "  private Double waitRpcTimeoutMultiplier;\n"
          + "  private Duration waitMaxRpcTimeout;\n"
          + "  private Duration waitTotalTimeout;\n"
          + "  private Duration blockInitialRpcTimeout;\n"
          + "  private Double blockRpcTimeoutMultiplier;\n"
          + "  private Duration blockMaxRpcTimeout;\n"
          + "  private Duration blockTotalTimeout;\n"
          + "  private Duration collideNameInitialRpcTimeout;\n"
          + "  private Double collideNameRpcTimeoutMultiplier;\n"
          + "  private Duration collideNameMaxRpcTimeout;\n"
          + "  private Duration collideNameTotalTimeout;\n"
          + "\n"
          + "  @Override\n"
          + "  public Credentials getCredentials() {\n"
          + "    return this.credentials;\n"
          + "  }\n"
          + "\n"
          + "  public String getQuotaProjectId() {\n"
          + "    return this.quotaProjectId;\n"
          + "  }\n"
          + "\n"
          + "  public void setQuotaProjectId(String quotaProjectId) {\n"
          + "    this.quotaProjectId = quotaProjectId;\n"
          + "  }\n"
          + "\n"
          + "  public boolean getUseRest() {\n"
          + "    return this.useRest;\n"
          + "  }\n"
          + "\n"
          + "  public Integer getExecutorThreadCount() {\n"
          + "    return this.executorThreadCount;\n"
          + "  }\n"
          + "\n"
          + "  public void setExecutorThreadCount(Integer executorThreadCount) {\n"
          + "    this.executorThreadCount = executorThreadCount;\n"
          + "  }\n"
          + "\n"
          // getters and setters for retry settings.
          + "  public Duration getEchoInitialRetryDelay() {\n"
          + "    return this.echoInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoInitialRetryDelay(Duration echoInitialRetryDelay) {\n"
          + "    this.echoInitialRetryDelay = echoInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public double getEchoRetryDelayMultiplier() {\n"
          + "    return this.echoRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoRetryDelayMultiplier(double echoRetryDelayMultiplier) {\n"
          + "    this.echoRetryDelayMultiplier = echoRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getEchoMaxRetryDelay() {\n"
          + "    return this.echoMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoMaxRetryDelay(Duration echoMaxRetryDelay) {\n"
          + "    this.echoMaxRetryDelay = echoMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getEchoInitialRpcTimeout() {\n"
          + "    return this.echoInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoInitialRpcTimeout(Duration echoInitialRpcTimeout) {\n"
          + "    this.echoInitialRpcTimeout = echoInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getEchoRpcTimeoutMultiplier() {\n"
          + "    return this.echoRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoRpcTimeoutMultiplier(double echoRpcTimeoutMultiplier) {\n"
          + "    this.echoRpcTimeoutMultiplier = echoRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getEchoMaxRpcTimeout() {\n"
          + "    return this.echoMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoMaxRpcTimeout(Duration echoMaxRpcTimeout) {\n"
          + "    this.echoMaxRpcTimeout = echoMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getEchoTotalTimeout() {\n"
          + "    return this.echoTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoTotalTimeout(Duration echoTotalTimeout) {\n"
          + "    this.echoTotalTimeout = echoTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandInitialRetryDelay() {\n"
          + "    return this.expandInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandInitialRetryDelay(Duration expandInitialRetryDelay) {\n"
          + "    this.expandInitialRetryDelay = expandInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public double getExpandRetryDelayMultiplier() {\n"
          + "    return this.expandRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandRetryDelayMultiplier(double expandRetryDelayMultiplier) {\n"
          + "    this.expandRetryDelayMultiplier = expandRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandMaxRetryDelay() {\n"
          + "    return this.expandMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandMaxRetryDelay(Duration expandMaxRetryDelay) {\n"
          + "    this.expandMaxRetryDelay = expandMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandInitialRpcTimeout() {\n"
          + "    return this.expandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandInitialRpcTimeout(Duration expandInitialRpcTimeout) {\n"
          + "    this.expandInitialRpcTimeout = expandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getExpandRpcTimeoutMultiplier() {\n"
          + "    return this.expandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandRpcTimeoutMultiplier(double expandRpcTimeoutMultiplier) {\n"
          + "    this.expandRpcTimeoutMultiplier = expandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandMaxRpcTimeout() {\n"
          + "    return this.expandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandMaxRpcTimeout(Duration expandMaxRpcTimeout) {\n"
          + "    this.expandMaxRpcTimeout = expandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandTotalTimeout() {\n"
          + "    return this.expandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandTotalTimeout(Duration expandTotalTimeout) {\n"
          + "    this.expandTotalTimeout = expandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollectInitialRpcTimeout() {\n"
          + "    return this.collectInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollectInitialRpcTimeout(Duration collectInitialRpcTimeout) {\n"
          + "    this.collectInitialRpcTimeout = collectInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getCollectRpcTimeoutMultiplier() {\n"
          + "    return this.collectRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollectRpcTimeoutMultiplier(double collectRpcTimeoutMultiplier) {\n"
          + "    this.collectRpcTimeoutMultiplier = collectRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollectMaxRpcTimeout() {\n"
          + "    return this.collectMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollectMaxRpcTimeout(Duration collectMaxRpcTimeout) {\n"
          + "    this.collectMaxRpcTimeout = collectMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollectTotalTimeout() {\n"
          + "    return this.collectTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollectTotalTimeout(Duration collectTotalTimeout) {\n"
          + "    this.collectTotalTimeout = collectTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatInitialRpcTimeout() {\n"
          + "    return this.chatInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatInitialRpcTimeout(Duration chatInitialRpcTimeout) {\n"
          + "    this.chatInitialRpcTimeout = chatInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getChatRpcTimeoutMultiplier() {\n"
          + "    return this.chatRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatRpcTimeoutMultiplier(double chatRpcTimeoutMultiplier) {\n"
          + "    this.chatRpcTimeoutMultiplier = chatRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatMaxRpcTimeout() {\n"
          + "    return this.chatMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatMaxRpcTimeout(Duration chatMaxRpcTimeout) {\n"
          + "    this.chatMaxRpcTimeout = chatMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatTotalTimeout() {\n"
          + "    return this.chatTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatTotalTimeout(Duration chatTotalTimeout) {\n"
          + "    this.chatTotalTimeout = chatTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatAgainInitialRpcTimeout() {\n"
          + "    return this.chatAgainInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatAgainInitialRpcTimeout(Duration chatAgainInitialRpcTimeout) {\n"
          + "    this.chatAgainInitialRpcTimeout = chatAgainInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getChatAgainRpcTimeoutMultiplier() {\n"
          + "    return this.chatAgainRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatAgainRpcTimeoutMultiplier(double chatAgainRpcTimeoutMultiplier) {\n"
          + "    this.chatAgainRpcTimeoutMultiplier = chatAgainRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatAgainMaxRpcTimeout() {\n"
          + "    return this.chatAgainMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatAgainMaxRpcTimeout(Duration chatAgainMaxRpcTimeout) {\n"
          + "    this.chatAgainMaxRpcTimeout = chatAgainMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatAgainTotalTimeout() {\n"
          + "    return this.chatAgainTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatAgainTotalTimeout(Duration chatAgainTotalTimeout) {\n"
          + "    this.chatAgainTotalTimeout = chatAgainTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandInitialRetryDelay() {\n"
          + "    return this.pagedExpandInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandInitialRetryDelay(Duration pagedExpandInitialRetryDelay) {\n"
          + "    this.pagedExpandInitialRetryDelay = pagedExpandInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public double getPagedExpandRetryDelayMultiplier() {\n"
          + "    return this.pagedExpandRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandRetryDelayMultiplier(double pagedExpandRetryDelayMultiplier) {\n"
          + "    this.pagedExpandRetryDelayMultiplier = pagedExpandRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandMaxRetryDelay() {\n"
          + "    return this.pagedExpandMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandMaxRetryDelay(Duration pagedExpandMaxRetryDelay) {\n"
          + "    this.pagedExpandMaxRetryDelay = pagedExpandMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandInitialRpcTimeout() {\n"
          + "    return this.pagedExpandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandInitialRpcTimeout(Duration pagedExpandInitialRpcTimeout) {\n"
          + "    this.pagedExpandInitialRpcTimeout = pagedExpandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getPagedExpandRpcTimeoutMultiplier() {\n"
          + "    return this.pagedExpandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandRpcTimeoutMultiplier(double pagedExpandRpcTimeoutMultiplier) {\n"
          + "    this.pagedExpandRpcTimeoutMultiplier = pagedExpandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandMaxRpcTimeout() {\n"
          + "    return this.pagedExpandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandMaxRpcTimeout(Duration pagedExpandMaxRpcTimeout) {\n"
          + "    this.pagedExpandMaxRpcTimeout = pagedExpandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandTotalTimeout() {\n"
          + "    return this.pagedExpandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandTotalTimeout(Duration pagedExpandTotalTimeout) {\n"
          + "    this.pagedExpandTotalTimeout = pagedExpandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getSimplePagedExpandInitialRpcTimeout() {\n"
          + "    return this.simplePagedExpandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setSimplePagedExpandInitialRpcTimeout(Duration simplePagedExpandInitialRpcTimeout) {\n"
          + "    this.simplePagedExpandInitialRpcTimeout = simplePagedExpandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getSimplePagedExpandRpcTimeoutMultiplier() {\n"
          + "    return this.simplePagedExpandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setSimplePagedExpandRpcTimeoutMultiplier(\n"
          + "      double simplePagedExpandRpcTimeoutMultiplier) {\n"
          + "    this.simplePagedExpandRpcTimeoutMultiplier = simplePagedExpandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getSimplePagedExpandMaxRpcTimeout() {\n"
          + "    return this.simplePagedExpandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setSimplePagedExpandMaxRpcTimeout(Duration simplePagedExpandMaxRpcTimeout) {\n"
          + "    this.simplePagedExpandMaxRpcTimeout = simplePagedExpandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getSimplePagedExpandTotalTimeout() {\n"
          + "    return this.simplePagedExpandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setSimplePagedExpandTotalTimeout(Duration simplePagedExpandTotalTimeout) {\n"
          + "    this.simplePagedExpandTotalTimeout = simplePagedExpandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getWaitInitialRpcTimeout() {\n"
          + "    return this.waitInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setWaitInitialRpcTimeout(Duration waitInitialRpcTimeout) {\n"
          + "    this.waitInitialRpcTimeout = waitInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getWaitRpcTimeoutMultiplier() {\n"
          + "    return this.waitRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setWaitRpcTimeoutMultiplier(double waitRpcTimeoutMultiplier) {\n"
          + "    this.waitRpcTimeoutMultiplier = waitRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getWaitMaxRpcTimeout() {\n"
          + "    return this.waitMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setWaitMaxRpcTimeout(Duration waitMaxRpcTimeout) {\n"
          + "    this.waitMaxRpcTimeout = waitMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getWaitTotalTimeout() {\n"
          + "    return this.waitTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setWaitTotalTimeout(Duration waitTotalTimeout) {\n"
          + "    this.waitTotalTimeout = waitTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getBlockInitialRpcTimeout() {\n"
          + "    return this.blockInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setBlockInitialRpcTimeout(Duration blockInitialRpcTimeout) {\n"
          + "    this.blockInitialRpcTimeout = blockInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getBlockRpcTimeoutMultiplier() {\n"
          + "    return this.blockRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setBlockRpcTimeoutMultiplier(double blockRpcTimeoutMultiplier) {\n"
          + "    this.blockRpcTimeoutMultiplier = blockRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getBlockMaxRpcTimeout() {\n"
          + "    return this.blockMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setBlockMaxRpcTimeout(Duration blockMaxRpcTimeout) {\n"
          + "    this.blockMaxRpcTimeout = blockMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getBlockTotalTimeout() {\n"
          + "    return this.blockTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setBlockTotalTimeout(Duration blockTotalTimeout) {\n"
          + "    this.blockTotalTimeout = blockTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollideNameInitialRpcTimeout() {\n"
          + "    return this.collideNameInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollideNameInitialRpcTimeout(Duration collideNameInitialRpcTimeout) {\n"
          + "    this.collideNameInitialRpcTimeout = collideNameInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getCollideNameRpcTimeoutMultiplier() {\n"
          + "    return this.collideNameRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollideNameRpcTimeoutMultiplier(double collideNameRpcTimeoutMultiplier) {\n"
          + "    this.collideNameRpcTimeoutMultiplier = collideNameRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollideNameMaxRpcTimeout() {\n"
          + "    return this.collideNameMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollideNameMaxRpcTimeout(Duration collideNameMaxRpcTimeout) {\n"
          + "    this.collideNameMaxRpcTimeout = collideNameMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollideNameTotalTimeout() {\n"
          + "    return this.collideNameTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollideNameTotalTimeout(Duration collideNameTotalTimeout) {\n"
          + "    this.collideNameTotalTimeout = collideNameTotalTimeout;\n"
          + "  }\n"
          + "}\n";
}
