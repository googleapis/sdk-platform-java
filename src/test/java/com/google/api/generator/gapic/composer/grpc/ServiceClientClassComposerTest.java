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
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ServiceClientClassComposerTest {
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {"EchoClient", GrpcTestProtoLoader.instance().parseShowcaseEcho()},
          {"DeprecatedServiceClient", GrpcTestProtoLoader.instance().parseDeprecatedService()},
          {"IdentityClient", GrpcTestProtoLoader.instance().parseShowcaseIdentity()},
          {"BookshopClient", GrpcTestProtoLoader.instance().parseBookshopService()},
          {"MessagingClient", GrpcTestProtoLoader.instance().parseShowcaseMessaging()},
        });
  }

  @Parameterized.Parameter public String name;

  @Parameterized.Parameter(1)
  public GapicContext context;

  @Test
  public void generateServiceClientClasses() {
    Service service = context.services().get(0);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, service);

    Assert.assertGoldenClass(this.getClass(), clazz, name + ".golden");
    Assert.assertGoldenSamples(
        this.getClass(), name, clazz.classDefinition().packageString(), clazz.samples());
  }
}
