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

public class MockServiceImplClassComposerTest {
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
        MockServiceImplClassComposer.instance().generate(echoProtoService, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1;\n"
          + "\n"
          + "import com.google.api.core.BetaApi;\n"
          + "import com.google.longrunning.Operation;\n"
          + "import com.google.protobuf.AbstractMessage;\n"
          + "import com.google.showcase.v1beta1.EchoGrpc.EchoImplBase;\n"
          + "import io.grpc.stub.StreamObserver;\n"
          + "import java.util.ArrayList;\n"
          + "import java.util.LinkedList;\n"
          + "import java.util.List;\n"
          + "import java.util.Queue;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "@BetaApi\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "public class MockEchoImpl extends EchoImplBase {\n"
          + "  private List<AbstractMessage> requests;\n"
          + "  private Queue<Object> responses;\n"
          + "\n"
          + "  public MockEchoImpl() {\n"
          + "    requests = new ArrayList<>();\n"
          + "    responses = new LinkedList<>();\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public List<AbstractMessage> getRequests() {\n"
          + "    return requests;\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void addResponse(AbstractMessage response) {\n"
          + "    responses.add(response);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void setResponses(List<AbstractMessage> responses) {\n"
          + "    this.responses = new LinkedList<Object>(responses);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void addException(Exception exception) {\n"
          + "    responses.add(exception);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void reset() {\n"
          + "    requests = new ArrayList<>();\n"
          + "    responses = new LinkedList<>();\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void echo(EchoRequest request, StreamObserver<EchoResponse>"
          + " responseObserver) {\n"
          + "    Object response = responses.remove();\n"
          + "    if (response instanceof EchoResponse) {\n"
          + "      requests.add(request);\n"
          + "      responseObserver.onNext(((EchoResponse) response));\n"
          + "      responseObserver.onCompleted();\n"
          + "    } else if (response instanceof Exception) {\n"
          + "      responseObserver.onError(((Exception) response));\n"
          + "    } else {\n"
          + "      responseObserver.onError();\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void expand(ExpandRequest request, StreamObserver<EchoResponse>"
          + " responseObserver) {\n"
          + "    Object response = responses.remove();\n"
          + "    if (response instanceof EchoResponse) {\n"
          + "      requests.add(request);\n"
          + "      responseObserver.onNext(((EchoResponse) response));\n"
          + "      responseObserver.onCompleted();\n"
          + "    } else if (response instanceof Exception) {\n"
          + "      responseObserver.onError(((Exception) response));\n"
          + "    } else {\n"
          + "      responseObserver.onError();\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public StreamObserver<EchoRequest> collect(final StreamObserver<EchoResponse>"
          + " responseObserver) {\n"
          + "    StreamObserver<EchoRequest> requestObserver =\n"
          + "        new StreamObserver<EchoRequest>() {\n"
          + "          @Override\n"
          + "          public void onNext(EchoRequest value) {\n"
          + "            requests.add(value);\n"
          + "            final Object response = responses.remove();\n"
          + "            if (response instanceof EchoResponse) {\n"
          + "              responseObserver.onNext(((EchoResponse) response));\n"
          + "            } else if (response instanceof Exception) {\n"
          + "              responseObserver.onError(((Exception) response));\n"
          + "            } else {\n"
          + "              responseObserver.onError();\n"
          + "            }\n"
          + "          }\n"
          + "\n"
          + "          @Override\n"
          + "          public void onError(Throwable t) {\n"
          + "            responseObserver.onError(t);\n"
          + "          }\n"
          + "\n"
          + "          @Override\n"
          + "          public void onCompleted() {\n"
          + "            responseObserver.onCompleted();\n"
          + "          }\n"
          + "        };\n"
          + "    return requestObserver;\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public StreamObserver<EchoRequest> chat(final StreamObserver<EchoResponse>"
          + " responseObserver) {\n"
          + "    StreamObserver<EchoRequest> requestObserver =\n"
          + "        new StreamObserver<EchoRequest>() {\n"
          + "          @Override\n"
          + "          public void onNext(EchoRequest value) {\n"
          + "            requests.add(value);\n"
          + "            final Object response = responses.remove();\n"
          + "            if (response instanceof EchoResponse) {\n"
          + "              responseObserver.onNext(((EchoResponse) response));\n"
          + "            } else if (response instanceof Exception) {\n"
          + "              responseObserver.onError(((Exception) response));\n"
          + "            } else {\n"
          + "              responseObserver.onError();\n"
          + "            }\n"
          + "          }\n"
          + "\n"
          + "          @Override\n"
          + "          public void onError(Throwable t) {\n"
          + "            responseObserver.onError(t);\n"
          + "          }\n"
          + "\n"
          + "          @Override\n"
          + "          public void onCompleted() {\n"
          + "            responseObserver.onCompleted();\n"
          + "          }\n"
          + "        };\n"
          + "    return requestObserver;\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public StreamObserver<EchoRequest> chatAgain(\n"
          + "      final StreamObserver<EchoResponse> responseObserver) {\n"
          + "    StreamObserver<EchoRequest> requestObserver =\n"
          + "        new StreamObserver<EchoRequest>() {\n"
          + "          @Override\n"
          + "          public void onNext(EchoRequest value) {\n"
          + "            requests.add(value);\n"
          + "            final Object response = responses.remove();\n"
          + "            if (response instanceof EchoResponse) {\n"
          + "              responseObserver.onNext(((EchoResponse) response));\n"
          + "            } else if (response instanceof Exception) {\n"
          + "              responseObserver.onError(((Exception) response));\n"
          + "            } else {\n"
          + "              responseObserver.onError();\n"
          + "            }\n"
          + "          }\n"
          + "\n"
          + "          @Override\n"
          + "          public void onError(Throwable t) {\n"
          + "            responseObserver.onError(t);\n"
          + "          }\n"
          + "\n"
          + "          @Override\n"
          + "          public void onCompleted() {\n"
          + "            responseObserver.onCompleted();\n"
          + "          }\n"
          + "        };\n"
          + "    return requestObserver;\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void pagedExpand(\n"
          + "      PagedExpandRequest request, StreamObserver<PagedExpandResponse>"
          + " responseObserver) {\n"
          + "    Object response = responses.remove();\n"
          + "    if (response instanceof PagedExpandResponse) {\n"
          + "      requests.add(request);\n"
          + "      responseObserver.onNext(((PagedExpandResponse) response));\n"
          + "      responseObserver.onCompleted();\n"
          + "    } else if (response instanceof Exception) {\n"
          + "      responseObserver.onError(((Exception) response));\n"
          + "    } else {\n"
          + "      responseObserver.onError();\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void wait(WaitRequest request, StreamObserver<Operation> responseObserver)"
          + " {\n"
          + "    Object response = responses.remove();\n"
          + "    if (response instanceof Operation) {\n"
          + "      requests.add(request);\n"
          + "      responseObserver.onNext(((Operation) response));\n"
          + "      responseObserver.onCompleted();\n"
          + "    } else if (response instanceof Exception) {\n"
          + "      responseObserver.onError(((Exception) response));\n"
          + "    } else {\n"
          + "      responseObserver.onError();\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void block(BlockRequest request, StreamObserver<BlockResponse>"
          + " responseObserver) {\n"
          + "    Object response = responses.remove();\n"
          + "    if (response instanceof BlockResponse) {\n"
          + "      requests.add(request);\n"
          + "      responseObserver.onNext(((BlockResponse) response));\n"
          + "      responseObserver.onCompleted();\n"
          + "    } else if (response instanceof Exception) {\n"
          + "      responseObserver.onError(((Exception) response));\n"
          + "    } else {\n"
          + "      responseObserver.onError();\n"
          + "    }\n"
          + "  }\n"
          + "}\n";
}
