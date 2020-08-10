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
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
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
  public void parseTokenHierarchy_basic() {
    List<String> patterns =
        Arrays.asList(
            "projects/{project}/locations/{location}/autoscalingPolicies/{autoscaling_policy}",
            "projects/{project}/regions/{region}/autoscalingPolicies/{autoscaling_policy}",
            "projects/{project}/autoscalingPolicies/{autoscaling_policy}");
    List<List<String>> tokenHierarchies =
        ResourceNameHelperClassComposer.parseTokenHierarchy(patterns);
    assertEquals(3, tokenHierarchies.size());
    assertThat(tokenHierarchies.get(0))
        .containsExactly("project", "location", "autoscaling_policy");
    assertThat(tokenHierarchies.get(1)).containsExactly("project", "region", "autoscaling_policy");
    assertThat(tokenHierarchies.get(2)).containsExactly("project", "autoscaling_policy");
  }

  @Test
  public void parseTokenHierarchy_singletonCollection() {
    List<String> patterns =
        Arrays.asList(
            "projects/{project}/agent/sessions/{session}",
            "projects/{project}/agent/environments/{environment}/users/{user}/sessions/{session}");
    List<List<String>> tokenHierarchies =
        ResourceNameHelperClassComposer.parseTokenHierarchy(patterns);
    assertEquals(2, tokenHierarchies.size());
    assertThat(tokenHierarchies.get(0)).containsExactly("project", "session");
    assertThat(tokenHierarchies.get(1))
        .containsExactly("project", "environment", "user", "session");
  }

  @Test
  public void parseTokenHierarchy_invalidPatterns() {
    List<String> patterns =
        Arrays.asList(
            "projects/{project}/agent/sessions/{session}/anotherSingleton",
            "{project}/agent/environments/{environment}/users/{user}/sessions/{session}");
    List<List<String>> tokenHierarchies =
        ResourceNameHelperClassComposer.parseTokenHierarchy(patterns);
    assertEquals(2, tokenHierarchies.size());
    assertThat(tokenHierarchies.get(0)).containsExactly("project", "session");
    assertThat(tokenHierarchies.get(1))
        .containsExactly("project", "environment", "user", "session");
  }

  @Test
  public void getTokenSet_basic() {
    List<String> patterns =
        Arrays.asList(
            "projects/{project}/agent/sessions/{session}",
            "projects/{project}/agent/environments/{environment}/users/{user}/sessions/{session}");
    List<List<String>> tokenHierarchies =
        ResourceNameHelperClassComposer.parseTokenHierarchy(patterns);

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
  public void generateResourceNameClass() {
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
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1;\n"
          + "\n"
          + "import com.google.api.pathtemplate.PathTemplate;\n"
          + "import com.google.api.resourcenames.ResourceName;\n"
          + "import java.util.Map;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "public class FoobarName implements ResourceName {\n"
          + "  private static final PathTemplate PROJECT_FOOBAR =\n"
          + "     "
          + " PathTemplate.createWithoutUrlEncoding(\"projects/{project}/foobars/{foobar}\");\n"
          + "  private static final PathTemplate PROJECT_VARIANT_FOOBAR =\n"
          + "      PathTemplate.createWithoutUrlEncoding(\n"
          + "          \"projects/{project}/chocolate/variants/{variant}/foobars/{foobar}\");\n"
          + "  private static final PathTemplate FOOBAR =\n"
          + "      PathTemplate.createWithoutUrlEncoding(\"foobars/{foobar}\");\n"
          + "  private volatile Map<String, String> fieldValuesMap;\n"
          + "  private PathTemplate pathTemplate;\n"
          + "  private String fixedValue;\n"
          + "  private String foobar;\n"
          + "  private String variant;\n"
          + "  private String project;\n"
          + "}\n";
}
