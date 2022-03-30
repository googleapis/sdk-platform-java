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

package com.google.api.generator.gapic.composer;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.gapic.composer.comment.CommentComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Transport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpringComposer {
  public static List<GapicClass> composeServiceAutoConfigClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.addAll(generateClientAutoConfig(context));
    return addApacheLicense(clazzes);
  }

  public static List<GapicClass> generateClientAutoConfig(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    context
        .services()
        .forEach(
            s -> {
              if (context.transport() == Transport.GRPC) {
                clazzes.add(SpringAutoConfigClassComposer.instance().generate(context, s));
              }
            });
    return clazzes;
  }

  protected static List<GapicClass> addApacheLicense(List<GapicClass> gapicClassList) {
    return gapicClassList.stream()
        .map(
            gapicClass -> {
              ClassDefinition classWithHeader =
                  gapicClass
                      .classDefinition()
                      .toBuilder()
                      .setFileHeader(CommentComposer.APACHE_LICENSE_COMMENT)
                      .build();
              return GapicClass.create(gapicClass.kind(), classWithHeader);
            })
        .collect(Collectors.toList());
  }
}
