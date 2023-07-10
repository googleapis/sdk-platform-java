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
import com.google.api.generator.gapic.model.GapicPackageInfo;
import com.google.api.generator.gapic.model.Message;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;

public class ClientLibraryReflectConfigComposer {

  public static List<ReflectConfig> generateReflectConfig(
      GapicContext context, GapicPackageInfo packageInfo) {
    List<String> list = new ArrayList<>();
    context
        .messages()
        .forEach(
            (String id, Message message) -> {
              // if (id.startsWith(packageInfo.packageInfo().pakkage())) {
              list.add(id);
              if (!message.isEnum()) {
                list.add(id + "$Builder");
              }
              // }
            });
    return list.stream().sorted().map(ReflectConfig::new).collect(ImmutableList.toImmutableList());
  }

  public static class ReflectConfig {
    String name;
    boolean queryAllDeclaredConstructors = true;
    boolean queryAllPublicConstructors = true;
    boolean queryAllDeclaredMethods = true;
    boolean queryAllPublicMethods = true;
    boolean allDeclaredClasses = true;
    boolean allPublicClasses = true;

    ReflectConfig(String name) {
      this.name = name;
    }
  }
}
