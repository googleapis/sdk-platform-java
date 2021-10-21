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
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

public class GrpcServiceStubClassComposerTest {
  @Test
  public void generateGrpcServiceStubClass_simple() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz = GrpcServiceStubClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "GrpcEchoStub.golden", visitor.write());
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(this.getClass()), "GrpcEchoStub.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateGrpcServiceStubClass_deprecated() {
    GapicContext context = GrpcTestProtoLoader.instance().parseDeprecatedService();
    Service protoService = context.services().get(0);
    GapicClass clazz = GrpcServiceStubClassComposer.instance().generate(context, protoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "GrpcDeprecatedServiceStub.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "GrpcDeprecatedServiceStub.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateGrpcServiceStubClass_httpBindings() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseTesting();
    Service service = context.services().get(0);
    GapicClass clazz = GrpcServiceStubClassComposer.instance().generate(context, service);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "GrpcTestingStub.golden", visitor.write());
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(this.getClass()), "GrpcTestingStub.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateGrpcServiceStubClass_httpBindingsWithSubMessageFields() {
    GapicContext context = GrpcTestProtoLoader.instance().parsePubSubPublisher();
    Service service = context.services().get(0);
    GapicClass clazz = GrpcServiceStubClassComposer.instance().generate(context, service);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "GrpcPublisherStub.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "GrpcPublisherStub.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateGrpcServiceStubClass_createBatchingCallable() {
    GapicContext context = GrpcTestProtoLoader.instance().parseLogging();
    Service service = context.services().get(0);
    GapicClass clazz = GrpcServiceStubClassComposer.instance().generate(context, service);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "GrpcLoggingStub.golden", visitor.write());
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(this.getClass()), "GrpcLoggingStub.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }
}
