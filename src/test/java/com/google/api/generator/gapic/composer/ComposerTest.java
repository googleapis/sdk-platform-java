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

import static org.junit.Assert.assertEquals;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.comment.CommentComposer;
import com.google.api.generator.gapic.composer.grpc.GrpcServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.composer.grpc.GrpcTestProtoLoader;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import com.google.common.collect.ImmutableList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ComposerTest {
  private final GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseEcho();
  private final Service echoProtoService = context.services().get(0);
  private final List<GapicClass> clazzes =
      Arrays.asList(
          GrpcServiceCallableFactoryClassComposer.instance().generate(context, echoProtoService));
  private final String protoPackage = context.gapicMetadata().getProtoPackage();
  private final List<Sample> samples = clazzes.get(0).samples();

  @Test
  public void gapicClass_addApacheLicense() {
    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString("com.google.showcase.v1beta1.stub")
            .setName("ComposerPostProcOnFooBar")
            .setScope(ScopeNode.PUBLIC)
            .build();
    List<GapicClass> gapicClassWithHeaderList =
        Composer.addApacheLicense(Arrays.asList(GapicClass.create(Kind.TEST, classDef)));
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    gapicClassWithHeaderList.get(0).classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "ComposerPostProcOnFooBar.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "ComposerPostProcOnFooBar.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void composeSamples_showcase() {
    for (Sample sample : samples) {
      assertEquals(
          "File header will be empty before composing samples",
          sample.fileHeader(),
          ImmutableList.of());
      assertEquals(
          "ApiShortName will be empty before composing samples",
          sample.regionTag().apiShortName(),
          "");
      assertEquals(
          "ApiVersion will be empty before composing samples", sample.regionTag().apiVersion(), "");
    }

    List<Sample> composedSamples =
        Composer.prepareExecutableSamples(clazzes, protoPackage).get(0).samples();

    for (Sample sample : composedSamples) {
      assertEquals(
          "File header should be apache",
          sample.fileHeader(),
          Arrays.asList(CommentComposer.APACHE_LICENSE_COMMENT));
      assertEquals(
          "ApiShortName should be showcase", sample.regionTag().apiShortName(), "showcase");
      assertEquals("ApiVersion should be v1beta1", sample.regionTag().apiVersion(), "v1beta1");
    }
  }

  @Test
  public void composeSamples_parseProtoPackage() {
    String protoPack = "google.cloud.accessapproval.v1";
    List<Sample> composedSamples =
        Composer.prepareExecutableSamples(clazzes, protoPack).get(0).samples();

    for (Sample sample : composedSamples) {
      assertEquals(
          "ApiShortName should be accessapproval",
          sample.regionTag().apiShortName(),
          "accessapproval");
      assertEquals("ApiVersion should be v1", sample.regionTag().apiVersion(), "v1");
    }

    protoPack = "google.cloud.vision.v1p1beta1";
    composedSamples = Composer.prepareExecutableSamples(clazzes, protoPack).get(0).samples();
    for (Sample sample : composedSamples) {
      assertEquals("ApiShortName should be vision", sample.regionTag().apiShortName(), "vision");
      assertEquals("ApiVersion should be v1p1beta1", sample.regionTag().apiVersion(), "v1p1beta1");
    }

    protoPack = "google.cloud.vision";
    composedSamples = Composer.prepareExecutableSamples(clazzes, protoPack).get(0).samples();
    for (Sample sample : composedSamples) {
      assertEquals("ApiShortName should be vision", sample.regionTag().apiShortName(), "vision");
      assertEquals("ApiVersion should be empty", sample.regionTag().apiVersion(), "");
    }
  }
}
