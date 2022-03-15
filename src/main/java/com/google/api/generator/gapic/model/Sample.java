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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.Statement;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.List;

@AutoValue
public abstract class Sample {
  public abstract List<Statement> body();

  public abstract List<AssignmentExpr> variableAssignments();

  public abstract List<CommentStatement> fileHeader();

  public abstract RegionTag regionTag();

  public abstract String name();

  public static Builder builder() {
    return new AutoValue_Sample.Builder()
        .setBody(ImmutableList.of())
        .setVariableAssignments(ImmutableList.of())
        .setFileHeader(ImmutableList.of());
  }

  abstract Builder toBuilder();

  public final Sample withHeader(List<CommentStatement> header) {
    return toBuilder().setFileHeader(header).build();
  }

  public final Sample withRegionTag(RegionTag regionTag) {
    return toBuilder()
        .setName(generateSampleClassName(regionTag()))
        .setRegionTag(regionTag)
        .build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setBody(List<Statement> body);

    public abstract Builder setVariableAssignments(List<AssignmentExpr> variableAssignments);

    public abstract Builder setFileHeader(List<CommentStatement> header);

    public abstract Builder setRegionTag(RegionTag regionTag);

    abstract Builder setName(String name);

    abstract Sample autoBuild();

    abstract RegionTag regionTag();

    public final Sample build() {
      setName(generateSampleClassName(regionTag()));
      return autoBuild();
    }
  }

  private static String generateSampleClassName(RegionTag regionTag) {
    return (regionTag.isAsynchronous() ? "Async" : "Sync")
        + regionTag.rpcName()
        + regionTag.overloadDisambiguation();
  }
}
