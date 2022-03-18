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
import java.util.Collections;
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
    return toBuilder().setSamples(samples).build();
  }

  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder setKind(Kind kind);

    abstract Builder setClassDefinition(ClassDefinition classDefinition);

    abstract Builder setSamples(List<Sample> samples);

    abstract GapicClass autoBuild();

    abstract List<Sample> samples();

    public final GapicClass build() {
      setSamples(handleDuplicateSamples(samples()));
      return autoBuild();
    }
  }

  private static List<Sample> handleDuplicateSamples(List<Sample> samples) {
    //  filter out any duplicate samples and group by sample name
    Map<String, List<Sample>> distinctSamplesGroupedByName =
        samples.stream().distinct().collect(Collectors.groupingBy(s -> s.name()));

    // grab unique samples
    List<Sample> uniqueSamples =
        distinctSamplesGroupedByName.entrySet().stream()
            .filter(entry -> entry.getValue().size() < 2)
            .map(entry -> entry.getValue().get(0))
            .collect(Collectors.toList());

    if (uniqueSamples.size() == distinctSamplesGroupedByName.size()) {
      return uniqueSamples;
    }

    // grab distinct samples with same name - similar version of same sample
    List<Map.Entry<String, List<Sample>>> duplicateDistinctSamples =
        distinctSamplesGroupedByName.entrySet().stream()
            .filter(entry -> entry.getValue().size() > 1)
            .collect(Collectors.toList());

    // update similar samples regionTag/name so filesname/regiontag are unique
    for (Map.Entry<String, List<Sample>> entry : duplicateDistinctSamples) {
      int sampleNum = 0;
      for (Sample sample : entry.getValue()) {
        //  first sample will be canonical, not updating disambiguation
        Sample uniqueSample = sample;
        if (sampleNum != 0) {
          uniqueSample =
              sample.withRegionTag(
                  sample
                      .regionTag()
                      .withOverloadDisambiguation(
                          sample.regionTag().overloadDisambiguation() + sampleNum));
        }
        uniqueSamples.add(uniqueSample);
        sampleNum++;
      }
    }
    return uniqueSamples;
  }
}
