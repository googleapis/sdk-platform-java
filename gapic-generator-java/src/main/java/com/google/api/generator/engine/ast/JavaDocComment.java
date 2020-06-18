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
import com.google.googlejavaformat.java.Formatter;
import java.util.Optional;

@AutoValue
public abstract class JavaDocComment implements Comment {
  public abstract ImmutableList<String> comments();

  public abstract Optional<String> deprecated();

  public abstract Optional<String> sampleCode();

  public abstract Optional<String> throwsText();

  public abstract ImmutableList<ParamPair> params();

  public static class ParamPair {
    String paramName;
    String paramDescription;

    public ParamPair(String paramName, String paramDescription) {
      this.paramName = paramName;
      this.paramDescription = paramDescription;
    }
  }

  public static Builder builder() {
    return new AutoValue_JavaDocComment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    abstract Builder setDeprecated(String deprecatedText);

    abstract Builder setSampleCode(String sampleCode);

    abstract Builder setThrowsText(String throwsText);

    protected abstract ImmutableList.Builder<String> commentsBuilder();

    protected abstract ImmutableList.Builder<ParamPair> paramsBuilder();

    public Builder addComment(String comment) {
      commentsBuilder().add(comment);
      return this;
    }

    public Builder addParam(ParamPair paramPair) {
      paramsBuilder().add(paramPair);
      return this;
    }

    abstract JavaDocComment build();
  }

  public String write() {
    StringBuilder formattedComment = new StringBuilder("/**\n");
    ImmutableList<String> commentList = comments();
    ImmutableList<ParamPair> paramList = params();
    for (String comment : commentList) {
      formattedComment.append("* " + comment + "\n");
    }
    for (ParamPair p : paramList) {
      formattedComment.append("* @param " + p.paramName + " " + p.paramDescription + "\n");
    }
    if (sampleCode().isPresent()) {
      formattedComment.append("* Sample code:\n* <pre><code>\n");
      String sampleCode = sampleCode().get();
      String[] sampleLines = sampleCode.split("\\r?\\n");
      for (int i = 0; i < sampleLines.length; i++) {
        sampleLines[i] = "* " + sampleLines[i];
      }
      formattedComment.append(String.join("\n", sampleLines) + "\n* </code></pre>\n");
    }
    if (deprecated().isPresent()) {
      formattedComment.append("* @deprecated " + deprecated() + "\n");
    }
    if (throwsText().isPresent()) {
      formattedComment.append("* @throws " + throwsText() + "\n");
    }
    String formattedSource = "";
    try {
      formattedSource = new Formatter().formatSource(formattedComment.append("*/").toString());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return formattedSource;
  }
}
