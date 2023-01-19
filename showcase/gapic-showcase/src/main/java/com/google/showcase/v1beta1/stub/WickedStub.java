/*
 * Copyright 2022 Google LLC
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

package com.google.showcase.v1beta1.stub;

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.showcase.v1beta1.EvilRequest;
import com.google.showcase.v1beta1.EvilResponse;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Base stub class for the Wicked service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public abstract class WickedStub implements BackgroundResource {

  public UnaryCallable<EvilRequest, EvilResponse> craftEvilPlanCallable() {
    throw new UnsupportedOperationException("Not implemented: craftEvilPlanCallable()");
  }

  public BidiStreamingCallable<EvilRequest, EvilResponse> brainstormEvilPlansCallable() {
    throw new UnsupportedOperationException("Not implemented: brainstormEvilPlansCallable()");
  }

  public ClientStreamingCallable<EvilRequest, EvilResponse> persuadeEvilPlanCallable() {
    throw new UnsupportedOperationException("Not implemented: persuadeEvilPlanCallable()");
  }

  @Override
  public abstract void close();
}
