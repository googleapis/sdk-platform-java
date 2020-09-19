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
import com.google.showcase.v1beta1.TestingOuterClass;
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
  public void parseTokenHierarchy_wildcards() {
    List<String> patterns =
        Arrays.asList(
            "projects/{project}/metricDescriptors/{metric_descriptor=**}",
            "organizations/{organization}/metricDescriptors/{metric_descriptor=**}",
            "folders/{folder=**}/metricDescriptors/{metric_descriptor}");
    List<List<String>> tokenHierarchies =
        ResourceNameHelperClassComposer.parseTokenHierarchy(patterns);
    assertEquals(3, tokenHierarchies.size());
    assertThat(tokenHierarchies.get(0)).containsExactly("project", "metric_descriptor");
    assertThat(tokenHierarchies.get(1)).containsExactly("organization", "metric_descriptor");
    assertThat(tokenHierarchies.get(2)).containsExactly("folder", "metric_descriptor");
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
  public void parseTokenHierarchy_singletonCollectionAndNonSlashSeparators() {
    List<String> patterns =
        Arrays.asList(
            "users/{user}/profile/blurbs/legacy/{legacy_user}~{blurb}",
            "users/{user}/profile/blurbs/{blurb}",
            "rooms/{room}/blurbs/{blurb}",
            "users/{user}/profile/blurbs/legacy/{legacy_document}_{blurb}",
            "users/{user}/profile/blurbs/legacy/{legacy_book}-{blurb}",
            "rooms/{room}/blurbs/legacy/{legacy_room}.{blurb}");

    List<List<String>> tokenHierarchies =
        ResourceNameHelperClassComposer.parseTokenHierarchy(patterns);
    assertEquals(6, tokenHierarchies.size());
    assertThat(tokenHierarchies.get(0)).containsExactly("user", "legacy_user_blurb");
    assertThat(tokenHierarchies.get(1)).containsExactly("user", "blurb");
    assertThat(tokenHierarchies.get(2)).containsExactly("room", "blurb");
    assertThat(tokenHierarchies.get(3)).containsExactly("user", "legacy_document_blurb");
    assertThat(tokenHierarchies.get(4)).containsExactly("user", "legacy_book_blurb");
    assertThat(tokenHierarchies.get(5)).containsExactly("room", "legacy_room_blurb");
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
    assertEquals(EXPECTED_FOOBAR_NAME_CLASS_STRING, visitor.write());
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
    assertEquals(EXPECTED_SESSION_NAME_CLASS_STRING, visitor.write());
  }

  // TODO(miraleung): Add more tests for a single pattern.
  // TODO(miraleung): Update this when a file-diffing test mechanism is in place.
  private static final String EXPECTED_FOOBAR_NAME_CLASS_STRING =
      "package com.google.showcase.v1beta1;\n"
          + "\n"
          + "import com.google.api.core.BetaApi;\n"
          + "import com.google.api.pathtemplate.PathTemplate;\n"
          + "import com.google.api.pathtemplate.ValidationException;\n"
          + "import com.google.api.resourcenames.ResourceName;\n"
          + "import com.google.common.base.Preconditions;\n"
          + "import com.google.common.collect.ImmutableMap;\n"
          + "import java.util.ArrayList;\n"
          + "import java.util.List;\n"
          + "import java.util.Map;\n"
          + "import java.util.Objects;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "// AUTO-GENERATED DOCUMENTATION AND CLASS.\n"
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
          + "  private static final PathTemplate BAR_FOO_FOOBAR =\n"
          + "     "
          + " PathTemplate.createWithoutUrlEncoding(\"bar_foos/{bar_foo}/foobars/{foobar}\");\n"
          + "  private volatile Map<String, String> fieldValuesMap;\n"
          + "  private PathTemplate pathTemplate;\n"
          + "  private String fixedValue;\n"
          + "  private final String project;\n"
          + "  private final String foobar;\n"
          + "  private final String variant;\n"
          + "  private final String barFoo;\n"
          + "\n"
          + "  private FoobarName(Builder builder) {\n"
          + "    project = Preconditions.checkNotNull(builder.getProject());\n"
          + "    foobar = Preconditions.checkNotNull(builder.getFoobar());\n"
          + "    pathTemplate = PROJECT_FOOBAR;\n"
          + "  }\n"
          + "\n"
          + "  private FoobarName(ProjectVariantFoobarBuilder builder) {\n"
          + "    project = Preconditions.checkNotNull(builder.getProject());\n"
          + "    variant = Preconditions.checkNotNull(builder.getVariant());\n"
          + "    foobar = Preconditions.checkNotNull(builder.getFoobar());\n"
          + "    pathTemplate = PROJECT_VARIANT_FOOBAR;\n"
          + "  }\n"
          + "\n"
          + "  private FoobarName(FoobarBuilder builder) {\n"
          + "    foobar = Preconditions.checkNotNull(builder.getFoobar());\n"
          + "    pathTemplate = FOOBAR;\n"
          + "  }\n"
          + "\n"
          + "  private FoobarName(BarFooFoobarBuilder builder) {\n"
          + "    barFoo = Preconditions.checkNotNull(builder.getBarFoo());\n"
          + "    foobar = Preconditions.checkNotNull(builder.getFoobar());\n"
          + "    pathTemplate = BAR_FOO_FOOBAR;\n"
          + "  }\n"
          + "\n"
          + "  public String getProject() {\n"
          + "    return project;\n"
          + "  }\n"
          + "\n"
          + "  public String getFoobar() {\n"
          + "    return foobar;\n"
          + "  }\n"
          + "\n"
          + "  public String getVariant() {\n"
          + "    return variant;\n"
          + "  }\n"
          + "\n"
          + "  public String getBarFoo() {\n"
          + "    return barFoo;\n"
          + "  }\n"
          + "\n"
          + "  public static Builder newBuilder() {\n"
          + "    return new Builder();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The per-pattern Builders are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static Builder newProjectFoobarBuilder() {\n"
          + "    return new Builder();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The per-pattern Builders are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static ProjectVariantFoobarBuilder newProjectVariantFoobarBuilder() {\n"
          + "    return new ProjectVariantFoobarBuilder();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The per-pattern Builders are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static FoobarBuilder newFoobarBuilder() {\n"
          + "    return new FoobarBuilder();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The per-pattern Builders are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static BarFooFoobarBuilder newBarFooFoobarBuilder() {\n"
          + "    return new BarFooFoobarBuilder();\n"
          + "  }\n"
          + "\n"
          + "  public Builder toBuilder() {\n"
          + "    return new Builder(this);\n"
          + "  }\n"
          + "\n"
          + "  public static FoobarName of(String project, String foobar) {\n"
          + "    return newBuilder().setProject(project).setFoobar(foobar).build();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The static create methods are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static FoobarName ofProjectFoobarBuilder(String project, String foobar) {\n"
          + "    return newBuilder().setProject(project).setFoobar(foobar).build();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The static create methods are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static FoobarName ofProjectVariantFoobarBuilder(\n"
          + "      String project, String variant, String foobar) {\n"
          + "    return newProjectVariantFoobarBuilder()\n"
          + "        .setProject(project)\n"
          + "        .setVariant(variant)\n"
          + "        .setFoobar(foobar)\n"
          + "        .build();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The static create methods are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static FoobarName ofFoobarBuilder(String foobar) {\n"
          + "    return newFoobarBuilder().setFoobar(foobar).build();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The static create methods are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static FoobarName ofBarFooFoobarBuilder(String barFoo, String foobar) {\n"
          + "    return newBarFooFoobarBuilder().setBarFoo(barFoo).setFoobar(foobar).build();\n"
          + "  }\n"
          + "\n"
          + "  public static String format(String project, String foobar) {\n"
          + "    return newBuilder().setProject(project).setFoobar(foobar).build().toString();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The static format methods are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static String formatProjectFoobarBuilder(String project, String foobar) {\n"
          + "    return newBuilder().setProject(project).setFoobar(foobar).build().toString();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The static format methods are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static String formatProjectVariantFoobarBuilder(\n"
          + "      String project, String variant, String foobar) {\n"
          + "    return newProjectVariantFoobarBuilder()\n"
          + "        .setProject(project)\n"
          + "        .setVariant(variant)\n"
          + "        .setFoobar(foobar)\n"
          + "        .build()\n"
          + "        .toString();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The static format methods are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static String formatFoobarBuilder(String foobar) {\n"
          + "    return newFoobarBuilder().setFoobar(foobar).build().toString();\n"
          + "  }\n"
          + "\n"
          + "  @BetaApi(\"The static format methods are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static String formatBarFooFoobarBuilder(String barFoo, String foobar) {\n"
          + "    return"
          + " newBarFooFoobarBuilder().setBarFoo(barFoo).setFoobar(foobar).build().toString();\n"
          + "  }\n"
          + "\n"
          + "  public static FoobarName parse(String formattedString) {\n"
          + "    if (formattedString.isEmpty()) {\n"
          + "      return null;\n"
          + "    }\n"
          + "    if (PROJECT_FOOBAR.matches(formattedString)) {\n"
          + "      Map<String, String> matchMap = PROJECT_FOOBAR.match(formattedString);\n"
          + "      return ofProjectFoobarBuilder(matchMap.get(\"project\"),"
          + " matchMap.get(\"foobar\"));\n"
          + "    } else if (PROJECT_VARIANT_FOOBAR.matches(formattedString)) {\n"
          + "      Map<String, String> matchMap = PROJECT_VARIANT_FOOBAR.match(formattedString);\n"
          + "      return ofProjectVariantFoobarBuilder(\n"
          + "          matchMap.get(\"project\"), matchMap.get(\"variant\"),"
          + " matchMap.get(\"foobar\"));\n"
          + "    } else if (FOOBAR.matches(formattedString)) {\n"
          + "      Map<String, String> matchMap = FOOBAR.match(formattedString);\n"
          + "      return ofFoobarBuilder(matchMap.get(\"foobar\"));\n"
          + "    } else if (BAR_FOO_FOOBAR.matches(formattedString)) {\n"
          + "      Map<String, String> matchMap = BAR_FOO_FOOBAR.match(formattedString);\n"
          + "      return ofBarFooFoobarBuilder(matchMap.get(\"bar_foo\"),"
          + " matchMap.get(\"foobar\"));\n"
          + "    }\n"
          + "    throw new ValidationException(\"FoobarName.parse: formattedString not in valid"
          + " format\");\n"
          + "  }\n"
          + "\n"
          + "  public static List<FoobarName> parseList(List<String> formattedStrings) {\n"
          + "    List<FoobarName> list = new ArrayList<>(formattedStrings.size());\n"
          + "    for (String formattedString : formattedStrings) {\n"
          + "      list.add(parse(formattedString));\n"
          + "    }\n"
          + "    return list;\n"
          + "  }\n"
          + "\n"
          + "  public static List<String> toStringList(List<FoobarName> values) {\n"
          + "    List<String> list = new ArrayList<>(values.size());\n"
          + "    for (FoobarName value : values) {\n"
          + "      if (Objects.isNull(value)) {\n"
          + "        list.add(\"\");\n"
          + "      } else {\n"
          + "        list.add(value.toString());\n"
          + "      }\n"
          + "    }\n"
          + "    return list;\n"
          + "  }\n"
          + "\n"
          + "  public static boolean isParsableFrom(String formattedString) {\n"
          + "    return PROJECT_FOOBAR.matches(formattedString)\n"
          + "        || PROJECT_VARIANT_FOOBAR.matches(formattedString)\n"
          + "        || FOOBAR.matches(formattedString)\n"
          + "        || BAR_FOO_FOOBAR.matches(formattedString);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public Map<String, String> getFieldValuesMap() {\n"
          + "    if (Objects.isNull(fieldValuesMap)) {\n"
          + "      synchronized (this) {\n"
          + "        if (Objects.isNull(fieldValuesMap)) {\n"
          + "          ImmutableMap.Builder<String, String> fieldMapBuilder ="
          + " ImmutableMap.builder();\n"
          + "          if (!Objects.isNull(project)) {\n"
          + "            fieldMapBuilder.put(\"project\", project);\n"
          + "          }\n"
          + "          if (!Objects.isNull(foobar)) {\n"
          + "            fieldMapBuilder.put(\"foobar\", foobar);\n"
          + "          }\n"
          + "          if (!Objects.isNull(variant)) {\n"
          + "            fieldMapBuilder.put(\"variant\", variant);\n"
          + "          }\n"
          + "          if (!Objects.isNull(barFoo)) {\n"
          + "            fieldMapBuilder.put(\"bar_foo\", barFoo);\n"
          + "          }\n"
          + "          fieldValuesMap = fieldMapBuilder.build();\n"
          + "        }\n"
          + "      }\n"
          + "    }\n"
          + "    return fieldValuesMap;\n"
          + "  }\n"
          + "\n"
          + "  public String getFieldValue(String fieldName) {\n"
          + "    return getFieldValuesMap().get(fieldName);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public String toString() {\n"
          + "    return !Objects.isNull(fixedValue) ? fixedValue :"
          + " pathTemplate.instantiate(getFieldValuesMap());\n"
          + "  }\n"
          + "\n"
          + "  /** Builder for projects/{project}/foobars/{foobar}. */\n"
          + "  public static class Builder {\n"
          + "    private String project;\n"
          + "    private String foobar;\n"
          + "\n"
          + "    private Builder() {}\n"
          + "\n"
          + "    public String getProject() {\n"
          + "      return project;\n"
          + "    }\n"
          + "\n"
          + "    public String getFoobar() {\n"
          + "      return foobar;\n"
          + "    }\n"
          + "\n"
          + "    public Builder setProject(String project) {\n"
          + "      this.project = project;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    public Builder setFoobar(String foobar) {\n"
          + "      this.foobar = foobar;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    private Builder(FoobarName foobarName) {\n"
          + "      Preconditions.checkArgument(\n"
          + "          Objects.equals(foobarName.pathTemplate, PROJECT_FOOBAR),\n"
          + "          \"toBuilder is only supported when FoobarName has the pattern of"
          + " projects/{project}/foobars/{foobar}\");\n"
          + "      project = foobarName.project;\n"
          + "      foobar = foobarName.foobar;\n"
          + "    }\n"
          + "\n"
          + "    public FoobarName build() {\n"
          + "      return new FoobarName(this);\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  /** Builder for projects/{project}/chocolate/variants/{variant}/foobars/{foobar}."
          + " */\n"
          + "  @BetaApi(\"The per-pattern Builders are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static class ProjectVariantFoobarBuilder {\n"
          + "    private String project;\n"
          + "    private String variant;\n"
          + "    private String foobar;\n"
          + "\n"
          + "    private ProjectVariantFoobarBuilder() {}\n"
          + "\n"
          + "    public String getProject() {\n"
          + "      return project;\n"
          + "    }\n"
          + "\n"
          + "    public String getVariant() {\n"
          + "      return variant;\n"
          + "    }\n"
          + "\n"
          + "    public String getFoobar() {\n"
          + "      return foobar;\n"
          + "    }\n"
          + "\n"
          + "    public ProjectVariantFoobarBuilder setProject(String project) {\n"
          + "      this.project = project;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    public ProjectVariantFoobarBuilder setVariant(String variant) {\n"
          + "      this.variant = variant;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    public ProjectVariantFoobarBuilder setFoobar(String foobar) {\n"
          + "      this.foobar = foobar;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    public FoobarName build() {\n"
          + "      return new FoobarName(this);\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  /** Builder for foobars/{foobar}. */\n"
          + "  @BetaApi(\"The per-pattern Builders are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static class FoobarBuilder {\n"
          + "    private String foobar;\n"
          + "\n"
          + "    private FoobarBuilder() {}\n"
          + "\n"
          + "    public String getFoobar() {\n"
          + "      return foobar;\n"
          + "    }\n"
          + "\n"
          + "    public FoobarBuilder setFoobar(String foobar) {\n"
          + "      this.foobar = foobar;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    public FoobarName build() {\n"
          + "      return new FoobarName(this);\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  /** Builder for bar_foos/{bar_foo}/foobars/{foobar}. */\n"
          + "  @BetaApi(\"The per-pattern Builders are not stable yet and may be changed in the"
          + " future.\")\n"
          + "  public static class BarFooFoobarBuilder {\n"
          + "    private String barFoo;\n"
          + "    private String foobar;\n"
          + "\n"
          + "    private BarFooFoobarBuilder() {}\n"
          + "\n"
          + "    public String getBarFoo() {\n"
          + "      return barFoo;\n"
          + "    }\n"
          + "\n"
          + "    public String getFoobar() {\n"
          + "      return foobar;\n"
          + "    }\n"
          + "\n"
          + "    public BarFooFoobarBuilder setBarFoo(String barFoo) {\n"
          + "      this.barFoo = barFoo;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    public BarFooFoobarBuilder setFoobar(String foobar) {\n"
          + "      this.foobar = foobar;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    public FoobarName build() {\n"
          + "      return new FoobarName(this);\n"
          + "    }\n"
          + "  }\n"
          + "}\n";

  private static final String EXPECTED_SESSION_NAME_CLASS_STRING =
      "package com.google.showcase.v1beta1;\n"
          + "\n"
          + "import com.google.api.pathtemplate.PathTemplate;\n"
          + "import com.google.api.resourcenames.ResourceName;\n"
          + "import com.google.common.base.Preconditions;\n"
          + "import com.google.common.collect.ImmutableMap;\n"
          + "import java.util.ArrayList;\n"
          + "import java.util.List;\n"
          + "import java.util.Map;\n"
          + "import java.util.Objects;\n"
          + "import javax.annotation.Generated;\n"
          + "\n"
          + "// AUTO-GENERATED DOCUMENTATION AND CLASS.\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "public class SessionName implements ResourceName {\n"
          + "  private static final PathTemplate SESSION =\n"
          + "      PathTemplate.createWithoutUrlEncoding(\"sessions/{session}\");\n"
          + "  private volatile Map<String, String> fieldValuesMap;\n"
          + "  private final String session;\n"
          + "\n"
          + "  private SessionName(Builder builder) {\n"
          + "    session = Preconditions.checkNotNull(builder.getSession());\n"
          + "  }\n"
          + "\n"
          + "  public String getSession() {\n"
          + "    return session;\n"
          + "  }\n"
          + "\n"
          + "  public static Builder newBuilder() {\n"
          + "    return new Builder();\n"
          + "  }\n"
          + "\n"
          + "  public Builder toBuilder() {\n"
          + "    return new Builder(this);\n"
          + "  }\n"
          + "\n"
          + "  public static SessionName of(String session) {\n"
          + "    return newBuilder().setSession(session).build();\n"
          + "  }\n"
          + "\n"
          + "  public static String format(String session) {\n"
          + "    return newBuilder().setSession(session).build().toString();\n"
          + "  }\n"
          + "\n"
          + "  public static SessionName parse(String formattedString) {\n"
          + "    if (formattedString.isEmpty()) {\n"
          + "      return null;\n"
          + "    }\n"
          + "    Map<String, String> matchMap =\n"
          + "        SESSION.validatedMatch(\n"
          + "            formattedString, \"SessionName.parse:"
          + " formattedString not in valid format\");\n"
          + "    return of(matchMap.get(\"session\"));\n"
          + "  }\n"
          + "\n"
          + "  public static List<SessionName> parseList(List<String> formattedStrings) {\n"
          + "    List<SessionName> list = new ArrayList<>(formattedStrings.size());\n"
          + "    for (String formattedString : formattedStrings) {\n"
          + "      list.add(parse(formattedString));\n"
          + "    }\n"
          + "    return list;\n"
          + "  }\n"
          + "\n"
          + "  public static List<String> toStringList(List<SessionName> values) {\n"
          + "    List<String> list = new ArrayList<>(values.size());\n"
          + "    for (SessionName value : values) {\n"
          + "      if (Objects.isNull(value)) {\n"
          + "        list.add(\"\");\n"
          + "      } else {\n"
          + "        list.add(value.toString());\n"
          + "      }\n"
          + "    }\n"
          + "    return list;\n"
          + "  }\n"
          + "\n"
          + "  public static boolean isParsableFrom(String formattedString) {\n"
          + "    return SESSION.matches(formattedString);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public Map<String, String> getFieldValuesMap() {\n"
          + "    if (Objects.isNull(fieldValuesMap)) {\n"
          + "      synchronized (this) {\n"
          + "        if (Objects.isNull(fieldValuesMap)) {\n"
          + "          ImmutableMap.Builder<String, String> fieldMapBuilder ="
          + " ImmutableMap.builder();\n"
          + "          if (!Objects.isNull(session)) {\n"
          + "            fieldMapBuilder.put(\"session\", session);\n"
          + "          }\n"
          + "          fieldValuesMap = fieldMapBuilder.build();\n"
          + "        }\n"
          + "      }\n"
          + "    }\n"
          + "    return fieldValuesMap;\n"
          + "  }\n"
          + "\n"
          + "  public String getFieldValue(String fieldName) {\n"
          + "    return getFieldValuesMap().get(fieldName);\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public String toString() {\n"
          + "    return SESSION.instantiate(\"session\", session);\n"
          + "  }\n"
          + "\n"
          + "  /** Builder for sessions/{session}. */\n"
          + "  public static class Builder {\n"
          + "    private String session;\n"
          + "\n"
          + "    private Builder() {}\n"
          + "\n"
          + "    public String getSession() {\n"
          + "      return session;\n"
          + "    }\n"
          + "\n"
          + "    public Builder setSession(String session) {\n"
          + "      this.session = session;\n"
          + "      return this;\n"
          + "    }\n"
          + "\n"
          + "    private Builder(SessionName sessionName) {\n"
          + "      session = sessionName.session;\n"
          + "    }\n"
          + "\n"
          + "    public SessionName build() {\n"
          + "      return new SessionName(this);\n"
          + "    }\n"
          + "  }\n"
          + "}\n";
}
