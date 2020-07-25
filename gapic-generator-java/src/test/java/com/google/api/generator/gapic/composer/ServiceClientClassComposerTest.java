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

public class ServiceClientClassComposerTest {
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
        ServiceClientClassComposer.instance().generate(echoProtoService, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1;\n"
          + "\n"
          + "import com.google.api.gax.core.BackgroundResource;\n"
          + "import com.google.api.gax.longrunning.OperationFuture;\n"
          + "import com.google.api.gax.rpc.BidiStreamingCallable;\n"
          + "import com.google.api.gax.rpc.ClientStreamingCallable;\n"
          + "import com.google.api.gax.rpc.OperationCallable;\n"
          + "import com.google.api.gax.rpc.ServerStreamingCallable;\n"
          + "import com.google.api.gax.rpc.UnaryCallable;\n"
          + "import com.google.longrunning.Operation;\n"
          + "import com.google.longrunning.OperationsClient;\n"
          + "import com.google.rpc.Status;\n"
          + "import com.google.showcase.v1beta1.stub.EchoStub;\n"
          + "import java.io.IOException;\n"
          + "import java.util.concurrent.TimeUnit;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "@Generated(\"by gapic-generator\")\n"
          + "public class EchoClient implements BackgroundResource {\n"
          + "  private final EchoSettings settings;\n"
          + "  private final EchoStub stub;\n"
          + "  private final OperationsClient operationsClient;\n"
          + "\n"
          + "  public static final EchoClient create(EchoSettings settings) throws IOException {\n"
          + "    return create(EchoSettings.newBuilder().builder());\n"
          + "  }\n"
          + "\n"
          + "  public EchoStub getStub() {\n"
          + "    return stub;\n"
          + "  }\n"
          + "\n"
          + "  public final EchoSettings getSettings() {\n"
          + "    return settings;\n"
          + "  }\n"
          + "\n"
          + "  public final OperationsClient getOperationsClient() {\n"
          + "    return operationsClient;\n"
          + "  }\n"
          + "\n"
          + "  public final EchoResponse echo(String content) {\n"
          + "    EchoRequest request = EchoRequest.newBuilder().setContent(content).build();\n"
          + "    return echo(request);\n"
          + "  }\n"
          + "\n"
          + "  public final EchoResponse echo(Status error) {\n"
          + "    EchoRequest request = EchoRequest.newBuilder().setError(error).build();\n"
          + "    return echo(request);\n"
          + "  }\n"
          + "\n"
          + "  public final EchoResponse echo(String content, Severity severity) {\n"
          + "    EchoRequest request =\n"
          + "        EchoRequest.newBuilder().setContent(content).setSeverity(severity).build();\n"
          + "    return echo(request);\n"
          + "  }\n"
          + "\n"
          + "  public final EchoResponse echo(EchoRequest request) {\n"
          + "    return echoCallable().call(request);\n"
          + "  }\n"
          + "\n"
          + "  public final UnaryCallable<EchoRequest, EchoResponse> echoCallable() {\n"
          + "    return stub.echoCallable();\n"
          + "  }\n"
          + "\n"
          + "  public final ServerStreamingCallable<ExpandRequest, EchoResponse> expandCallable()"
          + " {\n"
          + "    return stub.expandCallable();\n"
          + "  }\n"
          + "\n"
          + "  public final ClientStreamingCallable<EchoRequest, EchoResponse> collectCallable()"
          + " {\n"
          + "    return stub.collectCallable();\n"
          + "  }\n"
          + "\n"
          + "  public final BidiStreamingCallable<EchoRequest, EchoResponse> chatCallable() {\n"
          + "    return stub.chatCallable();\n"
          + "  }\n"
          + "\n"
          + "  public final BidiStreamingCallable<EchoRequest, EchoResponse> chatAgainCallable()"
          + " {\n"
          + "    return stub.chatAgainCallable();\n"
          + "  }\n"
          + "\n"
          + "  public final PagedExpandResponse pagedExpand(PagedExpandRequest request) {\n"
          + "    return pagedExpandCallable().call(request);\n"
          + "  }\n"
          + "\n"
          + "  public final UnaryCallable<PagedExpandRequest, PagedExpandResponse>"
          + " pagedExpandCallable() {\n"
          + "    return stub.pagedExpandCallable();\n"
          + "  }\n"
          + "\n"
          + "  public final OperationFuture<WaitResponse, WaitMetadata> waitAsync(WaitRequest"
          + " request) {\n"
          + "    return waitOperationCallable().futureCall(request);\n"
          + "  }\n"
          + "\n"
          + "  public final OperationCallable<WaitRequest, WaitResponse, WaitMetadata>"
          + " waitOperationCallabke() {\n"
          + "    return stub.waitOperationCallabke();\n"
          + "  }\n"
          + "\n"
          + "  public final UnaryCallable<WaitRequest, Operation> waitCallable() {\n"
          + "    return stub.waitCallable();\n"
          + "  }\n"
          + "\n"
          + "  public final BlockResponse block(BlockRequest request) {\n"
          + "    return blockCallable().call(request);\n"
          + "  }\n"
          + "\n"
          + "  public final UnaryCallable<BlockRequest, BlockResponse> blockCallable() {\n"
          + "    return stub.blockCallable();\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void close() {}\n"
          + "\n"
          + "  @Override\n"
          + "  public void shutdown() {}\n"
          + "\n"
          + "  @Override\n"
          + "  public boolean isShutdown() {\n"
          + "    return false;\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public boolean isTerminated() {\n"
          + "    return false;\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void shutdownNow() {}\n"
          + "\n"
          + "  @Override\n"
          + "  public boolean awaitTermination(long duration, TimeUnit unit) throws"
          + " InterruptedException {\n"
          + "    return false;\n"
          + "  }\n"
          + "}\n";
}
