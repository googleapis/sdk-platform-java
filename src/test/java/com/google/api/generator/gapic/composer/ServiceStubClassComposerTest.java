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

public class ServiceStubClassComposerTest {
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
    GapicClass clazz = ServiceStubClassComposer.instance().generate(echoProtoService, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1.stub;\n"
          + "\n"
          + "import com.google.api.gax.core.BackgroundResource;\n"
          + "import com.google.api.gax.rpc.BidiStreamingCallable;\n"
          + "import com.google.api.gax.rpc.ClientStreamingCallable;\n"
          + "import com.google.api.gax.rpc.OperationCallable;\n"
          + "import com.google.api.gax.rpc.ServerStreamingCallable;\n"
          + "import com.google.api.gax.rpc.UnaryCallable;\n"
          + "import com.google.longrunning.stub.OperationsStub;\n"
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
          + "@Generated(\"by gapic-generator\")\n"
          + "public abstract class EchoStub implements BackgroundResource {\n"
          + "  public OperationsStub getOperationsStub() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented:"
          + " getOperationsStub()\");\n"
          + "  }\n"
          + "\n"
          + "  public UnaryCallable<EchoRequest, EchoResponse> echoCallable() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented: echoCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  public ServerStreamingCallable<ExpandRequest, EchoResponse> expandCallable() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented: expandCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  public ClientStreamingCallable<EchoRequest, EchoResponse> collectCallable() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented:"
          + " collectCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  public BidiStreamingCallable<EchoRequest, EchoResponse> chatCallable() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented: chatCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  public BidiStreamingCallable<EchoRequest, EchoResponse> chatAgainCallable() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented:"
          + " chatAgainCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  public UnaryCallable<PagedExpandRequest, PagedExpandResponse> pagedExpandCallable()"
          + " {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented:"
          + " pagedExpandCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  public OperationCallable<WaitRequest, WaitResponse, WaitMetadata>"
          + " waitOperationCallable() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented:"
          + " waitOperationCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  public UnaryCallable<WaitRequest, WaitResponse, WaitMetadata> waitCallable() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented: waitCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  public UnaryCallable<BlockRequest, BlockResponse> blockCallable() {\n"
          + "    throw new UnsupportedOperationException(\"Not implemented: blockCallable()\");\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public abstract void close();\n"
          + "}\n";
}
