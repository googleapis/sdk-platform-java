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

package com.google.api.generator.gapic.model;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class MethodTest {
  @Test
  public void toStream() {
    // Argument order: isClientStreaming, isServerStreaming.
    assertThat(Method.toStream(false, false)).isEqualTo(Method.Stream.NONE);
    assertThat(Method.toStream(true, false)).isEqualTo(Method.Stream.CLIENT);
    assertThat(Method.toStream(false, true)).isEqualTo(Method.Stream.SERVER);
    assertThat(Method.toStream(true, true)).isEqualTo(Method.Stream.BIDI);
  }
}
