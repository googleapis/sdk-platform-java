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

package com.google.api.generator.gapic.utils;

import com.google.common.base.CaseFormat;

public class JavaStyle {
  private static final String UNDERSCORE = "_";

  public static String toLowerCamelCase(String s) {
    if (s.indexOf(UNDERSCORE) >= 0) {
      s = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, s);
    }
    return String.format("%s%s", s.substring(0, 1).toLowerCase(), s.substring(1));
  }

  public static String toUpperCamelCase(String s) {
    if (s.indexOf(UNDERSCORE) >= 0) {
      s = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, s);
    }
    return String.format("%s%s", s.substring(0, 1).toUpperCase(), s.substring(1));
  }

  public static String toUpperSnakeCase(String s) {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, toUpperCamelCase(s));
  }
}
