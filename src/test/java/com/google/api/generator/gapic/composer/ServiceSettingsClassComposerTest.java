// Copyright 2020 Google LLC
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

package com.google.api.generator.gapic.composer;

import static junit.framework.Assert.assertEquals;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class ServiceSettingsClassComposerTest {
  private ServiceDescriptor echoService;
  private FileDescriptor echoFileDescriptor;

  @Before
  public void setUp() {
    echoFileDescriptor = EchoOuterClass.getDescriptor();
    echoService = echoFileDescriptor.getServices().get(0);
    assertEquals(echoService.getName(), "Echo");
  }

  @Test
  public void generateServiceClasses() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(echoFileDescriptor, messageTypes, resourceNames, outputResourceNames);

    Service echoProtoService = services.get(0);
    GapicClass clazz =
        ServiceSettingsClassComposer.instance().generate(echoProtoService, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1;\n"
          + "\n"
          + "import static com.google.showcase.v1beta1.EchoClient.PagedExpandPagedResponse;\n"
          + "\n"
          + "import com.google.api.core.ApiFunction;\n"
          + "import com.google.api.core.BetaApi;\n"
          + "import com.google.api.gax.core.GoogleCredentialsProvider;\n"
          + "import com.google.api.gax.core.InstantiatingExecutorProvider;\n"
          + "import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;\n"
          + "import com.google.api.gax.rpc.ApiClientHeaderProvider;\n"
          + "import com.google.api.gax.rpc.ClientContext;\n"
          + "import com.google.api.gax.rpc.ClientSettings;\n"
          + "import com.google.api.gax.rpc.OperationCallSettings;\n"
          + "import com.google.api.gax.rpc.PagedCallSettings;\n"
          + "import com.google.api.gax.rpc.ServerStreamingCallSettings;\n"
          + "import com.google.api.gax.rpc.StreamingCallSettings;\n"
          + "import com.google.api.gax.rpc.StubSettings;\n"
          + "import com.google.api.gax.rpc.TransportChannelProvider;\n"
          + "import com.google.api.gax.rpc.UnaryCallSettings;\n"
          + "import com.google.longrunning.Operation;\n"
          + "import com.google.showcase.v1beta1.stub.EchoStubSettings;\n"
          + "import java.io.IOException;\n"
          + "import java.util.List;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "public class EchoSettings extends ClientSettings<EchoSettings> {\n"
          + "  public UnaryCallSettings<EchoRequest, EchoResponse> echoSettings() {\n"
          + "    return ((EchoStubSettings) getStubSettings()).echoSettings();\n"
          + "  }\n"
          + "\n"
          + "  public ServerStreamingCallSettings<ExpandRequest, EchoResponse> expandSettings()"
          + " {\n"
          + "    return ((EchoStubSettings) getStubSettings()).expandSettings();\n"
          + "  }\n"
          + "\n"
          + "  public StreamingCallSettings<EchoRequest, EchoResponse> collectSettings() {\n"
          + "    return ((EchoStubSettings) getStubSettings()).collectSettings();\n"
          + "  }\n"
          + "\n"
          + "  public StreamingCallSettings<EchoRequest, EchoResponse> chatSettings() {\n"
          + "    return ((EchoStubSettings) getStubSettings()).chatSettings();\n"
          + "  }\n"
          + "\n"
          + "  public StreamingCallSettings<EchoRequest, EchoResponse> chatAgainSettings() {\n"
          + "    return ((EchoStubSettings) getStubSettings()).chatAgainSettings();\n"
          + "  }\n"
          + "\n"
          + "  public PagedCallSettings<PagedExpandRequest, PagedExpandResponse,"
          + " PagedExpandPagedResponse>\n"
          + "      pagedExpandSettings() {\n"
          + "    return ((EchoStubSettings) getStubSettings()).pagedExpandSettings();\n"
          + "  }\n"
          + "\n"
          + "  public UnaryCallSettings<WaitRequest, Operation> waitSettings() {\n"
          + "    return ((EchoStubSettings) getStubSettings()).waitSettings();\n"
          + "  }\n"
          + "\n"
          + "  public OperationCallSettings<WaitRequest, WaitResponse, WaitMetadata>"
          + " waitOperationSettings() {\n"
          + "    return ((EchoStubSettings) getStubSettings()).waitOperationSettings();\n"
          + "  }\n"
          + "\n"
          + "  public UnaryCallSettings<BlockRequest, BlockResponse> blockSettings() {\n"
          + "    return ((EchoStubSettings) getStubSettings()).blockSettings();\n"
          + "  }\n"
          + "\n"
          + "  public static final EchoSettings create(EchoStubSettings stub) throws IOException"
          + " {\n"
          + "    return new EchoStubSettings.Builder(stub.toBuilder()).build();\n"
          + "  }\n"
          + "\n"
          + "  public static InstantiatingExecutorProvider.Builder"
          + " defaultExecutorProviderBuilder() {\n"
          + "    return EchoStubSettings.defaultExecutorProviderBuilder();\n"
          + "  }\n"
          + "\n"
          + "  public static String getDefaultEndpoint() {\n"
          + "    return EchoStubSettings.getDefaultEndpoint();\n"
          + "  }\n"
          + "\n"
          + "  public static List<String> getDefaultServiceScopes() {\n"
          + "    return EchoStubSettings.getDefaultServiceScopes();\n"
          + "  }\n"
          + "\n"
          + "  public static GoogleCredentialsProvider.Builder defaultCredentialsProviderBuilder()"
          + " {\n"
          + "    return EchoStubSettings.defaultCredentialsProviderBuilder();\n"
          + "  }\n"
          + "\n"
          + "  public static InstantiatingGrpcChannelProvider.Builder"
          + " defaultGrpcTransportProviderBuilder() {\n"
          + "    return EchoStubSettings.defaultGrpcTransportProviderBuilder();\n"
          + "  }\n"
          + "\n"
          + "  public static TransportChannelProvider defaultTransportChannelProvider() {\n"
          + "    return EchoStubSettings.defaultTransportChannelProvider();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The surface for customizing headers is not stable yet and may change in"
          + " the future.\")\n"
          + "  public static ApiClientHeaderProvider.Builder"
          + " defaultApiClientHeaderProviderBuilder() {\n"
          + "    return EchoStubSettings.defaultApiClientHeaderProviderBuilder();\n"
          + "  }\n"
          + "\n"
          + "  public static Builder newBuilder() {\n"
          + "    return Builder.createDefault();\n"
          + "  }\n"
          + "\n"
          + "  public static Builder newBuilder(ClientContext clientContext) {\n"
          + "    return new Builder(clientContext);\n"
          + "  }\n"
          + "\n"
          + "  public Builder toBuilder() {\n"
          + "    return new Builder(this);\n"
          + "  }\n"
          + "\n"
          + "  protected EchoSettings(Builder settingsBuilder) throws IOException {\n"
          + "    super(settingsBuilder);\n"
          + "  }\n"
          + "\n"
          + "  public static class Builder extends ClientSettings.Builder<EchoSettings, Builder>"
          + " {\n"
          + "    protected Builder() throws IOException {\n"
          + "      this(((ClientContext) null));\n"
          + "    }\n"
          + "\n"
          + "    protected Builder(ClientContext clientContext) {\n"
          + "      super(EchoStubSettings.newBuilder(clientContext));\n"
          + "    }\n"
          + "\n"
          + "    protected Builder(EchoSettings settings) {\n"
          + "      super(settings.getStubSettings().toBuilder());\n"
          + "    }\n"
          + "\n"
          + "    protected Builder(EchoStubSettings.Builder stubSettings) {\n"
          + "      super(stubSettings);\n"
          + "    }\n"
          + "\n"
          + "    private static Builder createDefault() {\n"
          + "      return new Builder(EchoStubSettings.newBuilder());\n"
          + "    }\n"
          + "\n"
          + "    public EchoStubSettings.Builder getStubSettingsBuilder() {\n"
          + "      return ((EchoStubSettings.Builder) getStubSettings());\n"
          + "    }\n"
          + "\n"
          + "    public Builder applyToAllUnaryMethods(\n"
          + "        ApiFunction<UnaryCallSettings.Builder<?, ?>, Void> settingsUpdater) throws"
          + " Exception {\n"
          + "      super.applyToAllUnaryMethods(\n"
          + "          getStubSettingsBuilder().unaryMethodSettingsBuilders(), settingsUpdater);\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    public UnaryCallSettings.Builder<EchoRequest, EchoResponse> echoSettings() {\n"
          + "      return getStubSettingsBuilder().echoSettings();\n"
          + "    }\n"
          + "\n"
          + "    public ServerStreamingCallSettings.Builder<ExpandRequest, EchoResponse>"
          + " expandSettings() {\n"
          + "      return getStubSettingsBuilder().expandSettings();\n"
          + "    }\n"
          + "\n"
          + "    public StreamingCallSettings.Builder<EchoRequest, EchoResponse> collectSettings()"
          + " {\n"
          + "      return getStubSettingsBuilder().collectSettings();\n"
          + "    }\n"
          + "\n"
          + "    public StreamingCallSettings.Builder<EchoRequest, EchoResponse> chatSettings()"
          + " {\n"
          + "      return getStubSettingsBuilder().chatSettings();\n"
          + "    }\n"
          + "\n"
          + "    public StreamingCallSettings.Builder<EchoRequest, EchoResponse>"
          + " chatAgainSettings() {\n"
          + "      return getStubSettingsBuilder().chatAgainSettings();\n"
          + "    }\n"
          + "\n"
          + "    public PagedCallSettings.Builder<\n"
          + "            PagedExpandRequest, PagedExpandResponse, PagedExpandPagedResponse>\n"
          + "        pagedExpandSettings() {\n"
          + "      return getStubSettingsBuilder().pagedExpandSettings();\n"
          + "    }\n"
          + "\n"
          + "    public UnaryCallSettings.Builder<WaitRequest, Operation> waitSettings() {\n"
          + "      return getStubSettingsBuilder().waitSettings();\n"
          + "    }\n"
          + "\n"
          + "    public OperationCallSettings.Builder<WaitRequest, WaitResponse, WaitMetadata>\n"
          + "        waitOperationSettings() {\n"
          + "      return getStubSettingsBuilder().waitOperationSettings();\n"
          + "    }\n"
          + "\n"
          + "    public UnaryCallSettings.Builder<BlockRequest, BlockResponse> blockSettings() {\n"
          + "      return getStubSettingsBuilder().blockSettings();\n"
          + "    }\n"
          + "\n"
          + "    @Override\n"
          + "    public EchoSettings build() throws IOException {\n"
          + "      return new EchoSettings(this);\n"
          + "    }\n"
          + "  }\n"
          + "}\n";
}
