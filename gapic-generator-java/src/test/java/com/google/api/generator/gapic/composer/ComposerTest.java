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
import static org.junit.Assert.assertFalse;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.comment.CommentComposer;
import com.google.api.generator.gapic.composer.grpc.GrpcServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.RegionTag;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.GoldenTestHelper;
import com.google.api.generator.test.protoloader.GrpcTestProtoLoader;
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
          GrpcServiceCallableFactoryClassComposer.instance()
              .generate(context, echoProtoService)
              .withApiShortName(echoProtoService.apiShortName())
              .withApiVersion(echoProtoService.apiVersion()));
  private final Sample sample =
      Sample.builder()
          .setRegionTag(
              RegionTag.builder().setServiceName("serviceName").setRpcName("rpcName").build())
          .build();
  private List<Sample> ListofSamples = Arrays.asList(new Sample[] {sample});

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
    GoldenTestHelper.saveCodegenToFile(
        this.getClass(), "ComposerPostProcOnFooBar.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(
            GoldenTestHelper.getGoldenDir(this.getClass()), "ComposerPostProcOnFooBar.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void composeSamples_showcase() {
    GapicClass testClass = clazzes.get(0).withSamples(ListofSamples);
    List<GapicClass> testClassList = Arrays.asList(new GapicClass[] {testClass});

    List<Sample> composedSamples =
        Composer.prepareExecutableSamples(testClassList).get(0).samples();

    assertFalse(composedSamples.isEmpty());
    for (Sample sample : composedSamples) {
      assertEquals(
          "File header should be APACHE",
          Arrays.asList(CommentComposer.APACHE_LICENSE_COMMENT),
          sample.fileHeader());
      assertEquals(
          "ApiShortName should be Localhost7469",
          "Localhost7469",
          sample.regionTag().apiShortName());
      assertEquals("ApiVersion should be V1Beta1", "V1Beta1", sample.regionTag().apiVersion());
    }
  }

  @Test
  public void gapicClass_addRegionTagAndHeaderToSample() {
    Sample testSample;
    testSample = Composer.addRegionTagAndHeaderToSample(sample, "showcase", "v1");
    assertEquals("Showcase", testSample.regionTag().apiShortName());
    assertEquals("V1", testSample.regionTag().apiVersion());
    assertEquals(Arrays.asList(CommentComposer.APACHE_LICENSE_COMMENT), testSample.fileHeader());
  }

  @Test
  public void composeSamples_parseProtoPackage() {

    String defaultHost = "accessapproval.googleapis.com:443";
    String protoPack = "google.cloud.accessapproval.v1";
    Service testService =
        echoProtoService.toBuilder().setDefaultHost(defaultHost).setProtoPakkage(protoPack).build();
    List<GapicClass> testClassList = getTestClassListFromService(testService);
    List<Sample> composedSamples =
        Composer.prepareExecutableSamples(testClassList).get(0).samples();

    // If samples is empty, the test automatically passes without checking.
    assertFalse(composedSamples.isEmpty());

    for (Sample sample : composedSamples) {
      assertEquals(
          "ApiShortName should be Accessapproval",
          sample.regionTag().apiShortName(),
          "Accessapproval");
      assertEquals("ApiVersion should be V1", sample.regionTag().apiVersion(), "V1");
    }

    protoPack = "google.cloud.vision.v1p1beta1";
    defaultHost = "vision.googleapis.com";
    testService =
        testService.toBuilder().setDefaultHost(defaultHost).setProtoPakkage(protoPack).build();
    testClassList = getTestClassListFromService(testService);
    composedSamples = Composer.prepareExecutableSamples(testClassList).get(0).samples();
    // If samples is empty, the test automatically passes without checking.
    assertFalse(composedSamples.isEmpty());

    for (Sample sample : composedSamples) {
      assertEquals("ApiShortName should be Vision", sample.regionTag().apiShortName(), "Vision");
      assertEquals("ApiVersion should be V1P1Beta1", sample.regionTag().apiVersion(), "V1P1Beta1");
    }

    protoPack = "google.cloud.vision";
    testService =
        testService.toBuilder().setDefaultHost(defaultHost).setProtoPakkage(protoPack).build();
    testClassList = getTestClassListFromService(testService);
    composedSamples = Composer.prepareExecutableSamples(testClassList).get(0).samples();
    // If samples is empty, the test automatically passes without checking.
    assertFalse(composedSamples.isEmpty());

    for (Sample sample : composedSamples) {
      assertEquals("ApiShortName should be Vision", sample.regionTag().apiShortName(), "Vision");
      assertEquals("ApiVersion should be empty", sample.regionTag().apiVersion(), "");
    }
  }

  private List<GapicClass> getTestClassListFromService(Service testService) {
    GapicClass testClass =
        GrpcServiceCallableFactoryClassComposer.instance()
            .generate(context, testService)
            .withSamples(ListofSamples)
            .withApiShortName(testService.apiShortName())
            .withApiVersion(testService.apiVersion());
    List<GapicClass> testClassList = Arrays.asList(new GapicClass[] {testClass});
    return testClassList;
  }
}
