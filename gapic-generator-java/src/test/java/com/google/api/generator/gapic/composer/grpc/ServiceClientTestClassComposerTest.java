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
import com.google.api.generator.test.protoloader.GrpcTestProtoLoader;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ServiceClientTestClassComposerTest {
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {"EchoClientTest", GrpcTestProtoLoader.instance().parseShowcaseEcho(), 0},
          {
            "DeprecatedServiceClientTest",
            GrpcTestProtoLoader.instance().parseDeprecatedService(),
            0
          },
          {"TestingClientTest", GrpcTestProtoLoader.instance().parseShowcaseTesting(), 0},
          {"SubscriberClientTest", GrpcTestProtoLoader.instance().parsePubSubPublisher(), 1},
          {"LoggingClientTest", GrpcTestProtoLoader.instance().parseLogging(), 0},
        });
  }

  @Parameterized.Parameter public String name;

  @Parameterized.Parameter(1)
  public GapicContext context;

  @Parameterized.Parameter(2)
  public int serviceIndex;

  @Test
  public void generateServiceClientTestClasses() {
    Service echoProtoService = context.services().get(serviceIndex);
    GapicClass clazz =
        ServiceClientTestClassComposer.instance().generate(context, echoProtoService);

    Assert.assertGoldenClass(this.getClass(), clazz, name + ".golden");
    Assert.assertEmptySamples(clazz.samples());
  }
}
