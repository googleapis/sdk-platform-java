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

import com.google.api.generator.engine.escaper.HtmlEscaper;
import com.google.api.generator.engine.escaper.MetacharEscaper;
import com.google.auto.value.AutoValue;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AutoValue
public abstract class JavaDocComment implements Comment {
  @Override
  public abstract String comment();

  // Convenience helper for simple comments.
  public static JavaDocComment withComment(String comment) {
    return builder().addComment(comment).build();
  }

  public static Builder builder() {
    return new AutoValue_JavaDocComment.Builder();
  }

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    // The lack of a getter for these local variables in the external class is WAI.
    String throwsType = null;
    String throwsDescription = null;
    String deprecated = null;
    List<String> paramsList = new ArrayList<>();
    List<String> componentsList = new ArrayList<>();
    // Private accessor, set complete and consolidated comment.
    abstract Builder setComment(String comment);

    abstract JavaDocComment autoBuild();

    public Builder setThrows(String type, String description) {
      throwsType = type;
      throwsDescription = description;
      return this;
    }

    public Builder setDeprecated(String deprecatedText) {
      deprecated = deprecatedText;
      return this;
    }

    public Builder addParam(String name, String description) {
      paramsList.add(String.format("@param %s %s", name, description));
      return this;
    }

    public Builder addUnescapedComment(String comment) {
      componentsList.add(comment);
      return this;
    }

    public Builder addComment(String comment) {
      componentsList.add(HtmlEscaper.escaper(comment));
      return this;
    }

    public Builder addSampleCode(String sampleCode) {
      componentsList.add("<pre><code>");
      Arrays.stream(sampleCode.split("\\r?\\n"))
          .forEach(
              line -> {
                componentsList.add(HtmlEscaper.escaper(line));
              });
      componentsList.add("</code></pre>");
      return this;
    }

    public Builder addParagraph(String paragraph) {
      componentsList.add(String.format("<p> %s", paragraph));
      return this;
    }

    public Builder addOrderedList(List<String> oList) {
      componentsList.add("<ol>");
      oList.stream()
          .forEach(
              s -> {
                componentsList.add(String.format("<li> %s", s));
              });
      componentsList.add("</ol>");
      return this;
    }

    public Builder addUnorderedList(List<String> uList) {
      componentsList.add("<ul>");
      uList.stream()
          .forEach(
              s -> {
                componentsList.add(String.format("<li> %s", s));
              });
      componentsList.add("</ul>");
      return this;
    }

    public JavaDocComment build() {
      // @param, @throws and @deprecated should always get printed at the end.
      componentsList.addAll(paramsList);
      if (!Strings.isNullOrEmpty(throwsType)) {
        componentsList.add(String.format("@throws %s %s", throwsType, throwsDescription));
      }
      if (!Strings.isNullOrEmpty(deprecated)) {
        componentsList.add(String.format("@deprecated %s", deprecated));
      }
      // Escape component in list one by one, because we will join the components by `\n`
      // `\n` will be taken as escape character by the comment escaper.
      componentsList =
          componentsList.stream().map(c -> MetacharEscaper.escaper(c)).collect(Collectors.toList());
      setComment(String.join("\n", componentsList));
      return autoBuild();
    }
  }
}
