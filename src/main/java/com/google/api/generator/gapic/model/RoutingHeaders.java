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

import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
public abstract class RoutingHeaders {

  public abstract List<RoutingHeader> routingHeadersSet();

  // TODO: change to a builder and expose only add method
  public static RoutingHeaders create(List<RoutingHeader> routingHeaderList) {
    return new AutoValue_RoutingHeaders(routingHeaderList);
  }

  @AutoValue
  public abstract static class RoutingHeader {

    public abstract String field();

    public abstract String name();

    public abstract String pattern();

    public static RoutingHeaders.RoutingHeader create(String field, String name, String pattern) {
      return new AutoValue_RoutingHeaders_RoutingHeader(field, name, pattern);
    }
  }
}
