// Copyright 2021 Google LLC
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

import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicContext;

// TODO: remove after Pre-DIREGAPIC refactoring is fully merged
public class TestProtoLoaderUtil {
  public static GapicContext parseDeprecatedService() {
    return TestProtoLoader.instance().parseDeprecatedService();
  }

  public static GapicContext parseShowcaseEcho() {
    return TestProtoLoader.instance().parseShowcaseEcho();
  }

  public static GapicContext parseShowcaseIdentity() {
    return TestProtoLoader.instance().parseShowcaseIdentity();
  }

  public static GapicContext parseShowcaseTesting() {
    return TestProtoLoader.instance().parseShowcaseTesting();
  }

  public static GapicContext parsePubSubPublisher() {
    return TestProtoLoader.instance().parsePubSubPublisher();
  }

  public static GapicContext parseLogging() {
    return TestProtoLoader.instance().parseLogging();
  }
}
