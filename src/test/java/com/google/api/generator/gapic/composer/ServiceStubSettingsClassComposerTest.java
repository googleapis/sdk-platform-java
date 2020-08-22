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
import static junit.framework.Assert.assertTrue;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.protoparser.ServiceConfigParser;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import io.grpc.serviceconfig.ServiceConfig;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class ServiceStubSettingsClassComposerTest {
  private static final String JSON_DIRECTORY =
      "src/test/java/com/google/api/generator/gapic/testdata/";

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

    String jsonFilename = "showcase_grpc_service_config.json";
    Path jsonPath = Paths.get(JSON_DIRECTORY, jsonFilename);
    Optional<ServiceConfig> configOpt = ServiceConfigParser.parseFile(jsonPath.toString());
    assertTrue(configOpt.isPresent());
    ServiceConfig config = configOpt.get();

    Service echoProtoService = services.get(0);
    GapicClass clazz =
        ServiceStubSettingsClassComposer.instance()
            .generate(echoProtoService, config, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1.stub;\n"
          + "\n"
          + "import static com.google.showcase.v1beta1.EchoClient.PagedExpandPagedResponse;\n"
          + "\n"
          + "import com.google.api.core.BetaApi;\n"
          + "import com.google.api.gax.rpc.OperationCallSettings;\n"
          + "import com.google.api.gax.rpc.PagedCallSettings;\n"
          + "import com.google.api.gax.rpc.ServerStreamingCallSettings;\n"
          + "import com.google.api.gax.rpc.StreamingCallSettings;\n"
          + "import com.google.api.gax.rpc.StubSettings;\n"
          + "import com.google.api.gax.rpc.UnaryCallSettings;\n"
          + "import com.google.common.collect.ImmutableList;\n"
          + "import com.google.longrunning.Operation;\n"
          + "import com.google.showcase.v1beta1.BlockRequest;\n"
          + "import com.google.showcase.v1beta1.BlockResponse;\n"
          + "import com.google.showcase.v1beta1.EchoRequest;\n"
          + "import com.google.showcase.v1beta1.EchoResponse;\n"
          + "import com.google.showcase.v1beta1.ExpandRequest;\n"
          + "import com.google.showcase.v1beta1.PagedExpandRequest;\n"
          + "import com.google.showcase.v1beta1.PagedExpandResponse;\n"
          + "import com.google.showcase.v1beta1.WaitMetadata;\n"
          + "import com.google.showcase.v1beta1.WaitRequest;\n"
          + "import com.google.showcase.v1beta1.WaitResponse;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "@BetaApi\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "public class EchoStubSettings extends StubSettings<EchoStubSettings> {\n"
          + "  private static final ImmutableList<String> DEFAULT_SERVICE_SCOPES =\n"
          + "     "
          + " ImmutableList.<String>builder().add(\"https://www.googleapis.com/auth/cloud-platform\").build();\n"
          + "  private final UnaryCallSettings<EchoRequest, EchoResponse> echoSettings;\n"
          + "  private final ServerStreamingCallSettings<ExpandRequest, EchoResponse>"
          + " expandSettings;\n"
          + "  private final StreamingCallSettings<EchoRequest, EchoResponse> collectSettings;\n"
          + "  private final StreamingCallSettings<EchoRequest, EchoResponse> chatSettings;\n"
          + "  private final StreamingCallSettings<EchoRequest, EchoResponse> chatAgainSettings;\n"
          + "  private final PagedCallSettings<PagedExpandRequest, PagedExpandResponse,"
          + " PagedExpandPagedResponse>\n"
          + "      pagedExpandSettings;\n"
          + "  private final UnaryCallSettings<WaitRequest, Operation> waitSettings;\n"
          + "  private final OperationCallSettings<WaitRequest, WaitResponse, WaitMetadata>\n"
          + "      waitOperationSettings;\n"
          + "  private final UnaryCallSettings<BlockRequest, BlockResponse> blockSettings;\n"
          + "\n"
          + "  public UnaryCallSettings<EchoRequest, EchoResponse> echoSettings() {\n"
          + "    return echoSettings;\n"
          + "  }\n"
          + "\n"
          + "  public ServerStreamingCallSettings<ExpandRequest, EchoResponse> expandSettings()"
          + " {\n"
          + "    return expandSettings;\n"
          + "  }\n"
          + "\n"
          + "  public StreamingCallSettings<EchoRequest, EchoResponse> collectSettings() {\n"
          + "    return collectSettings;\n"
          + "  }\n"
          + "\n"
          + "  public StreamingCallSettings<EchoRequest, EchoResponse> chatSettings() {\n"
          + "    return chatSettings;\n"
          + "  }\n"
          + "\n"
          + "  public StreamingCallSettings<EchoRequest, EchoResponse> chatAgainSettings() {\n"
          + "    return chatAgainSettings;\n"
          + "  }\n"
          + "\n"
          + "  public PagedCallSettings<PagedExpandRequest, PagedExpandResponse,"
          + " PagedExpandPagedResponse>\n"
          + "      pagedExpandSettings() {\n"
          + "    return pagedExpandSettings;\n"
          + "  }\n"
          + "\n"
          + "  public UnaryCallSettings<WaitRequest, Operation> waitSettings() {\n"
          + "    return waitSettings;\n"
          + "  }\n"
          + "\n"
          + "  public OperationCallSettings<WaitRequest, WaitResponse, WaitMetadata>"
          + " waitOperationSettings() {\n"
          + "    return waitOperationSettings;\n"
          + "  }\n"
          + "\n"
          + "  public UnaryCallSettings<BlockRequest, BlockResponse> blockSettings() {\n"
          + "    return blockSettings;\n"
          + "  }\n"
          + "\n"
          + "  public static class Builder {}\n"
          + "}\n";
}
