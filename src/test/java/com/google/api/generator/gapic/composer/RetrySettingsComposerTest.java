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

import com.google.api.generator.engine.ast.BlockStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.protoparser.ServiceConfigParser;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class RetrySettingsComposerTest {
  private static final String JSON_DIRECTORY =
      "src/test/java/com/google/api/generator/gapic/testdata/";
  private static final VariableExpr RETRY_PARAM_DEFINITIONS_VAR_EXPR =
      createRetryParamDefinitionsVarExpr();

  private JavaWriterVisitor writerVisitor;

  @Before
  public void setUp() {
    writerVisitor = new JavaWriterVisitor();
  }

  @Test
  public void paramDefinitionsBlock_noConfigsFound() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    ServiceDescriptor echoServiceDescriptor = echoFileDescriptor.getServices().get(0);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(echoFileDescriptor, messageTypes, resourceNames, outputResourceNames);
    assertEquals(1, services.size());

    Service service = services.get(0);

    String jsonFilename = "retrying_grpc_service_config.json";
    Path jsonPath = Paths.get(JSON_DIRECTORY, jsonFilename);
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(serviceConfigOpt.isPresent());
    GapicServiceConfig serviceConfig = serviceConfigOpt.get();

    BlockStatement paramDefinitionsBlock =
        RetrySettingsComposer.createRetryParamDefinitionsBlock(
            service, serviceConfig, RETRY_PARAM_DEFINITIONS_VAR_EXPR);

    paramDefinitionsBlock.accept(writerVisitor);
    String expected =
        createLines(
            "static {\n",
            "ImmutableMap.Builder<String, RetrySettings> definitions = ImmutableMap.builder();\n",
            "RetrySettings settings = null;\n",
            "RETRY_PARAM_DEFINITIONS = definitions.build();\n",
            "}\n");
    assertEquals(expected, writerVisitor.write());
  }

  @Test
  public void paramDefinitionsBlock_basic() {
    // TODO(miraleung): Fill this out.
  }

  private static VariableExpr createRetryParamDefinitionsVarExpr() {
    TypeNode retrySettingsType =
        TypeNode.withReference(
            VaporReference.builder()
                .setPakkage("com.google.api.gax.retrying")
                .setName("RetrySettings")
                .build());
    TypeNode varType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ImmutableMap.class)
                .setGenerics(
                    Arrays.asList(TypeNode.STRING, retrySettingsType).stream()
                        .map(t -> t.reference())
                        .collect(Collectors.toList()))
                .build());
    return VariableExpr.withVariable(
        Variable.builder().setType(varType).setName("RETRY_PARAM_DEFINITIONS").build());
  }

  private static String createLines(String... lines) {
    // Cast to get rid of warnings.
    return String.format(new String(new char[lines.length]).replace("\0", "%s"), (Object[]) lines);
  }
}
