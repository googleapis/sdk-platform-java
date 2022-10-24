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

package com.google.api.generator.gapic.composer.utils;

import org.junit.Assert;
import org.junit.Test;

public class ClassNamesTest {
  @Test
  public void getPureServiceName_returnsServiceName() {
    Assert.assertEquals(ClassNames.getPureServiceName("EchoClient"), "Echo");
    Assert.assertEquals(ClassNames.getPureServiceName("Echo"), "Echo");
    Assert.assertEquals(ClassNames.getPureServiceName(""), "");
    Assert.assertEquals(ClassNames.getPureServiceName("ClientEcho"), "");
    Assert.assertEquals(ClassNames.getPureServiceName("echoclient"), "echoclient");
    Assert.assertEquals(ClassNames.getPureServiceName("echoClient"), "echo");
    Assert.assertEquals(ClassNames.getPureServiceName("echoServiceClient"), "echoService");
  }
}
