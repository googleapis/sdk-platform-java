/*
 * Copyright 2024 Google LLC
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

package com.google.api.gax.logging;

import com.google.api.core.InternalApi;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@InternalApi
@AutoValue
public abstract class LogData {
  private static final Gson gson = new Gson();

  public abstract String serviceName();

  public abstract String rpcName();

  @Nullable
  public abstract String requestId();

  @Nullable
  public abstract String requestHeaders();

  @Nullable
  public abstract String requestPayload();

  @Nullable
  public abstract String responseStatus();

  @Nullable
  public abstract String responseHeaders();

  @Nullable
  public abstract JsonElement responsePayload();

  public static Builder builder() {
    return new AutoValue_LogData.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder serviceName(String serviceName);

    public abstract Builder rpcName(String rpcName);

    public abstract Builder requestId(String requestId);

    public abstract Builder requestHeaders(String requestHeaders);

    public abstract Builder requestPayload(String requestPayload);

    public abstract Builder responseStatus(String responseStatus);

    public abstract Builder responseHeaders(String responseHeaders);

    public abstract Builder responsePayload(JsonElement responsePayload);

    public abstract LogData build();
  }

  public Map<String, String> serviceAndRpcToMap() {
    Map<String, String> map = new HashMap<>();
    map.put("serviceName", serviceName());
    map.put("rpcName", rpcName());
    return map;
  }

  public Map<String, String> requestDetailsToMap() {
    Map<String, String> map = new HashMap<>();
    map.put("serviceName", serviceName());
    map.put("rpcName", rpcName());
    map.put("request.headers", requestHeaders());
    map.put("request.payload", requestPayload());
    return map;
  }

  public Map<String, String> responseInfoToMap() {
    Map<String, String> map = new HashMap<>();
    map.put("serviceName", serviceName());
    map.put("rpcName", rpcName());
    map.put("response.status", responseStatus());
    return map;
  }

  public Map<String, String> responseDetailsToMap() {
    Map<String, String> map = new HashMap<>();
    map.put("serviceName", serviceName());
    map.put("rpcName", rpcName());
    map.put("response.status", responseStatus());
    map.put("response.payload", gson.toJson(responsePayload()));
    map.put("response.headers", responseHeaders());
    return map;
  }

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("serviceName", serviceName());
    map.put("rpcName", rpcName());
    map.put("requestId", requestId());
    map.put("request.headers", requestHeaders());
    map.put("request.payload", requestPayload());
    map.put("response.status", responseStatus());
    map.put("response.headers", responseHeaders());
    map.put("response.payload", responsePayload().toString());
    return map;
  }
}
