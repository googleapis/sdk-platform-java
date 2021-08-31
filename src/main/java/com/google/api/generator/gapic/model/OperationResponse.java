/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.api.generator.gapic.model;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

@AutoValue
public abstract class OperationResponse {
  @Nullable
  public abstract String getNameFieldName();

  @Nullable
  public abstract String getStatusFieldName();

  @Nullable
  public abstract String getErrorCodeFieldName();

  @Nullable
  public abstract String getErrorMessageFieldName();

  public static Builder builder() {
    return new AutoValue_OperationResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setNameFieldName(String nameFieldName);

    public abstract Builder setStatusFieldName(String val);

    public abstract Builder setErrorCodeFieldName(String val);

    public abstract Builder setErrorMessageFieldName(String val);

    public abstract OperationResponse build();
  }
}
