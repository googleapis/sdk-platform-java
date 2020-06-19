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
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class JavaDocComment {
  public enum COMMENT_TYPE {
    COMMENT,
    HTML_P,
    HTML_UL,
    HTML_OL,
    SAMPLE_CODE,
  }

  public static List<COMMENT_TYPE> commentList = new ArrayList<>();;

  @Nullable
  public abstract String deprecated();

  @Nullable
  public abstract String throwsText();

  public abstract ImmutableMap<String, String> params();

  public abstract ImmutableList<String> sampleCode();

  public abstract ImmutableList<String> comments();

  public abstract ImmutableList<String> html_p();

  public abstract ImmutableList<List<String>> html_ol();

  public abstract ImmutableList<List<String>> html_ul();

  public static Builder builder() {
    return new AutoValue_JavaDocComment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setDeprecated(String deprecatedText);

    public abstract Builder setThrowsText(String throwsText);

    protected abstract ImmutableMap.Builder<String, String> paramsBuilder();

    protected abstract ImmutableList.Builder<String> sampleCodeBuilder();

    protected abstract ImmutableList.Builder<String> commentsBuilder();

    protected abstract ImmutableList.Builder<String> html_pBuilder();

    protected abstract ImmutableList.Builder<List<String>> html_olBuilder();

    protected abstract ImmutableList.Builder<List<String>> html_ulBuilder();

    public Builder addComment(String comment) {
      commentsBuilder().add(comment);
      commentList.add(COMMENT_TYPE.COMMENT);
      return this;
    }

    public Builder addParam(String name, String description) {
      paramsBuilder().put(name, description);
      return this;
    }

    public Builder addSampleCode(String sampleCode) {
      sampleCodeBuilder().add(sampleCode);
      commentList.add(COMMENT_TYPE.SAMPLE_CODE);
      return this;
    }

    public Builder addHtmlP(String paragraph) {
      html_pBuilder().add(paragraph);
      commentList.add(COMMENT_TYPE.HTML_P);
      return this;
    }

    public Builder addHtmlOl(List<String> oList) {
      html_olBuilder().add(oList);
      commentList.add(COMMENT_TYPE.HTML_OL);
      return this;
    }

    public Builder addHtmlUl(List<String> uList) {
      html_ulBuilder().add(uList);
      commentList.add(COMMENT_TYPE.HTML_UL);
      return this;
    }

    public abstract JavaDocComment build();
  }

  public String accept(AstNodeVisitor visitor) throws Exception {
    return visitor.visit(this);
  }
}
