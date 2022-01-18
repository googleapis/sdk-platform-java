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

import static com.google.common.truth.Truth.assertThat;

import com.google.api.generator.gapic.model.RoutingHeaders.RoutingHeader;
import java.util.List;
import org.junit.Test;

public class RoutingHeadersTest {

  @Test
  public void getDescendantFieldNames_shouldSplitFieldNameByDot() {
    RoutingHeader routingHeader = RoutingHeader.create("table.name", "name", "/abc/dec");
    List<String> descendantFieldNames = routingHeader.getDescendantFieldNames();
    assertThat(descendantFieldNames).containsExactly("table", "name");
  }
}
