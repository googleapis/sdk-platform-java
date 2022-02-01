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

package com.google.api.generator.gapic.composer.grpc;

import static com.google.api.generator.test.framework.Assert.assertCodeEquals;
import static org.junit.Assert.assertEquals;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.samplecode.ExecutableSampleComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import org.junit.Test;

public class ServiceStubSettingsClassComposerTest {
  @Test
  public void generateServiceStubSettingsClasses_batchingWithEmptyResponses() {
    GapicContext context = GrpcTestProtoLoader.instance().parseLogging();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceStubSettingsClassComposer.instance().generate(context, protoService);
    Set<Sample> samples = clazz.samples();

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(
        this.getClass(), "LoggingServiceV2StubSettings.golden", visitor.write());
    String goldenDir = Utils.getGoldenDir(this.getClass());
    Path goldenFilePath = Paths.get(goldenDir, "LoggingServiceV2StubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
    assertGoldenSamples(
        samples,
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/servicesettings/", goldenDir));
  }

  @Test
  public void generateServiceStubSettingsClasses_batchingWithNonemptyResponses() {
    GapicContext context = GrpcTestProtoLoader.instance().parsePubSubPublisher();
    Service protoService = context.services().get(0);
    assertEquals("Publisher", protoService.name());
    GapicClass clazz = ServiceStubSettingsClassComposer.instance().generate(context, protoService);
    Set<Sample> samples = clazz.samples();

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "PublisherStubSettings.golden", visitor.write());
    String goldenDir = Utils.getGoldenDir(this.getClass());
    Path goldenFilePath = Paths.get(goldenDir, "PublisherStubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
    assertGoldenSamples(
        samples,
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/servicesettings/", goldenDir));
  }

  @Test
  public void generateServiceStubSettingsClasses_basic() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz =
        ServiceStubSettingsClassComposer.instance().generate(context, echoProtoService);
    Set<Sample> samples = clazz.samples();

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "EchoStubSettings.golden", visitor.write());
    String goldenDir = Utils.getGoldenDir(this.getClass());
    Path goldenFilePath = Paths.get(goldenDir, "EchoStubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
    assertGoldenSamples(
        samples,
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/servicesettings/", goldenDir));
  }

  @Test
  public void generateServiceStubSettingsClasses_deprecated() {
    GapicContext context = GrpcTestProtoLoader.instance().parseDeprecatedService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceStubSettingsClassComposer.instance().generate(context, protoService);
    Set<Sample> samples = clazz.samples();

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(
        this.getClass(), "DeprecatedServiceStubSettings.golden", visitor.write());
    String goldenDir = Utils.getGoldenDir(this.getClass());
    Path goldenFilePath = Paths.get(goldenDir, "DeprecatedServiceStubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
    assertGoldenSamples(
        samples,
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/servicesettings/", goldenDir));
  }

  private void assertGoldenSamples(Set<Sample> samples, String packkage, String goldenDir) {
    for (Sample sample : samples) {
      String fileName = sample.getName().concat(".golden");
      Path goldenFilePath = Paths.get(goldenDir, fileName);
      assertCodeEquals(
          goldenFilePath, ExecutableSampleComposer.createExecutableSample(sample, packkage));
    }
  }
}
