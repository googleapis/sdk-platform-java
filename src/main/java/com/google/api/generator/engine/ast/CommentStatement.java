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

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;

@AutoValue
public abstract class CommentStatement implements Statement {

  public abstract ImmutableList<LineComment> lineComments();

  @Nullable
  public abstract JavaDocComment javaDocComment();

  @Nullable
  public abstract BlockComment blockComment();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static Builder builder() {
    return new AutoValue_CommentStatement.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract ImmutableList.Builder<LineComment> lineCommentsBuilder();
    // Comment statement can be purely JavaDocComment, BlockComment or LineComment.
    // LineComments can be multiple, while there should be only one JavaDocComment and BlockComment.
    // The order of the comments should be LineComments -> JavaComment -> BlockComment
    // Optional.
    public abstract Builder setJavaDocComment(JavaDocComment javaDocComment);
    // Optional.
    public abstract Builder setBlockComment(BlockComment blockComment);
    // Optional.
    public Builder addLineComment(LineComment lineComment) {
      lineCommentsBuilder().add(lineComment);
      return this;
    }

    public abstract CommentStatement build();
  }
}
