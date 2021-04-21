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
import com.google.api.generator.gapic.composer.constants.ComposerConstants;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;

public class ServiceStubSettingsClassComposerTest {
  @Test
  public void generateServiceStubSettingsClasses_batchingWithEmptyResponses() throws IOException {
    GapicContext context = TestProtoLoaderUtil.parseLogging();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceStubSettingsClassComposer.instance().generate(context, protoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(
        this.getClass(), "LoggingServiceV2StubSettings.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "LoggingServiceV2StubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateServiceStubSettingsClasses_batchingWithNonemptyResponses()
      throws IOException {
    GapicContext context = TestProtoLoaderUtil.parsePubSubPublisher();
    Service protoService = context.services().get(0);
    assertEquals("Publisher", protoService.name());
    GapicClass clazz = ServiceStubSettingsClassComposer.instance().generate(context, protoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "PublisherStubSettings.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "PublisherStubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateServiceStubSettingsClasses_basic() throws IOException {
    GapicContext context = TestProtoLoaderUtil.parseShowcaseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz =
        ServiceStubSettingsClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "EchoStubSettings.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "EchoStubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateServiceStubSettingsClasses_deprecated() throws IOException {
    GapicContext context = TestProtoLoaderUtil.parseDeprecatedService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceStubSettingsClassComposer.instance().generate(context, protoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(
        this.getClass(), "DeprecatedServiceStubSettings.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "DeprecatedServiceStubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }
}
