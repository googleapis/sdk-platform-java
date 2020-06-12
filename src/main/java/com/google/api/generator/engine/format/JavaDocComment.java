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
package com.google.api.generator.engine.format;

import com.google.auto.value.AutoValue;
import java.util.Optional;

@AutoValue
public abstract class JavaDocComment implements Comment {
  public abstract String comment();

  public abstract Optional<String> deprecatedText();

  public abstract Optional<String> sampleCode();

  public abstract Optional<String> throwsText();

  public abstract Optional<ParamPair> params();

  public class ParamPair {
    String paramName;
    String paramDescription;
  }

  public static Builder builder() {
    return new AutoValue_JavaDocComment.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    abstract Builder setDeprecatedText(String deprecatedText);

    abstract Builder setComment(String comment);

    abstract Builder setParams(ParamPair paramPair);

    abstract Builder setSampleCode(String sampleCode);

    abstract Builder setThrowsText(String throwsText);

    abstract JavaDocComment build();
  }

  public String write() {
    return comment();
  }
}
