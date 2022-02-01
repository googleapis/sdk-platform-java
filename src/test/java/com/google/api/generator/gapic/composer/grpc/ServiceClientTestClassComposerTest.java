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

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Utils;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.api.generator.test.framework.Assert.assertCodeEquals;
import static com.google.api.generator.test.framework.Assert.assertEmptySamples;
import static org.junit.Assert.assertEquals;

public class ServiceClientTestClassComposerTest {
  @Test
  public void generateClientTest_echoClient() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz =
        ServiceClientTestClassComposer.instance().generate(context, echoProtoService);
    assertEmptySamples(clazz.samples());

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "EchoClientTest.golden", visitor.write());
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(this.getClass()), "EchoClientTest.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateClientTest_deprecatedServiceClient() {
    GapicContext context = GrpcTestProtoLoader.instance().parseDeprecatedService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientTestClassComposer.instance().generate(context, protoService);
    assertEmptySamples(clazz.samples());

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "DeprecatedServiceClientTest.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "DeprecatedServiceClientTest.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateClientTest_testingClientResnameWithOnePatternWithNonSlashSepNames() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseTesting();
    Service testingProtoService = context.services().get(0);
    GapicClass clazz =
        ServiceClientTestClassComposer.instance().generate(context, testingProtoService);
    assertEmptySamples(clazz.samples());

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "TestingClientTest.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "TestingClientTest.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateClientTest_pubSubPublisherClient() {
    GapicContext context = GrpcTestProtoLoader.instance().parsePubSubPublisher();
    Service subscriptionService = context.services().get(1);
    assertEquals("Subscriber", subscriptionService.name());
    GapicClass clazz =
        ServiceClientTestClassComposer.instance().generate(context, subscriptionService);
    assertEmptySamples(clazz.samples());

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "SubscriberClientTest.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "SubscriberClientTest.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateClientTest_logging() {
    GapicContext context = GrpcTestProtoLoader.instance().parseLogging();
    Service loggingService = context.services().get(0);
    GapicClass clazz = ServiceClientTestClassComposer.instance().generate(context, loggingService);
    assertEmptySamples(clazz.samples());

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "LoggingClientTest.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "LoggingClientTest.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }
}
