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

package com.google.api.generator.engine.ast;

import com.google.api.generator.engine.escaper.CommentEscaper;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class MultiLineComment implements Comment {
  // Private accessor.
  abstract ImmutableList<String> comments();

  @Override
  public String comment() {
    return String.join("\n", comments());
  }

  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static final MultiLineComment APACHE_LICENSE =
      MultiLineComment.builder()
          .addComment("Copyright 2020 Google LLC")
          .addComment("")
          .addComment("Licensed under the Apache License, Version 2.0 (the \"License\");")
          .addComment("you may not use this file except in compliance with the License.")
          .addComment("You may obtain a copy of the License at")
          .addComment("")
          .addComment("    https://www.apache.org/licenses/LICENSE-2.0")
          .addComment("")
          .addComment("Unless required by applicable law or agreed to in writing, software")
          .addComment("distributed under the License is distributed on an \"AS IS\" BASIS,")
          .addComment("WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.")
          .addComment("See the License for the specific language governing permissions and")
          .addComment("limitations under the License.")
          .build();

  public static Builder builder() {
    return new AutoValue_MultiLineComment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    // Private accessor.
    abstract ImmutableList.Builder<String> commentsBuilder();

    public Builder addComment(String comment) {
      commentsBuilder().add(CommentEscaper.escape(comment));
      return this;
    }

    public abstract MultiLineComment build();
  }
}
