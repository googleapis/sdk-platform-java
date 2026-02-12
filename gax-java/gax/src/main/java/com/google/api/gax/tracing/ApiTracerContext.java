/*
 * Copyright 2026 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.api.gax.tracing;

import com.google.api.core.InternalApi;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/**
 * A context object that contains information used to infer attributes that are common for all
 * {@link ApiTracer}s.
 *
 * <p>For internal use only.
 */
@InternalApi
@AutoValue
public abstract class ApiTracerContext {
  private static final Logger LOGGER = Logger.getLogger(ApiTracerContext.class.getName());
  private static final String GAPIC_PROPERTIES_FILE = "/gapic.properties";
  private static final String REPO_KEY = "repo";

  @Nullable
  public abstract String getServerAddress();

  @Nullable
  public abstract String getRepo();

  public static Builder newBuilder() {
    return newBuilder(ApiTracerContext.class.getResourceAsStream(GAPIC_PROPERTIES_FILE));
  }

  @VisibleForTesting
  static Builder newBuilder(@Nullable InputStream inputStream) {
    Builder builder = new AutoValue_ApiTracerContext.Builder();
    loadRepoFromProperties(builder, inputStream);
    return builder;
  }

  private static void loadRepoFromProperties(Builder builder, @Nullable InputStream is) {
    if (is == null) {
      return;
    }
    try {
      Properties properties = new Properties();
      properties.load(is);
      builder.setRepo(properties.getProperty(REPO_KEY));
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Could not load gapic.properties", e);
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        LOGGER.log(Level.WARNING, "Could not close gapic.properties stream", e);
      }
    }
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setServerAddress(String serverAddress);

    public abstract Builder setRepo(String repo);

    public abstract ApiTracerContext build();
  }
}
