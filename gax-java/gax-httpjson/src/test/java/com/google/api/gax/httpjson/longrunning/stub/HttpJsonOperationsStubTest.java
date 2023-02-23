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
package com.google.api.gax.httpjson.longrunning.stub;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.HttpRule;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.ProtoMessageRequestFormatter;
import com.google.common.collect.ImmutableMap;
import com.google.longrunning.CancelOperationRequest;
import com.google.longrunning.DeleteOperationRequest;
import com.google.longrunning.GetOperationRequest;
import com.google.longrunning.ListOperationsRequest;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class HttpJsonOperationsStubTest {

  private static HttpJsonOperationsStub httpJsonOperationsStub;

  @BeforeClass
  public static void setUp() throws Exception {
    httpJsonOperationsStub =
        HttpJsonOperationsStub.create(
            OperationsStubSettings.newBuilder()
                .setCredentialsProvider(NoCredentialsProvider.create())
                .build(),
            ImmutableMap.of(
                "google.longrunning.Operations.ListOperations",
                HttpRule.newBuilder()
                    .setGet("testList")
                    .addAdditionalBindings(HttpRule.newBuilder().setGet("testList2"))
                    .build(),
                "google.longrunning.Operations.GetOperation",
                HttpRule.newBuilder()
                    .setGet("testGet")
                    .addAdditionalBindings(HttpRule.newBuilder().setGet("testGet2"))
                    .build(),
                "google.longrunning.Operations.DeleteOperation",
                HttpRule.newBuilder()
                    .setDelete("testDelete")
                    .addAdditionalBindings(HttpRule.newBuilder().setGet("testDelete2"))
                    .build(),
                "google.longrunning.Operations.CancelOperation",
                HttpRule.newBuilder()
                    .setPost("testCancel")
                    .addAdditionalBindings(HttpRule.newBuilder().setGet("testCancel2"))
                    .build()));
  }

  @Test
  public void testMethodDescriptorsURI() {
    List<ApiMethodDescriptor> apiMethodDescriptorList =
        httpJsonOperationsStub.getAllMethodDescriptors();
    assertThat(apiMethodDescriptorList.get(0).getRequestFormatter().getPathTemplate().toRawString())
        .isEqualTo("testList");
    assertThat(apiMethodDescriptorList.get(1).getRequestFormatter().getPathTemplate().toRawString())
        .isEqualTo("testGet");
    assertThat(apiMethodDescriptorList.get(2).getRequestFormatter().getPathTemplate().toRawString())
        .isEqualTo("testDelete");
    assertThat(apiMethodDescriptorList.get(3).getRequestFormatter().getPathTemplate().toRawString())
        .isEqualTo("testCancel");
  }

  @Test
  public void testMethodDescriptorsAdditionalBindings() {
    List<ApiMethodDescriptor> apiMethodDescriptorList =
        httpJsonOperationsStub.getAllMethodDescriptors();
    ProtoMessageRequestFormatter<ListOperationsRequest>
        listOperationsRequestProtoMessageRequestFormatter =
            (ProtoMessageRequestFormatter<ListOperationsRequest>)
                apiMethodDescriptorList.get(0).getRequestFormatter();
    assertThat(listOperationsRequestProtoMessageRequestFormatter.getAdditionalPathTemplates())
        .hasSize(1);
    assertThat(
            listOperationsRequestProtoMessageRequestFormatter
                .getAdditionalPathTemplates()
                .get(0)
                .toRawString())
        .isEqualTo("testList2");
    ProtoMessageRequestFormatter<GetOperationRequest>
        getOperationRequestProtoMessageRequestFormatter =
            (ProtoMessageRequestFormatter<GetOperationRequest>)
                apiMethodDescriptorList.get(1).getRequestFormatter();
    assertThat(getOperationRequestProtoMessageRequestFormatter.getAdditionalPathTemplates())
        .hasSize(1);
    assertThat(
            getOperationRequestProtoMessageRequestFormatter
                .getAdditionalPathTemplates()
                .get(0)
                .toRawString())
        .isEqualTo("testGet2");
    ProtoMessageRequestFormatter<DeleteOperationRequest>
        deleteOperationRequestProtoMessageRequestFormatter =
            (ProtoMessageRequestFormatter<DeleteOperationRequest>)
                apiMethodDescriptorList.get(2).getRequestFormatter();
    assertThat(deleteOperationRequestProtoMessageRequestFormatter.getAdditionalPathTemplates())
        .hasSize(1);
    assertThat(
            deleteOperationRequestProtoMessageRequestFormatter
                .getAdditionalPathTemplates()
                .get(0)
                .toRawString())
        .isEqualTo("testDelete2");
    ProtoMessageRequestFormatter<CancelOperationRequest>
        cancelOperationRequestProtoMessageRequestFormatter =
            (ProtoMessageRequestFormatter<CancelOperationRequest>)
                apiMethodDescriptorList.get(3).getRequestFormatter();
    assertThat(cancelOperationRequestProtoMessageRequestFormatter.getAdditionalPathTemplates())
        .hasSize(1);
    assertThat(
            cancelOperationRequestProtoMessageRequestFormatter
                .getAdditionalPathTemplates()
                .get(0)
                .toRawString())
        .isEqualTo("testCancel2");
  }
}
