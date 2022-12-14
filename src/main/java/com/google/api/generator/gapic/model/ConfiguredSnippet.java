package com.google.api.generator.gapic.model;

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.Statement;
import com.google.auto.value.AutoValue;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfigMetadata;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * This model represents a generated code sample that is configured per snippet_config_language.proto. It contains the information needed to generate a
 * configured sample file.
 */

@AutoValue
public abstract class ConfiguredSnippet {

        public abstract List<CommentStatement> fileHeader();

        public abstract RegionTag regionTag();

        public abstract String name();


        public static Builder builder() {
            return new AutoValue_ConfiguredSnippet.Builder()
                    .setFileHeader(ImmutableList.of());
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
