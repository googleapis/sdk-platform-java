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
import static com.google.api.generator.test.framework.Assert.assertSampleFileCount;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.samplecode.ExecutableSampleComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Utils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import org.junit.Test;

public class ServiceClientClassComposerTest {
  @Test
  public void generateServiceClasses() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, echoProtoService);
    String goldenDir = Utils.getGoldenDir(this.getClass());

    assertGoldenClass(clazz.classDefinition(), goldenDir, "EchoClient.golden");
    assertGoldenSamples(
        clazz.samples(),
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/echoclient/", goldenDir));
  }

  @Test
  public void generateServiceClasses_deprecated() {
    GapicContext context = GrpcTestProtoLoader.instance().parseDeprecatedService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, protoService);
    String goldenDir = Utils.getGoldenDir(this.getClass());

    assertGoldenClass(clazz.classDefinition(), goldenDir, "DeprecatedServiceClient.golden");
    assertGoldenSamples(
        clazz.samples(),
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/deprecatedserviceclient/", goldenDir));
  }

  @Test
  public void generateServiceClasses_methodSignatureHasNestedFields() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseIdentity();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, protoService);
    String goldenDir = Utils.getGoldenDir(this.getClass());

    assertGoldenClass(clazz.classDefinition(), goldenDir, "IdentityClient.golden");
    assertGoldenSamples(
        clazz.samples(),
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/identityclient/", goldenDir));
  }

  @Test
  public void generateServiceClasses_bookshopNameConflicts() {
    GapicContext context = GrpcTestProtoLoader.instance().parseBookshopService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, protoService);
    String goldenDir = Utils.getGoldenDir(this.getClass());

    assertGoldenClass(clazz.classDefinition(), goldenDir, "BookshopClient.golden");
    assertGoldenSamples(
        clazz.samples(),
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/bookshopclient/", goldenDir));
  }

  @Test
  public void generateServiceClasses_childTypeParentInJavadoc() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseMessaging();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, protoService);
    String goldenDir = Utils.getGoldenDir(this.getClass());

    assertGoldenClass(clazz.classDefinition(), goldenDir, "MessagingClient.golden");
    assertGoldenSamples(
        clazz.samples(),
        String.format("%s.samples", clazz.classDefinition().packageString()),
        String.format("%ssamples/messagingclient/", goldenDir));
  }

  private void assertGoldenClass(
      ClassDefinition classDefinition, String goldenDir, String fileName) {
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    classDefinition.accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), fileName, visitor.write());
    Path goldenFilePath = Paths.get(goldenDir, fileName);
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  private void assertGoldenSamples(Set<Sample> samples, String packkage, String goldenDir) {
    assertSampleFileCount(goldenDir, samples);

    for (Sample sample : samples) {
      String fileName = sample.getName().concat(".golden");
      Path goldenFilePath = Paths.get(goldenDir, fileName);
      assertCodeEquals(
          goldenFilePath, ExecutableSampleComposer.createExecutableSample(sample, packkage));
    }
  }
}
