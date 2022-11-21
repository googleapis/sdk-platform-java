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
import com.google.api.gax.rpc.UnaryCallable;
import com.google.protobuf.Empty;
import com.google.showcase.v1beta1.AttemptSequenceRequest;
import com.google.showcase.v1beta1.CreateSequenceRequest;
import com.google.showcase.v1beta1.GetSequenceReportRequest;
import com.google.showcase.v1beta1.Sequence;
import com.google.showcase.v1beta1.SequenceReport;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Base stub class for the SequenceService service API.
 *
 * <p>This class is for advanced usage and reflects the underlying API directly.
 */
@BetaApi
@Generated("by gapic-generator-java")
public abstract class SequenceServiceStub implements BackgroundResource {

  public UnaryCallable<CreateSequenceRequest, Sequence> createSequenceCallable() {
    throw new UnsupportedOperationException("Not implemented: createSequenceCallable()");
  }

  public UnaryCallable<GetSequenceReportRequest, SequenceReport> getSequenceReportCallable() {
    throw new UnsupportedOperationException("Not implemented: getSequenceReportCallable()");
  }

  public UnaryCallable<AttemptSequenceRequest, Empty> attemptSequenceCallable() {
    throw new UnsupportedOperationException("Not implemented: attemptSequenceCallable()");
  }

  @Override
  public abstract void close();
}
