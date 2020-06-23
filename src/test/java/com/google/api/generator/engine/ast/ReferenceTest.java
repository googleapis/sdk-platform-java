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

package com.google.api.generator.engine.ast;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class ReferenceTest {
  @Test
  public void basicReference() {
    Reference reference = Reference.builder().setClazz(Integer.class).build();
    assertThat(reference.name()).isEqualTo(Integer.class.getSimpleName());
  }

  @Test
  public void parameterizedReference() {
    Reference reference =
        Reference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    Reference.withClazz(String.class), Reference.withClazz(Integer.class)))
            .build();
    assertThat(reference.name()).isEqualTo("HashMap<String, Integer>");
  }

  @Test
  public void nestedParameterizedReference() {
    Reference mapReference =
        Reference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    Reference.withClazz(String.class), Reference.withClazz(Integer.class)))
            .build();
    Reference outerMapReference =
        Reference.builder()
            .setClazz(HashMap.class)
            .setGenerics(Arrays.asList(mapReference, mapReference))
            .build();
    Reference listReference =
        Reference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(outerMapReference))
            .build();
    assertThat(listReference.name())
        .isEqualTo("List<HashMap<HashMap<String, Integer>, HashMap<String, Integer>>>");
  }
}
