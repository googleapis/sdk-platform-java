/*
 * Copyright 2023 Google LLC
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
package com.google.api.gax.batching;

import com.google.common.base.Preconditions;

/**
 * The default implementation of {@link BatchResource} which tracks the elementCount and byteCount.
 */
final class DefaultBatchResource implements BatchResource {

  private long elementCount;
  private long byteCount;

  DefaultBatchResource(long elementCount, long byteCount) {
    this.elementCount = elementCount;
    this.byteCount = byteCount;
  }

  @Override
  public BatchResource add(BatchResource resource) {
    Preconditions.checkArgument(
        resource instanceof DefaultBatchResource,
        "BatchResource needs to be an instance of DefaultBatchResource");
    this.elementCount += ((DefaultBatchResource) resource).elementCount;
    this.byteCount += ((DefaultBatchResource) resource).byteCount;
    return this;
  }

  @Override
  public long getElementCount() {
    return elementCount;
  }

  @Override
  public long getByteCount() {
    return byteCount;
  }

  @Override
  public boolean isEmpty() {
    return elementCount == 0;
  }
}
