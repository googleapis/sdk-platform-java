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
import java.util.List;
import java.util.stream.Collectors;

@AutoValue
public abstract class JavaDocComment implements Comment {

  public abstract ImmutableList<String> comments();

  public static Builder builder() {
    return new AutoValue_JavaDocComment.Builder();
  }

  public static JavaDocComment withComment(String comment) {
    return JavaDocComment.builder().addComment(comment).build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    protected abstract ImmutableList.Builder<String> commentsBuilder();

    public Builder setDeprecated(String deprecatedText) {
      commentsBuilder().add(String.format("%s %s", "@deprecated", deprecatedText));
      return this;
    }

    public Builder addComment(String comment) {
      commentsBuilder().add(comment);
      return this;
    }

    public Builder addParam(String name, String description) {
      String parameter = String.format("%s %s %s", "@param", name, description);
      commentsBuilder().add(parameter);
      return this;
    }

    public Builder setThrows(String type, String description) {
      String throwsText = String.format("%s %s %s", "@throws", type, description);
      commentsBuilder().add(throwsText);
      return this;
    }

    public Builder addSampleCode(String sampleCode) {
      commentsBuilder().add("<pre><code>");
      String[] sampleLines = sampleCode.split("\\r?\\n");
      for (String sampleLine : sampleLines) {
        commentsBuilder().add(sampleLine);
      }
      commentsBuilder().add("</code></pre>");
      return this;
    }

    public Builder addParagraph(String paragraph) {
      commentsBuilder().add(String.format("%s %s", "<p>", paragraph));
      return this;
    }

    public Builder addOrderedList(List<String> oList) {
      commentsBuilder().add("<ol>");
      for (int i = 0; i < oList.size(); i++) {
        commentsBuilder().add(String.format("%s %s", "<li>", oList.get(i)));
      }
      commentsBuilder().add("</ol>");
      return this;
    }

    public Builder addUnorderedList(List<String> uList) {
      commentsBuilder().add("<ul>");
      for (int i = 0; i < uList.size(); i++) {
        commentsBuilder().add(String.format("%s %s", "<li>", uList.get(i)));
      }
      commentsBuilder().add("</ul>");
      return this;
    }

    public abstract JavaDocComment build();
  }

  @Override
  public String comment() {
    List<String> commentBody = comments().stream().collect(Collectors.toList());
    return String.join("\n", commentBody);
  }

  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }
}
