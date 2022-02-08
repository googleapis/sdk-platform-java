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

import static org.junit.Assert.assertEquals;

import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import org.junit.Test;

public class ServiceClientTestClassComposerTest {
  @Test
  public void generateClientTest_echoClient() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseEcho();
    Service echoProtoService = context.services().get(0);
    GapicClass clazz =
        ServiceClientTestClassComposer.instance().generate(context, echoProtoService);

    Assert.assertGoldenClass(this.getClass(), clazz, "EchoClientTest.golden");
    Assert.assertEmptySamples(clazz.samples());
  }

  @Test
  public void generateClientTest_deprecatedServiceClient() {
    GapicContext context = GrpcTestProtoLoader.instance().parseDeprecatedService();
    Service protoService = context.services().get(0);
    GapicClass clazz = ServiceClientTestClassComposer.instance().generate(context, protoService);

    Assert.assertGoldenClass(this.getClass(), clazz, "DeprecatedServiceClientTest.golden");
    Assert.assertEmptySamples(clazz.samples());
  }

  @Test
  public void generateClientTest_testingClientResnameWithOnePatternWithNonSlashSepNames() {
    GapicContext context = GrpcTestProtoLoader.instance().parseShowcaseTesting();
    Service testingProtoService = context.services().get(0);
    GapicClass clazz =
        ServiceClientTestClassComposer.instance().generate(context, testingProtoService);

    Assert.assertGoldenClass(this.getClass(), clazz, "TestingClientTest.golden");
    Assert.assertEmptySamples(clazz.samples());
  }

  @Test
  public void generateClientTest_pubSubPublisherClient() {
    GapicContext context = GrpcTestProtoLoader.instance().parsePubSubPublisher();
    Service subscriptionService = context.services().get(1);
    assertEquals("Subscriber", subscriptionService.name());
    GapicClass clazz =
        ServiceClientTestClassComposer.instance().generate(context, subscriptionService);

    Assert.assertGoldenClass(this.getClass(), clazz, "SubscriberClientTest.golden");
    Assert.assertEmptySamples(clazz.samples());
  }

  @Test
  public void generateClientTest_logging() {
    GapicContext context = GrpcTestProtoLoader.instance().parseLogging();
    Service loggingService = context.services().get(0);
    GapicClass clazz = ServiceClientTestClassComposer.instance().generate(context, loggingService);

    Assert.assertGoldenClass(this.getClass(), clazz, "LoggingClientTest.golden");
    Assert.assertEmptySamples(clazz.samples());
  }
}
