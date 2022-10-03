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
import com.google.api.generator.test.framework.Assert;
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

    Assert.assertGoldenClass(this.getClass(), clazz, "SpringAutoConfigClass.golden");
  }

  // TODO: Remove this string once SpringComposer is refactored
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
          + "@Configuration(\"proxyBeanMethods = false\")\n"
          + "@ConditionalOnClass(\"value = EchoClient.class\")\n"
          + "@ConditionalOnProperty(\n"
          + "    \"value = \\\"spring.cloud.gcp.autoconfig.showcase.echo.enabled\\\", matchIfMissing = false\")\n"
          + "@EnableConfigurationProperties(\"EchoSpringProperties.Class\")\n"
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
          + "      CredentialsProvider credentialsProvider,\n"
          + "      TransportChannelProvider defaultTransportChannelProvider)\n"
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
}
