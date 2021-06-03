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

package com.google.api.generator.gapic.model;

import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;
import java.util.Set;

@AutoValue
public abstract class HttpRuleBindings {
  public abstract String httpVerb();

  public abstract String pattern();

  public abstract Set<String> pathParameters();

  public abstract Set<String> queryParameters();

  public abstract Set<String> bodyParameters();

  public static HttpRuleBindings.Builder builder() {
    return new AutoValue_HttpRuleBindings.Builder()
        .setHttpVerb("")
        .setPattern("")
        .setPathParameters(ImmutableSet.of())
        .setQueryParameters(ImmutableSet.of())
        .setBodyParameters(ImmutableSet.of());
  }

  public boolean isEmpty() {
    return pathParameters().isEmpty();
  }

  // Protobuf fields and template patterns follow snake_case style. When translated into actual Java
  // class fields and URL respectively, those must be converted to lowerCamelCase.
  // For example:
  //   in .proto file: "/global/instanceTemplates/{instance_template=*}"
  //   in .java file:  "/global/instanceTemplates/{instanceTemplate=*}"
  public String patternLowerCamel() {
    String lowerCamelPattern = pattern();
    for (String pathParam : pathParameters()) {
      lowerCamelPattern =
          lowerCamelPattern.replaceAll(
              "\\{" + pathParam, "{" + JavaStyle.toLowerCamelCase(pathParam));
    }
    return lowerCamelPattern;
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract HttpRuleBindings.Builder setHttpVerb(String httpVerb);

    public abstract HttpRuleBindings.Builder setPattern(String pattern);

    public abstract HttpRuleBindings.Builder setPathParameters(Set<String> pathParameters);

    public abstract HttpRuleBindings.Builder setQueryParameters(Set<String> queryParameters);

    public abstract HttpRuleBindings.Builder setBodyParameters(Set<String> bodyParameters);

    public abstract HttpRuleBindings build();
  }
}
