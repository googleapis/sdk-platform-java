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

import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import org.junit.Test;

public class ServiceSettingsClassComposerTest {
  private final String goldenSampleDir =
      Utils.getGoldenDir(this.getClass()) + "samples/servicesettings/";

  @Test
  public void generateServiceClasses() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz = ServiceSettingsClassComposer.instance().generate(context, echoProtoService);

    Assert.assertGoldenClass(this.getClass(), clazz, "EchoSettings.golden");
    Assert.assertGoldenSamples(
        clazz.samples(), clazz.classDefinition().packageString(), goldenSampleDir);
  }

  @Test
  public void generateServiceClasses_deprecated() {
    GapicContext context = GrpcTestProtoLoader.instance().parseDeprecatedService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceSettingsClassComposer.instance().generate(context, protoService);

    Assert.assertGoldenClass(this.getClass(), clazz, "DeprecatedServiceSettings.golden");
    Assert.assertGoldenSamples(
        clazz.samples(), clazz.classDefinition().packageString(), goldenSampleDir);
  }
}
