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

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.auto.value.AutoValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AutoValue
public abstract class GapicClass {
  // TODO(miraleung): Add enum for resource name classes.
  public enum Kind {
    MAIN,
    STUB,
    TEST,
    PROTO
  };

  public abstract Kind kind();

  public abstract ClassDefinition classDefinition();

  public abstract List<Sample> samples();

  public static GapicClass create(Kind kind, ClassDefinition classDefinition) {
    return builder().setKind(kind).setClassDefinition(classDefinition).build();
  }

  public static GapicClass create(
      Kind kind, ClassDefinition classDefinition, List<Sample> samples) {
    return builder().setKind(kind).setClassDefinition(classDefinition).setSamples(samples).build();
  }

  static Builder builder() {
    return new AutoValue_GapicClass.Builder().setSamples(Collections.emptyList());
  }

  abstract Builder toBuilder();

  public final GapicClass withSamples(List<Sample> samples) {
    return toBuilder().setSamples(cleanupDuplicateSampleNames(samples)).build();
  }

  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder setKind(Kind kind);

    abstract Builder setClassDefinition(ClassDefinition classDefinition);

    abstract Builder setSamples(List<Sample> samples);

    abstract GapicClass autoBuild();

    abstract List<Sample> samples();

    public final GapicClass build() {
      setSamples(cleanupDuplicateSampleNames(samples()));
      return autoBuild();
    }
  }

  private static List<Sample> cleanupDuplicateSampleNames(List<Sample> samples) {
    HashMap<String, List<Sample>> duplicateName = new HashMap<>();
    for (Sample sample : samples) {
      if (!duplicateName.containsKey(sample.name())) {
        duplicateName.put(sample.name(), new ArrayList());
      }
      duplicateName.get(sample.name()).add(sample);
    }

    List<Sample> cleanSamples = new ArrayList();
    for (Map.Entry<String, List<Sample>> entry : duplicateName.entrySet()) {
      List<Sample> distinctDups = entry.getValue().stream().distinct().collect(Collectors.toList());
      if (distinctDups.size() > 1) {
        int num = 1;
        for (Sample sample : distinctDups) {
          Sample sample1 =
              sample.withRegionTag(
                  sample
                      .regionTag()
                      .withOverloadDisambiguation(
                          sample.regionTag().overloadDisambiguation() + num));
          cleanSamples.add(sample1);
          num += 1;
        }
      } else {
        cleanSamples.add(distinctDups.get(0));
      }
    }
    return cleanSamples;
  }
}
