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

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Utils;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

public class ServiceClientClassComposerTest {
  @Test
  public void generateServiceClasses() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "EchoClient.golden", visitor.write());
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(this.getClass()), "EchoClient.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateServiceClasses_deprecated() {
    GapicContext context = GrpcTestProtoLoader.instance().parseDeprecatedService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, protoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "DeprecatedServiceClient.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "DeprecatedServiceClient.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateServiceClasses_methodSignatureHasNestedFields() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseIdentity();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, protoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "IdentityClient.golden", visitor.write());
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(this.getClass()), "IdentityClient.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateServiceClasses_bookshopNameConflicts() {
    GapicContext context = GrpcTestProtoLoader.instance().parseBookshopService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, protoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "BookshopClient.golden", visitor.write());
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(this.getClass()), "BookshopClient.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }
}
