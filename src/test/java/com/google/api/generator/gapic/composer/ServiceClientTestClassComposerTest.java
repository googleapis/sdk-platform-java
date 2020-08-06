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
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ServiceClientTestClassComposerTest {
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
    List<Service> services = Parser.parseService(echoFileDescriptor, messageTypes);
    Service echoProtoService = services.get(0);
    GapicClass clazz =
        ServiceClientTestClassComposer.instance().generate(echoProtoService, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_CLASS_STRING =
      "// Copyright 2020 Google LLC\n"
          + "//\n"
          + "// Licensed under the Apache License, Version 2.0 (the \"License\");\n"
          + "// you may not use this file except in compliance with the License.\n"
          + "// You may obtain a copy of the License at\n"
          + "//\n"
          + "//      http://www.apache.org/licenses/LICENSE-2.0\n"
          + "//\n"
          + "// Unless required by applicable law or agreed to in writing, software\n"
          + "// distributed under the License is distributed on an \"AS IS\" BASIS,\n"
          + "// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
          + "// See the License for the specific language governing permissions and\n"
          + "// limitations under the License.\n"
          + "\n"
          + "package com.google.showcase.v1beta1;\n"
          + "\n"
          + "import com.google.api.gax.core.NoCredentialsProvider;\n"
          + "import com.google.api.gax.grpc.testing.LocalChannelProvider;\n"
          + "import com.google.api.gax.grpc.testing.MockGrpcService;\n"
          + "import com.google.api.gax.grpc.testing.MockServiceHelper;\n"
          + "import java.io.IOException;\n"
          + "import java.util.Arrays;\n"
          + "import java.util.UUID;\n"
          + "import javax.annotation.Generated;\n"
          + "import org.junit.After;\n"
          + "import org.junit.AfterClass;\n"
          + "import org.junit.Before;\n"
          + "import org.junit.BeforeClass;\n"
          + "\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "public class EchoClientTest {\n"
          + "  public static MockServiceHelper mockServiceHelper;\n"
          + "  public static MockEcho mockEcho;\n"
          + "  public EchoClient client;\n"
          + "  public LocalChannelProvider channelProvider;\n"
          + "\n"
          + "  @BeforeClass\n"
          + "  public static void startStaticServer() {\n"
          + "    mockEcho = newMockTodo();\n"
          + "    mockServiceHelper =\n"
          + "        newMockServiceHelperTodo(\n"
          + "            UUID.randomUUID().toString(), Arrays.<MockGrpcService>asList(mockEcho));\n"
          + "    mockServiceHelper.start();\n"
          + "  }\n"
          + "\n"
          + "  @AfterClass\n"
          + "  public static void stopServer() {\n"
          + "    mockServiceHelper.stop();\n"
          + "  }\n"
          + "\n"
          + "  @Before\n"
          + "  public void setUp() throws IOException {\n"
          + "    mockServiceHelper.reset();\n"
          + "    channelProvider = mockServiceHelper.createChannelProvider();\n"
          + "    settings =\n"
          + "        EchoSettings.newBuilder()\n"
          + "            .setTransportChannelProvider(channelProvider)\n"
          + "            .setCredentialsProvider(NoCredentialsProvider.create())\n"
          + "            .build();\n"
          + "    client = EchoClient.create(settings);\n"
          + "  }\n"
          + "\n"
          + "  @After\n"
          + "  public static void tearDown() throws Exception {\n"
          + "    client.close();\n"
          + "  }\n"
          + "}\n";
}
