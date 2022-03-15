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

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.RegionTag;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.test.framework.Assert;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ComposerTest {
  @Test
  public void gapicClass_addApacheLicense() {
    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString("com.google.showcase.v1beta1.stub")
            .setName("ComposerPostProcOnFooBar")
            .setScope(ScopeNode.PUBLIC)
            .build();
    List<Sample> samples =
        Arrays.asList(
            Sample.builder()
                .setRegionTag(
                    RegionTag.builder()
                        .setApiShortName("apiShortName")
                        .setServiceName("service")
                        .setRpcName("addApacheLicense")
                        .setOverloadDisambiguation("sample")
                        .build())
                .build());
    List<GapicClass> gapicClassWithHeaderList =
        Composer.addApacheLicense(Arrays.asList(GapicClass.create(Kind.TEST, classDef, samples)));

    Assert.assertGoldenClass(
        this.getClass(), gapicClassWithHeaderList.get(0), "ComposerPostProcOnFooBar.golden");
    Assert.assertGoldenSamples(
        this.getClass(),
        "",
        gapicClassWithHeaderList.get(0).classDefinition().packageString(),
        gapicClassWithHeaderList.get(0).samples());
  }
}
