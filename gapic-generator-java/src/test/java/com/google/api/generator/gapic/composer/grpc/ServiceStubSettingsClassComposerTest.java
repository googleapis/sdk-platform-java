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
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ServiceStubSettingsClassComposerTest {
  static Stream<Arguments> data() {
    return Stream.of(
        Arguments.of(
            "LoggingServiceV2StubSettings",
            GrpcTestProtoLoader.instance().parseLogging(),
            "logging",
            "v2"),
        Arguments.of(
            "PublisherStubSettings",
            GrpcTestProtoLoader.instance().parsePubSubPublisher(),
            "pubsub",
            "v1"),
        Arguments.of(
            "EchoStubSettings",
            GrpcTestProtoLoader.instance().parseShowcaseEcho(),
            "localhost:7469",
            "v1beta1"),
        Arguments.of(
            "DeprecatedServiceStubSettings",
            GrpcTestProtoLoader.instance().parseDeprecatedService(),
            "localhost:7469",
            "v1"),
        Arguments.of(
            "ApiVersionTestingStubSettings",
            GrpcTestProtoLoader.instance().parseApiVersionTesting(),
            "localhost:7469",
            "v1"));
  }

  @ParameterizedTest
  @MethodSource("data")
  void generateServiceStubSettingsClasses(
      String name,
      GapicContext context,
      String apiShortNameExpected,
      String packageVersionExpected) {
    Service service = context.services().get(0);
    GapicClass clazz = ServiceStubSettingsClassComposer.instance().generate(context, service);

    Assert.assertGoldenClass(this.getClass(), clazz, name + ".golden");
    Assert.assertGoldenSamples(
        this.getClass(),
        "servicesettings/stub",
        clazz.classDefinition().packageString(),
        clazz.samples());
    Assert.assertCodeEquals(clazz.apiShortName(), apiShortNameExpected);
    Assert.assertCodeEquals(clazz.packageVersion(), packageVersionExpected);
  }
}
