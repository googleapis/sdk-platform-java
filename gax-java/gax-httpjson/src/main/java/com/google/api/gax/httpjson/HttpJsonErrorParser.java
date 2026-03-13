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

package com.google.api.gax.httpjson;

import com.google.api.core.InternalApi;
import com.google.api.gax.rpc.ErrorDetails;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.TypeRegistry;
import com.google.protobuf.util.JsonFormat;
import com.google.rpc.BadRequest;
import com.google.rpc.DebugInfo;
import com.google.rpc.ErrorInfo;
import com.google.rpc.Help;
import com.google.rpc.LocalizedMessage;
import com.google.rpc.PreconditionFailure;
import com.google.rpc.QuotaFailure;
import com.google.rpc.RequestInfo;
import com.google.rpc.ResourceInfo;
import com.google.rpc.RetryInfo;
import com.google.rpc.Status;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility for parsing Google Cloud error responses from JSON.
 *
 * <p>This parser extracts {@link ErrorDetails} from a standard Google Cloud error response JSON
 * payload, which typically contains a top-level "error" object with a "details" list.
 */
@InternalApi
class HttpJsonErrorParser {
  private static final Logger LOGGER = Logger.getLogger(HttpJsonErrorParser.class.getName());
  private static final TypeRegistry STANDARD_ERROR_TYPES =
      TypeRegistry.newBuilder()
          .add(ErrorInfo.getDescriptor())
          .add(RetryInfo.getDescriptor())
          .add(DebugInfo.getDescriptor())
          .add(QuotaFailure.getDescriptor())
          .add(PreconditionFailure.getDescriptor())
          .add(BadRequest.getDescriptor())
          .add(RequestInfo.getDescriptor())
          .add(ResourceInfo.getDescriptor())
          .add(Help.getDescriptor())
          .add(LocalizedMessage.getDescriptor())
          .build();
  private static final JsonFormat.Parser JSON_PARSER =
      JsonFormat.parser().ignoringUnknownFields().usingTypeRegistry(STANDARD_ERROR_TYPES);

  /**
   * Parses the given JSON error payload into {@link ErrorDetails}.
   *
   * @param errorJson The JSON string representing a Google Cloud error response.
   * @return An {@link ErrorDetails} object containing the parsed error information. Returns an
   *     empty {@link ErrorDetails} if the input is null, empty, or invalid.
   */
  static ErrorDetails parseErrorDetails(String errorJson) {
    if (errorJson == null || errorJson.isEmpty()) {
      return ErrorDetails.builder().build();
    }

    try {
      JsonObject root = JsonParser.parseString(errorJson).getAsJsonObject();
      if (!root.has("error")) {
        return ErrorDetails.builder().build();
      }

      JsonElement errorElement = root.get("error");
      Status.Builder statusBuilder = Status.newBuilder();
      JSON_PARSER.merge(errorElement.toString(), statusBuilder);
      Status status = statusBuilder.build();

      ErrorDetails.Builder errorDetailsBuilder = ErrorDetails.builder();
      errorDetailsBuilder.setRawErrorMessages(status.getDetailsList());
      return errorDetailsBuilder.build();
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeException("Failed to parse Google Cloud error response", e);
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error during Google Cloud error parsing", e);
    }
  }
}
