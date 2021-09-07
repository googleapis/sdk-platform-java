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
public abstract class HttpBindings {
  public enum HttpVerb {
    GET,
    PUT,
    POST,
    DELETE,
    PATCH,
  }

  @AutoValue
  public abstract static class HttpBinding implements Comparable<HttpBinding> {
    public abstract String name();

    public abstract boolean isOptional();

    public static HttpBinding create(String name, boolean isOptional) {
      return new AutoValue_HttpBindings_HttpBinding(name, isOptional);
    }

    // Do not forget to keep it in sync with equals() implementation.
    @Override
    public int compareTo(HttpBinding o) {
      int res = name().compareTo(o.name());
      return res == 0 ? Boolean.compare(isOptional(), o.isOptional()) : res;
    }
  }

  public abstract HttpVerb httpVerb();

  public abstract String pattern();

  public abstract Set<HttpBinding> pathParameters();

  public abstract Set<HttpBinding> queryParameters();

  public abstract Set<HttpBinding> bodyParameters();

  public abstract boolean isAsteriskBody();

  public static HttpBindings.Builder builder() {
    return new AutoValue_HttpBindings.Builder()
        .setPathParameters(ImmutableSet.of())
        .setQueryParameters(ImmutableSet.of())
        .setBodyParameters(ImmutableSet.of());
  }

  // Protobuf fields and template patterns follow snake_case style. When translated into actual Java
  // class fields and URL respectively, those must be converted to lowerCamelCase.
  // For example:
  //   in .proto file: "/global/instanceTemplates/{instance_template=*}"
  //   in .java file:  "/global/instanceTemplates/{instanceTemplate=*}"
  public String patternLowerCamel() {
    String lowerCamelPattern = pattern();
    for (HttpBinding pathParam : pathParameters()) {
      lowerCamelPattern =
          lowerCamelPattern.replaceAll(
              "\\{" + pathParam.name(), "{" + JavaStyle.toLowerCamelCase(pathParam.name()));
    }
    return lowerCamelPattern;
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract HttpBindings.Builder setHttpVerb(HttpVerb httpVerb);

    public abstract HttpBindings.Builder setPattern(String pattern);

    abstract String pattern();

    public abstract HttpBindings.Builder setPathParameters(Set<HttpBinding> pathParameters);

    public abstract HttpBindings.Builder setQueryParameters(Set<HttpBinding> queryParameters);

    public abstract HttpBindings.Builder setBodyParameters(Set<HttpBinding> bodyParameters);

    public abstract HttpBindings.Builder setIsAsteriskBody(boolean asteriskBody);

    public abstract HttpBindings autoBuild();

    public HttpBindings build() {
      if ("".equals(pattern())) {
        throw new IllegalArgumentException("pattern cannot be empty");
      }
      return autoBuild();
    }
  }
}
