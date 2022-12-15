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

import com.google.api.generator.engine.ast.CommentStatement;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * This model represents a generated code sample that is configured per
 * snippet_config_language.proto. It contains the information needed to generate a configured sample
 * file.
 */
@AutoValue
public abstract class ConfiguredSnippet {

  public abstract List<CommentStatement> fileHeader();

  public abstract RegionTag regionTag();

  public abstract String name();

  public static Builder builder() {
    return new AutoValue_ConfiguredSnippet.Builder().setFileHeader(ImmutableList.of());
  }

  public abstract Builder toBuilder();

  /**
   * Helper method to easily update Sample's license header.
   *
   * @param header List of {@link CommentStatement} to replace Sample's header
   * @return Sample with updated header
   */
  public final ConfiguredSnippet withHeader(List<CommentStatement> header) {
    return toBuilder().setFileHeader(header).build();
  }

  /**
   * Helper method to easily update Sample's region tag.
   *
   * @param regionTag {@link RegionTag} to replace Sample's header
   * @return Sample with updated region tag.
   */
  public final ConfiguredSnippet withRegionTag(RegionTag regionTag) {
    return toBuilder()
        .setName(generateConfiguredSnippetClassName(regionTag()))
        .setRegionTag(regionTag)
        .build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setFileHeader(List<CommentStatement> header);

    public abstract Builder setRegionTag(RegionTag regionTag);

    abstract Builder setName(String name);

    abstract ConfiguredSnippet autoBuild();

    abstract RegionTag regionTag();

    public final ConfiguredSnippet build() {
      setName(generateConfiguredSnippetClassName(regionTag()));
      return autoBuild();
    }
  }

  // ConfiguredSample name will be rpcName + configId + Sync/Async (TODO: confirm this with Amanda)
  private static String generateConfiguredSnippetClassName(RegionTag regionTag) {
    return (regionTag.isAsynchronous() ? "Async" : "Sync")
        + regionTag.rpcName()
        + regionTag.overloadDisambiguation();
  }
}
