// Copyright 2022 Google LLC
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

package com.google.api.generator.spring;

import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.spring.composer.SpringAutoConfigClassComposer;
import com.google.api.generator.test.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpringAutoConfigClassComposerTest {
  private GapicContext context;
  private Service echoProtoService;

  @Before
  public void setUp() {
    this.context = TestProtoLoader.instance().parseShowcaseEcho();
    this.echoProtoService = this.context.services().get(0);
  }

  @Test
  public void generateAutoConfigClazzTest() {
    GapicClass clazz =
        SpringAutoConfigClassComposer.instance().generate(this.context, this.echoProtoService);
    Assert.assertGoldenClass(this.getClass(), clazz, "SpringAutoConfigClass.golden");
  }
}
