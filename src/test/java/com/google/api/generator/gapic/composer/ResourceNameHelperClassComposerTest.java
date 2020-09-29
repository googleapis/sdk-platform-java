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

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.assertEquals;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import com.google.showcase.v1beta1.TestingOuterClass;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class ResourceNameHelperClassComposerTest {
  private ServiceDescriptor echoService;
  private FileDescriptor echoFileDescriptor;

  @Before
  public void setUp() {
    echoFileDescriptor = EchoOuterClass.getDescriptor();
    echoService = echoFileDescriptor.getServices().get(0);
    assertEquals(echoService.getName(), "Echo");
  }

  @Test
  public void getTokenSet_basic() {
    List<String> patterns =
        Arrays.asList(
            "projects/{project}/agent/sessions/{session}",
            "projects/{project}/agent/environments/{environment}/users/{user}/sessions/{session}");
    List<List<String>> tokenHierarchies = ResourceNameTokenizer.parseTokenHierarchy(patterns);

    Set<String> tokenSet = ResourceNameHelperClassComposer.getTokenSet(tokenHierarchies);
    assertEquals(
        tokenSet,
        Stream.of("project", "session", "environment", "user").collect(Collectors.toSet()));
  }

  @Test
  public void concatToUpperSnakeCaseName_basic() {
    assertEquals(
        "PROJECT_LOCATION_AUTOSCALING_POLICY",
        ResourceNameHelperClassComposer.concatToUpperSnakeCaseName(
            Arrays.asList("project", "location", "autoscaling_policy")));
  }

  @Test
  public void concatToUpperCamelCaseName_basic() {
    assertEquals(
        "ProjectLocationAutoscalingPolicy",
        ResourceNameHelperClassComposer.concatToUpperCamelCaseName(
            Arrays.asList("project", "location", "autoscaling_policy")));
  }

  @Test
  public void generateResourceNameClass_echoFoobarMultiplePatterns() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(echoFileDescriptor, messageTypes, resourceNames, outputResourceNames);

    ResourceName foobarResname = resourceNames.get("showcase.googleapis.com/Foobar");
    assertThat(outputResourceNames).contains(foobarResname);

    Service echoProtoService = services.get(0);
    GapicClass clazz = ResourceNameHelperClassComposer.instance().generate(foobarResname);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "FoobarName.golden", visitor.write());
    Path goldenFilePath = Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "FoobarName.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateResourceNameClass_testingSessionOnePattern() {
    FileDescriptor testingFileDescriptor = TestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals(testingService.getName(), "Testing");

    Map<String, Message> messageTypes = Parser.parseMessages(testingFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(testingFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            testingFileDescriptor, messageTypes, resourceNames, outputResourceNames);

    ResourceName sessionResname = resourceNames.get("showcase.googleapis.com/Session");
    assertThat(outputResourceNames).contains(sessionResname);

    Service testingProtoService = services.get(0);
    GapicClass clazz = ResourceNameHelperClassComposer.instance().generate(sessionResname);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "SessionName.golden", visitor.write());
    Path goldenFilePath = Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "SessionName.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }
  // TODO(miraleung): Add more tests for a single pattern.
}
