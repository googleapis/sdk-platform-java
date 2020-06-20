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
import java.util.ArrayList;
import java.util.List;
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
      commentsBuilder().add(("* " + comment + "\n"));
      return this;
    }

    public Builder addParam(String name, String description) {
      String parameter = "* @param " + name + " " + description + "\n";
      commentsBuilder().add(parameter);
      return this;
    }

    public Builder addSampleCode(String sampleCode) {
      commentsBuilder().add("* Sample code:\n* <pre><code>\n");
      String[] sampleLines = sampleCode.split("\\r?\\n");
      for (int i = 0; i < sampleLines.length; i++) {
        sampleLines[i] = "* " + sampleLines[i];
      }
      commentsBuilder().add(String.join("\n", sampleLines) + "\n* </code></pre>\n");
      return this;
    }

    public Builder addHtmlP(String paragraph) {
      commentsBuilder().add("* <p> " + paragraph + "\n");
      return this;
    }

    public Builder addHtmlOl(List<String> oList) {
      commentsBuilder().add("* <ol>\n");
      for (int i = 0; i < oList.size(); i++) {
        oList.set(i, "* <li>" + oList.get(i) + "\n");
      }
      commentsBuilder().add(String.join("", oList) + "* </ol>\n");
      return this;
    }

    public Builder addHtmlUl(List<String> uList) {
      commentsBuilder().add("* <ul>\n");
      for (int i = 0; i < uList.size(); i++) {
        uList.set(i, "* <li>" + uList.get(i) + "\n");
      }
      commentsBuilder().add(String.join("", uList) + "* </ul>\n");
      return this;
    }

    public abstract JavaDocComment build();
  }

  public String comment() {
    List<String> commentBody = new ArrayList<>(comments());
    commentBody.add(0, "/**\n");
    commentBody.add("* @deprecated " + deprecated() + "\n");
    commentBody.add("* @throws " + throwsText() + "\n");
    commentBody.add("*/");
    return String.join("", commentBody);
  }

  public String accept(AstNodeVisitor visitor) throws Exception {
    return visitor.visit(this);
  }
}
