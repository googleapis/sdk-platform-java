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

package com.google.api.generator.gapic.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;

public class ServiceTest {
  private static final String SHOWCASE_PACKAGE_NAME = "com.google.showcase.v1beta1";
  private static final Service.Builder testServiceBuilder =
      Service.builder()
          .setName("Echo")
          .setDefaultHost("localhost:7469")
          .setOauthScopes(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"))
          .setPakkage(SHOWCASE_PACKAGE_NAME)
          .setProtoPakkage(SHOWCASE_PACKAGE_NAME)
          .setOriginalJavaPackage(SHOWCASE_PACKAGE_NAME)
          .setOverriddenName("Echo");

  @Test
  public void apiShortName_shouldReturnApiShortNameIfHostContainsRegionalEndpoint() {
    String defaultHost = "us-east1-pubsub.googleapis.com";
    Service testService = testServiceBuilder.setDefaultHost(defaultHost).build();
    assertEquals("pubsub", testService.apiShortName());
  }

  @Test
  public void apiShortName_shouldReturnApiShortName() {
    String defaultHost = "logging.googleapis.com";
    Service testService = testServiceBuilder.setDefaultHost(defaultHost).build();
    assertEquals("logging", testService.apiShortName());
  }

  @Test
  public void apiShortName_shouldReturnApiShortNameForIam() {
    String defaultHost = "iam-meta-api.googleapis.com";
    Service testService = testServiceBuilder.setDefaultHost(defaultHost).build();
    assertEquals("iam", testService.apiShortName());
  }

  @Test
  public void apiShortName_shouldReturnHostIfNoPeriods() {
    String defaultHost = "logging:7469";
    Service testService = testServiceBuilder.setDefaultHost(defaultHost).build();
    assertEquals("logging:7469", testService.apiShortName());
  }

  @Test
  public void apiVersion_shouldReturnVersionIfMatch() {
    String protoPackage = "com.google.showcase.v1";
    Service testService = testServiceBuilder.setProtoPakkage(protoPackage).build();
    assertEquals("v1", testService.apiVersion());
  }

  @Test
  public void apiVersion_shouldReturnEmptyIfNoMatch() {
    String protoPackage = "com.google.showcase";
    Service testService = testServiceBuilder.setProtoPakkage(protoPackage).build();
    assertEquals("", testService.apiVersion());
  }
}
