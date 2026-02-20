/*
 * Copyright 2026 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.redis.v1beta1.stub;

import com.google.api.core.BetaApi;
import com.google.api.core.InternalApi;
import com.google.api.gax.tracing.ApiTracerContext;
import javax.annotation.Generated;

@InternalApi
@BetaApi
@Generated("by gapic-generator-java")
public class CloudRedisApiTracerContext extends ApiTracerContext {
  private final String serverAddress;

  private CloudRedisApiTracerContext(String serverAddress) {
    this.serverAddress = serverAddress;
  }

  public static CloudRedisApiTracerContext create(String serverAddress) {
    return new CloudRedisApiTracerContext(serverAddress);
  }

  @Override
  public String getServerAddress() {
    return serverAddress;
  }

  @Override
  public String getRepo() {
    return null;
  }
}
