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
    List<Service> services = Parser.parseService(echoFileDescriptor, messageTypes);
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
          + "@Override\n"
          + "public <RequestT, ResponseT> UnaryCallable<RequestT, ResponseT>"
          + " createUnaryCallable(GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,"
          + " UnaryCallSettings<RequestT, ResponseT> callSettings, ClientContext clientContext)"
          + " {\n"
          + "return GrpcCallableFactory.createUnaryCallable(grpcCallSettings, callSettings,"
          + " clientContext);\n"
          + "}\n"
          + "@Override\n"
          + "public <RequestT, ResponseT, PagedListResponseT> UnaryCallable<RequestT,"
          + " PagedListResponseT> createPagedCallable(GrpcCallSettings<RequestT, ResponseT>"
          + " grpcCallSettings, PagedCallSettings<RequestT, ResponseT, PagedListResponseT>"
          + " callSettings, ClientContext clientContext) {\n"
          + "return GrpcCallableFactory.createPagedCallable(grpcCallSettings, callSettings,"
          + " clientContext);\n"
          + "}\n"
          + "@Override\n"
          + "public <RequestT, ResponseT> UnaryCallable<RequestT, ResponseT>"
          + " createBatchingCallable(GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,"
          + " BatchingCallSettings<RequestT, ResponseT> callSettings, ClientContext clientContext)"
          + " {\n"
          + "return GrpcCallableFactory.createBatchingCallable(grpcCallSettings, callSettings,"
          + " clientContext);\n"
          + "}\n"
          + "@Override\n"
          + "public <RequestT, ResponseT, MetadataT> OperationCallable<RequestT, ResponseT,"
          + " MetadataT> createOperationCallable(GrpcCallSettings<RequestT, Operation>"
          + " grpcCallSettings, OperationCallSettings<RequestT, ResponseT, MetadataT>"
          + " callSettings, ClientContext clientContext, OperationsStub operationsStub) {\n"
          + "return GrpcCallableFactory.createOperationCallable(grpcCallSettings, callSettings,"
          + " clientContext, operationsStub);\n"
          + "}\n"
          + "@Override\n"
          + "public <RequestT, ResponseT> BidiStreamingCallable<RequestT, ResponseT>"
          + " createBidiStreamingCallable(GrpcCallSettings<RequestT, ResponseT> grpcCallSettings,"
          + " StreamingCallSettings<RequestT, ResponseT> callSettings, ClientContext"
          + " clientContext) {\n"
          + "return GrpcCallableFactory.createBidiStreamingCallable(grpcCallSettings,"
          + " callSettings, clientContext);\n"
          + "}\n"
          + "@Override\n"
          + "public <RequestT, ResponseT> ServerStreamingCallable<RequestT, ResponseT>"
          + " createServerStreamingCallable(GrpcCallSettings<RequestT, ResponseT>"
          + " grpcCallSettings, ServerStreamingCallSettings<RequestT, ResponseT> callSettings,"
          + " ClientContext clientContext) {\n"
          + "return GrpcCallableFactory.createServerStreamingCallable(grpcCallSettings,"
          + " callSettings, clientContext);\n"
          + "}\n"
          + "@Override\n"
          + "public <RequestT, ResponseT> ClientStreamingCallable<RequestT, ResponseT>"
          + " createClientStreamingCallable(GrpcCallSettings<RequestT, ResponseT>"
          + " grpcCallSettings, StreamingCallSettings<RequestT, ResponseT> callSettings,"
          + " ClientContext clientContext) {\n"
          + "return GrpcCallableFactory.createClientStreamingCallable(grpcCallSettings,"
          + " callSettings, clientContext);\n"
          + "}\n"
          + "}";
}
