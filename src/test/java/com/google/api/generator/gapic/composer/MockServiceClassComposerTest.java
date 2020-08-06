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

public class MockServiceClassComposerTest {
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
    GapicClass clazz = MockServiceClassComposer.instance().generate(echoProtoService, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    System.out.println(visitor.write());
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
          + "// limitations under the License.\n\n"
          + "package com.google.showcase.v1beta1;\n"
          + "\n"
          + "import com.google.api.core.BetaApi;\n"
          + "import com.google.api.gax.grpc.testing.MockGrpcService;\n"
          + "import com.google.protobuf.AbstractMessage;\n"
          + "import io.grpc.ServerServiceDefinition;\n"
          + "import java.util.List;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "@BetaApi\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "public class MockEcho implements MockGrpcService {\n"
          + "  private final MockEchoImpl serviceImpl;\n"
          + "\n"
          + "  public MockEcho() {}\n"
          + "\n"
          + "  @Override\n"
          + "  public List<AbstractMessage> getRequests() {\n"
          + "    return serviceImpl.getRequests();\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void addResponse(AbstractMessage response) {\n"
          + "    serviceImpl.addResponse(response);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void addException(Exception exception) {\n"
          + "    serviceImpl.addException(exception);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public ServerServiceDefinition getServiceDefinition() {\n"
          + "    return serviceImpl.bindService();\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void reset() {\n"
          + "    serviceImpl.reset();\n"
          + "  }\n"
          + "}\n";
}
