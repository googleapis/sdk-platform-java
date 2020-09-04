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

public class GrpcServiceCallableFactoryClassComposerTest {
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
        GrpcServiceCallableFactoryClassComposer.instance().generate(echoProtoService, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1.stub;\n"
          + "\n"
          + "import com.google.api.gax.grpc.GrpcCallSettings;\n"
          + "import com.google.api.gax.grpc.GrpcCallableFactory;\n"
          + "import com.google.api.gax.grpc.GrpcStubCallableFactory;\n"
          + "import com.google.api.gax.rpc.BatchingCallSettings;\n"
          + "import com.google.api.gax.rpc.BidiStreamingCallable;\n"
          + "import com.google.api.gax.rpc.ClientContext;\n"
          + "import com.google.api.gax.rpc.ClientStreamingCallable;\n"
          + "import com.google.api.gax.rpc.OperationCallSettings;\n"
          + "import com.google.api.gax.rpc.OperationCallable;\n"
          + "import com.google.api.gax.rpc.PagedCallSettings;\n"
          + "import com.google.api.gax.rpc.ServerStreamingCallSettings;\n"
          + "import com.google.api.gax.rpc.ServerStreamingCallable;\n"
          + "import com.google.api.gax.rpc.StreamingCallSettings;\n"
          + "import com.google.api.gax.rpc.UnaryCallSettings;\n"
          + "import com.google.api.gax.rpc.UnaryCallable;\n"
          + "import com.google.longrunning.Operation;\n"
          + "import com.google.longrunning.stub.OperationsStub;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "@Generated(\"by gapic-generator\")\n"
          + "public class GrpcEchoCallableFactory implements GrpcStubCallableFactory {\n"
          + "\n"
          + "  @Override\n"
          + "  public <RequestT, ResponseT> UnaryCallable<RequestT, ResponseT>"
          + " createUnaryCallable(\n"
          + "      GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,\n"
          + "      UnaryCallSettings<RequestT, ResponseT> callSettings,\n"
          + "      ClientContext clientContext) {\n"
          + "    return GrpcCallableFactory.createUnaryCallable(grpcCallSettings, callSettings,"
          + " clientContext);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public <RequestT, ResponseT, PagedListResponseT>\n"
          + "      UnaryCallable<RequestT, PagedListResponseT> createPagedCallable(\n"
          + "          GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,\n"
          + "          PagedCallSettings<RequestT, ResponseT, PagedListResponseT> callSettings,\n"
          + "          ClientContext clientContext) {\n"
          + "    return GrpcCallableFactory.createPagedCallable(grpcCallSettings, callSettings,"
          + " clientContext);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public <RequestT, ResponseT> UnaryCallable<RequestT, ResponseT>"
          + " createBatchingCallable(\n"
          + "      GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,\n"
          + "      BatchingCallSettings<RequestT, ResponseT> callSettings,\n"
          + "      ClientContext clientContext) {\n"
          + "    return GrpcCallableFactory.createBatchingCallable(\n"
          + "        grpcCallSettings, callSettings, clientContext);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public <RequestT, ResponseT, MetadataT>\n"
          + "      OperationCallable<RequestT, ResponseT, MetadataT> createOperationCallable(\n"
          + "          GrpcCallSettings<RequestT, Operation> grpcCallSettings,\n"
          + "          OperationCallSettings<RequestT, ResponseT, MetadataT> callSettings,\n"
          + "          ClientContext clientContext,\n"
          + "          OperationsStub operationsStub) {\n"
          + "    return GrpcCallableFactory.createOperationCallable(\n"
          + "        grpcCallSettings, callSettings, clientContext, operationsStub);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public <RequestT, ResponseT>\n"
          + "      BidiStreamingCallable<RequestT, ResponseT> createBidiStreamingCallable(\n"
          + "          GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,\n"
          + "          StreamingCallSettings<RequestT, ResponseT> callSettings,\n"
          + "          ClientContext clientContext) {\n"
          + "    return GrpcCallableFactory.createBidiStreamingCallable(\n"
          + "        grpcCallSettings, callSettings, clientContext);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public <RequestT, ResponseT>\n"
          + "      ServerStreamingCallable<RequestT, ResponseT> createServerStreamingCallable(\n"
          + "          GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,\n"
          + "          ServerStreamingCallSettings<RequestT, ResponseT> callSettings,\n"
          + "          ClientContext clientContext) {\n"
          + "    return GrpcCallableFactory.createServerStreamingCallable(\n"
          + "        grpcCallSettings, callSettings, clientContext);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public <RequestT, ResponseT>\n"
          + "      ClientStreamingCallable<RequestT, ResponseT> createClientStreamingCallable(\n"
          + "          GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,\n"
          + "          StreamingCallSettings<RequestT, ResponseT> callSettings,\n"
          + "          ClientContext clientContext) {\n"
          + "    return GrpcCallableFactory.createClientStreamingCallable(\n"
          + "        grpcCallSettings, callSettings, clientContext);\n"
          + "  }\n"
          + "}\n";
}
