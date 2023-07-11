// Copyright 2023 Google LLC
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

import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Message;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;

public class ClientLibraryReflectConfigComposer {

  public static List<ReflectConfig> generateReflectConfig(GapicContext context) {
    List<String> allConfigs = new ArrayList<>();
    context.messages().forEach((id, msg) -> allConfigs.addAll(parseReflectConfig(id, msg)));
    return allConfigs.stream()
        .distinct()
        .sorted()
        .map(ReflectConfig::new)
        .collect(ImmutableList.toImmutableList());
  }

  /** List all classes in the message that should have a reflect-config entry */
  private static List<String> parseReflectConfig(String id, Message message) {
    final String name = formatNestedClasses(id);

    List<String> list = new ArrayList<>();
    list.add(name);
    for (String nestedEnum : message.nestedEnums()) {
      list.add(name + "$" + nestedEnum);
    }
    if (!message.isEnum()) {
      list.add(name + "$Builder");
    }
    return list;
  }

  /**
   * Replace '.' with '$' in fully qualified class names once the classes become nested. ex:
   * com.google.foo.Bar.Baz.Car becomes com.google.foo.Bar$Baz$Car
   */
  private static String formatNestedClasses(String name) {
    StringBuilder result = new StringBuilder();
    boolean isNested = false;
    for (String s : name.split("\\.")) {
      if (result.length() != 0) {
        result.append(isNested ? "$" : ".");
      }
      result.append(s);
      if (Character.isUpperCase(s.charAt(0))) {
        isNested = true;
      }
    }
    return result.toString();
  }

  public static class ReflectConfig {
    String name;
    boolean queryAllDeclaredConstructors = true;
    boolean queryAllPublicConstructors = true;
    boolean queryAllDeclaredMethods = true;
    boolean allPublicMethods = true;
    boolean allDeclaredClasses = true;
    boolean allPublicClasses = true;

    ReflectConfig(String name) {
      this.name = name;
    }
  }
}
