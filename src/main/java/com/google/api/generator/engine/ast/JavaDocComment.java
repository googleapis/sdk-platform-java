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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AutoValue
public abstract class JavaDocComment implements Comment {

  public abstract ImmutableList<String> comments();

  public abstract ImmutableList<String> params();

  public abstract String deprecated();

  // Private.
  abstract String throwType();

  // Private.
  abstract String throwDescription();

  public static Builder builder() {
    return new AutoValue_JavaDocComment.Builder().setDeprecated("").setThrows("", "");
  }

  @Override
  public String comment() {
    // TODO(xiaozhenliu): call comment escaper here.
    List<String> comment = comments().stream().collect(Collectors.toList());
    // @param, @throws and @deprecated should always get printed at the end.
    comment.addAll(params().stream().collect(Collectors.toList()));
    if (!throwType().isEmpty()) {
      comment.add(String.format("@throws %s %s", throwType(), throwDescription()));
    }
    if (!deprecated().isEmpty()) {
      comment.add(String.format("@deprecated %s", deprecated()));
    }
    return String.join("\n", comment);
  }

  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    // Private.
    abstract Builder setThrowType(String type);
    // Private.
    abstract Builder setThrowDescription(String type);
    // Private.
    abstract ImmutableList.Builder<String> commentsBuilder();
    // Private.
    abstract ImmutableList.Builder<String> paramsBuilder();

    public abstract Builder setDeprecated(String deprecatedText);

    public Builder addComment(String comment) {
      commentsBuilder().add(comment);
      return this;
    }

    public Builder addParam(String name, String description) {
      paramsBuilder().add(String.format("@param %s %s", name, description));
      return this;
    }

    public Builder addSampleCode(String sampleCode) {
      commentsBuilder().add("<pre><code>");
      String[] sampleLines = sampleCode.split("\\r?\\n");
      Arrays.stream(sampleLines)
          .forEach(
              line -> {
                commentsBuilder().add(line);
              });
      commentsBuilder().add("</code></pre>");
      return this;
    }

    public Builder addParagraph(String paragraph) {
      commentsBuilder().add(String.format("<p> %s", paragraph));
      return this;
    }

    public Builder addOrderedList(List<String> oList) {
      commentsBuilder().add("<ol>");
      oList.stream()
          .forEach(
              s -> {
                commentsBuilder().add(String.format("<li> %s", s));
              });
      commentsBuilder().add("</ol>");
      return this;
    }

    public Builder addUnorderedList(List<String> uList) {
      commentsBuilder().add("<ul>");
      uList.stream()
          .forEach(
              s -> {
                commentsBuilder().add(String.format("<li> %s", s));
              });
      commentsBuilder().add("</ul>");
      return this;
    }

    public Builder setThrows(String type, String description) {
      setThrowType(type);
      setThrowDescription(description);
      return this;
    }

    public abstract JavaDocComment build();
  }
}
