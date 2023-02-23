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

package com.google.api.generator.gapic.composer.rest;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.HttpBindings.HttpBinding;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import com.google.common.truth.Truth;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class HttpJsonServiceStubClassComposerTest {

  private HttpJsonServiceStubClassComposer composer;

  @Before
  public void setUp() throws Exception {
    composer = HttpJsonServiceStubClassComposer.instance();
  }

  @Test
  public void generateComplianceServiceClasses() {
    GapicContext context = RestTestProtoLoader.instance().parseCompliance();
    Service complianceProtoServices = context.services().get(0);
    GapicClass clazz = composer.generate(context, complianceProtoServices);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "HttpJsonComplianceStub.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "HttpJsonComplianceStub.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateEchoServiceClasses() {
    GapicContext context = RestTestProtoLoader.instance().parseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz = composer.generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "HttpJsonEchoStub.golden", visitor.write());
    Path goldenFilePath =
            Paths.get(Utils.getGoldenDir(this.getClass()), "HttpJsonEchoStub.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void
      getBindingFieldMethodName_shouldReturnGetFieldListIfTheFieldIsInLastPositionAndIsRepeated() {
    Field field =
        Field.builder()
            .setIsRepeated(true)
            .setName("doesNotMatter")
            .setType(TypeNode.OBJECT)
            .build();
    HttpBinding httpBinding =
        HttpBinding.builder().setField(field).setName("doesNotMatter").build();
    String actual = composer.getBindingFieldMethodName(httpBinding, 4, 3, "Values");
    Truth.assertThat(actual).isEqualTo("getValuesList");
  }

  @Test
  public void
      getBindingFieldMethodName_shouldReturnGetFieldValueIfTheFieldIsInLastPositionAndIsEnum() {
    Field field =
        Field.builder().setIsEnum(true).setName("doesNotMatter").setType(TypeNode.OBJECT).build();
    HttpBinding httpBinding =
        HttpBinding.builder().setField(field).setName("doesNotMatter").build();
    String actual = composer.getBindingFieldMethodName(httpBinding, 4, 3, "Enums");
    Truth.assertThat(actual).isEqualTo("getEnumsValue");
  }

  @Test
  public void
      getBindingFieldMethodName_shouldReturnGetFieldIfTheFieldIsInLastPositionAndNotRepeatedOrEnum() {
    Field field = Field.builder().setName("doesNotMatter").setType(TypeNode.OBJECT).build();
    HttpBinding httpBinding =
        HttpBinding.builder().setField(field).setName("doesNotMatter").build();
    String actual = composer.getBindingFieldMethodName(httpBinding, 4, 3, "Value");
    Truth.assertThat(actual).isEqualTo("getValue");
  }

  @Test
  public void getBindingFieldMethodName_shouldReturnGetFieldIfTheFieldIsNotInLastPosition() {
    Field field = Field.builder().setName("doesNotMatter").setType(TypeNode.OBJECT).build();
    HttpBinding httpBinding =
        HttpBinding.builder().setField(field).setName("doesNotMatter").build();
    String actual = composer.getBindingFieldMethodName(httpBinding, 4, 1, "Value");
    Truth.assertThat(actual).isEqualTo("getValue");
  }
}
