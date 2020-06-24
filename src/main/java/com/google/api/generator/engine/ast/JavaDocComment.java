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
import javax.annotation.Nullable;

@AutoValue
public abstract class JavaDocComment implements Comment {

  @Nullable
  public abstract String deprecated();

  @Nullable
  public abstract String throwsText();

  public abstract ImmutableList<String> comments();

  public static Builder builder() {
    return new AutoValue_JavaDocComment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    protected abstract ImmutableList.Builder<String> commentsBuilder();

    public abstract Builder setDeprecated(String deprecatedText);

    public abstract Builder setThrowsText(String throwsText);

    public Builder addComment(String comment) {
      commentsBuilder().add(String.format("%1$s" + comment + "%2$s", "* ", "\n"));
      return this;
    }

    public Builder addParam(String name, String description) {
      String parameter =
          String.format("%1$s" + name + "%2$s" + description + "%3$s", "* @param ", " ", "\n");
      commentsBuilder().add(parameter);
      return this;
    }

    public Builder addSampleCode(String sampleCode) {
      commentsBuilder().add("* <pre><code>\n");
      String[] sampleLines = sampleCode.split("\\r?\\n");
      for (int i = 0; i < sampleLines.length; i++) {
        sampleLines[i] = "* " + sampleLines[i];
      }
      commentsBuilder()
          .add(String.format(String.join("\n", sampleLines) + "%s", "\n* </code></pre>\n"));
      return this;
    }

    public Builder addParagraph(String paragraph) {
      commentsBuilder().add(String.format("%1$s" + paragraph + "%2$s", "* <p> ", "\n"));
      return this;
    }

    public Builder addOrderedList(List<String> oList) {
      commentsBuilder().add("* <ol>\n");
      for (int i = 0; i < oList.size(); i++) {
        oList.set(i, String.format("%1$s" + oList.get(i) + "%2$s", "* <li>", "\n"));
      }
      commentsBuilder().add(String.format(String.join("", oList) + "%s", "* </ol>\n"));
      return this;
    }

    public Builder addUnorderedList(List<String> uList) {
      commentsBuilder().add("* <ul>\n");
      for (int i = 0; i < uList.size(); i++) {
        uList.set(i, String.format("%1$s" + uList.get(i) + "%2$s", "* <li>", "\n"));
      }
      commentsBuilder().add(String.format(String.join("", uList) + "%s", "* </ul>\n"));
      return this;
    }

    public abstract JavaDocComment build();
  }

  @Override
  public String comment() {
    List<String> commentBody = comments().stream().collect(Collectors.toList());
    commentBody.add(0, "/**\n");
    commentBody.add(String.format("%1$s" + deprecated() + "%2$s", "* @deprecated ", "\n"));
    commentBody.add(String.format("%1$s" + throwsText() + "%2$s", "* @throws ", "\n"));
    commentBody.add("*/");
    return String.join("", commentBody);
  }

  public String accept(AstNodeVisitor visitor) throws Exception {
    return visitor.visit(this);
  }
}
