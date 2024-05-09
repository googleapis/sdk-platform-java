/*
 * Copyright 2023 Google LLC
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

package com.google.showcase.v1beta1;

import static com.google.showcase.v1beta1.ComplianceClient.ListLocationsPagedResponse;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.httpjson.GaxHttpJsonProperties;
import com.google.api.gax.httpjson.testing.MockHttpService;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.ApiExceptionFactory;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.testing.FakeStatusCode;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.collect.Lists;
import com.google.iam.v1.AuditConfig;
import com.google.iam.v1.Binding;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.FieldMask;
import com.google.showcase.v1beta1.stub.HttpJsonComplianceStub;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Generated;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Generated("by gapic-generator-java")
class ComplianceClientHttpJsonTest {
  private static MockHttpService mockService;
  private static ComplianceClient client;

  @BeforeAll
  static void startStaticServer() throws IOException {
    mockService =
        new MockHttpService(
            HttpJsonComplianceStub.getMethodDescriptors(), ComplianceSettings.getDefaultEndpoint());
    ComplianceSettings settings =
        ComplianceSettings.newHttpJsonBuilder()
            .setTransportChannelProvider(
                ComplianceSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(mockService)
                    .build())
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = ComplianceClient.create(settings);
  }

  @AfterAll
  static void stopServer() {
    client.close();
  }

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() throws Exception {
    mockService.reset();
  }

  @Test
  void repeatDataBodyTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockService.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataBody(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void repeatDataBodyExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataBody(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataBodyInfoTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockService.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataBodyInfo(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void repeatDataBodyInfoExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataBodyInfo(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataQueryTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockService.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataQuery(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void repeatDataQueryExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataQuery(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataSimplePathTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockService.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(
                ComplianceData.newBuilder()
                    .setFString("fString-9139")
                    .setFInt32(-1143775883)
                    .setFSint32(-815756300)
                    .setFSfixed32(-763212615)
                    .setFUint32(-758497998)
                    .setFFixed32(1837548026)
                    .setFInt64(-1143775788)
                    .setFSint64(-815756205)
                    .setFSfixed64(-763212520)
                    .setFUint64(-758497903)
                    .setFFixed64(1837548121)
                    .setFDouble(-1239459382)
                    .setFFloat(-1146609341)
                    .setFBool(true)
                    .setFBytes(ByteString.EMPTY)
                    .setFChild(ComplianceDataChild.newBuilder().build())
                    .setPString("pString-1191954271")
                    .setPInt32(-858673665)
                    .setPDouble(-991225216)
                    .setPBool(true)
                    .setPChild(ComplianceDataChild.newBuilder().build())
                    .build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataSimplePath(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void repeatDataSimplePathExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(
                  ComplianceData.newBuilder()
                      .setFString("fString-9139")
                      .setFInt32(-1143775883)
                      .setFSint32(-815756300)
                      .setFSfixed32(-763212615)
                      .setFUint32(-758497998)
                      .setFFixed32(1837548026)
                      .setFInt64(-1143775788)
                      .setFSint64(-815756205)
                      .setFSfixed64(-763212520)
                      .setFUint64(-758497903)
                      .setFFixed64(1837548121)
                      .setFDouble(-1239459382)
                      .setFFloat(-1146609341)
                      .setFBool(true)
                      .setFBytes(ByteString.EMPTY)
                      .setFChild(ComplianceDataChild.newBuilder().build())
                      .setPString("pString-1191954271")
                      .setPInt32(-858673665)
                      .setPDouble(-991225216)
                      .setPBool(true)
                      .setPChild(ComplianceDataChild.newBuilder().build())
                      .build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataSimplePath(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataPathResourceTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockService.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(
                ComplianceData.newBuilder()
                    .setFString("first/firs-9148")
                    .setFInt32(-1143775883)
                    .setFSint32(-815756300)
                    .setFSfixed32(-763212615)
                    .setFUint32(-758497998)
                    .setFFixed32(1837548026)
                    .setFInt64(-1143775788)
                    .setFSint64(-815756205)
                    .setFSfixed64(-763212520)
                    .setFUint64(-758497903)
                    .setFFixed64(1837548121)
                    .setFDouble(-1239459382)
                    .setFFloat(-1146609341)
                    .setFBool(true)
                    .setFBytes(ByteString.EMPTY)
                    .setFChild(
                        ComplianceDataChild.newBuilder()
                            .setFString("second/secon-5446")
                            .setFFloat(-1146609341)
                            .setFDouble(-1239459382)
                            .setFBool(true)
                            .setFContinent(Continent.forNumber(0))
                            .setFChild(ComplianceDataGrandchild.newBuilder().build())
                            .setPString("pString-1191954271")
                            .setPFloat(-861507123)
                            .setPDouble(-991225216)
                            .setPBool(true)
                            .setPContinent(Continent.forNumber(0))
                            .setPChild(ComplianceDataGrandchild.newBuilder().build())
                            .build())
                    .setPString("pString-1191954271")
                    .setPInt32(-858673665)
                    .setPDouble(-991225216)
                    .setPBool(true)
                    .setPChild(ComplianceDataChild.newBuilder().build())
                    .build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataPathResource(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void repeatDataPathResourceExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(
                  ComplianceData.newBuilder()
                      .setFString("first/firs-9148")
                      .setFInt32(-1143775883)
                      .setFSint32(-815756300)
                      .setFSfixed32(-763212615)
                      .setFUint32(-758497998)
                      .setFFixed32(1837548026)
                      .setFInt64(-1143775788)
                      .setFSint64(-815756205)
                      .setFSfixed64(-763212520)
                      .setFUint64(-758497903)
                      .setFFixed64(1837548121)
                      .setFDouble(-1239459382)
                      .setFFloat(-1146609341)
                      .setFBool(true)
                      .setFBytes(ByteString.EMPTY)
                      .setFChild(
                          ComplianceDataChild.newBuilder()
                              .setFString("second/secon-5446")
                              .setFFloat(-1146609341)
                              .setFDouble(-1239459382)
                              .setFBool(true)
                              .setFContinent(Continent.forNumber(0))
                              .setFChild(ComplianceDataGrandchild.newBuilder().build())
                              .setPString("pString-1191954271")
                              .setPFloat(-861507123)
                              .setPDouble(-991225216)
                              .setPBool(true)
                              .setPContinent(Continent.forNumber(0))
                              .setPChild(ComplianceDataGrandchild.newBuilder().build())
                              .build())
                      .setPString("pString-1191954271")
                      .setPInt32(-858673665)
                      .setPDouble(-991225216)
                      .setPBool(true)
                      .setPChild(ComplianceDataChild.newBuilder().build())
                      .build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataPathResource(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataPathTrailingResourceTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockService.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(
                ComplianceData.newBuilder()
                    .setFString("first/firs-9148")
                    .setFInt32(-1143775883)
                    .setFSint32(-815756300)
                    .setFSfixed32(-763212615)
                    .setFUint32(-758497998)
                    .setFFixed32(1837548026)
                    .setFInt64(-1143775788)
                    .setFSint64(-815756205)
                    .setFSfixed64(-763212520)
                    .setFUint64(-758497903)
                    .setFFixed64(1837548121)
                    .setFDouble(-1239459382)
                    .setFFloat(-1146609341)
                    .setFBool(true)
                    .setFBytes(ByteString.EMPTY)
                    .setFChild(
                        ComplianceDataChild.newBuilder()
                            .setFString("second/secon-3276")
                            .setFFloat(-1146609341)
                            .setFDouble(-1239459382)
                            .setFBool(true)
                            .setFContinent(Continent.forNumber(0))
                            .setFChild(ComplianceDataGrandchild.newBuilder().build())
                            .setPString("pString-1191954271")
                            .setPFloat(-861507123)
                            .setPDouble(-991225216)
                            .setPBool(true)
                            .setPContinent(Continent.forNumber(0))
                            .setPChild(ComplianceDataGrandchild.newBuilder().build())
                            .build())
                    .setPString("pString-1191954271")
                    .setPInt32(-858673665)
                    .setPDouble(-991225216)
                    .setPBool(true)
                    .setPChild(ComplianceDataChild.newBuilder().build())
                    .build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataPathTrailingResource(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void repeatDataPathTrailingResourceExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(
                  ComplianceData.newBuilder()
                      .setFString("first/firs-9148")
                      .setFInt32(-1143775883)
                      .setFSint32(-815756300)
                      .setFSfixed32(-763212615)
                      .setFUint32(-758497998)
                      .setFFixed32(1837548026)
                      .setFInt64(-1143775788)
                      .setFSint64(-815756205)
                      .setFSfixed64(-763212520)
                      .setFUint64(-758497903)
                      .setFFixed64(1837548121)
                      .setFDouble(-1239459382)
                      .setFFloat(-1146609341)
                      .setFBool(true)
                      .setFBytes(ByteString.EMPTY)
                      .setFChild(
                          ComplianceDataChild.newBuilder()
                              .setFString("second/secon-3276")
                              .setFFloat(-1146609341)
                              .setFDouble(-1239459382)
                              .setFBool(true)
                              .setFContinent(Continent.forNumber(0))
                              .setFChild(ComplianceDataGrandchild.newBuilder().build())
                              .setPString("pString-1191954271")
                              .setPFloat(-861507123)
                              .setPDouble(-991225216)
                              .setPBool(true)
                              .setPContinent(Continent.forNumber(0))
                              .setPChild(ComplianceDataGrandchild.newBuilder().build())
                              .build())
                      .setPString("pString-1191954271")
                      .setPInt32(-858673665)
                      .setPDouble(-991225216)
                      .setPBool(true)
                      .setPChild(ComplianceDataChild.newBuilder().build())
                      .build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataPathTrailingResource(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataBodyPutTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockService.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataBodyPut(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void repeatDataBodyPutExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataBodyPut(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataBodyPatchTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockService.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataBodyPatch(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void repeatDataBodyPatchExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataBodyPatch(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getEnumTest() throws Exception {
    EnumResponse expectedResponse =
        EnumResponse.newBuilder()
            .setRequest(EnumRequest.newBuilder().build())
            .setContinent(Continent.forNumber(0))
            .build();
    mockService.addResponse(expectedResponse);

    EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();

    EnumResponse actualResponse = client.getEnum(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void getEnumExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();
      client.getEnum(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void verifyEnumTest() throws Exception {
    EnumResponse expectedResponse =
        EnumResponse.newBuilder()
            .setRequest(EnumRequest.newBuilder().build())
            .setContinent(Continent.forNumber(0))
            .build();
    mockService.addResponse(expectedResponse);

    EnumResponse request =
        EnumResponse.newBuilder()
            .setRequest(EnumRequest.newBuilder().build())
            .setContinent(Continent.forNumber(0))
            .build();

    EnumResponse actualResponse = client.verifyEnum(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void verifyEnumExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      EnumResponse request =
          EnumResponse.newBuilder()
              .setRequest(EnumRequest.newBuilder().build())
              .setContinent(Continent.forNumber(0))
              .build();
      client.verifyEnum(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void listLocationsTest() throws Exception {
    Location responsesElement = Location.newBuilder().build();
    ListLocationsResponse expectedResponse =
        ListLocationsResponse.newBuilder()
            .setNextPageToken("")
            .addAllLocations(Arrays.asList(responsesElement))
            .build();
    mockService.addResponse(expectedResponse);

    ListLocationsRequest request =
        ListLocationsRequest.newBuilder()
            .setName("projects/project-3664")
            .setFilter("filter-1274492040")
            .setPageSize(883849137)
            .setPageToken("pageToken873572522")
            .build();

    ListLocationsPagedResponse pagedListResponse = client.listLocations(request);

    List<Location> resources = Lists.newArrayList(pagedListResponse.iterateAll());

    Assertions.assertEquals(1, resources.size());
    Assertions.assertEquals(expectedResponse.getLocationsList().get(0), resources.get(0));

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void listLocationsExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      ListLocationsRequest request =
          ListLocationsRequest.newBuilder()
              .setName("projects/project-3664")
              .setFilter("filter-1274492040")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      client.listLocations(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getLocationTest() throws Exception {
    Location expectedResponse =
        Location.newBuilder()
            .setName("name3373707")
            .setLocationId("locationId1541836720")
            .setDisplayName("displayName1714148973")
            .putAllLabels(new HashMap<String, String>())
            .setMetadata(Any.newBuilder().build())
            .build();
    mockService.addResponse(expectedResponse);

    GetLocationRequest request =
        GetLocationRequest.newBuilder()
            .setName("projects/project-9062/locations/location-9062")
            .build();

    Location actualResponse = client.getLocation(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void getLocationExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      GetLocationRequest request =
          GetLocationRequest.newBuilder()
              .setName("projects/project-9062/locations/location-9062")
              .build();
      client.getLocation(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void setIamPolicyTest() throws Exception {
    Policy expectedResponse =
        Policy.newBuilder()
            .setVersion(351608024)
            .addAllBindings(new ArrayList<Binding>())
            .addAllAuditConfigs(new ArrayList<AuditConfig>())
            .setEtag(ByteString.EMPTY)
            .build();
    mockService.addResponse(expectedResponse);

    SetIamPolicyRequest request =
        SetIamPolicyRequest.newBuilder()
            .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
            .setPolicy(Policy.newBuilder().build())
            .setUpdateMask(FieldMask.newBuilder().build())
            .build();

    Policy actualResponse = client.setIamPolicy(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void setIamPolicyExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
              .setPolicy(Policy.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      client.setIamPolicy(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getIamPolicyTest() throws Exception {
    Policy expectedResponse =
        Policy.newBuilder()
            .setVersion(351608024)
            .addAllBindings(new ArrayList<Binding>())
            .addAllAuditConfigs(new ArrayList<AuditConfig>())
            .setEtag(ByteString.EMPTY)
            .build();
    mockService.addResponse(expectedResponse);

    GetIamPolicyRequest request =
        GetIamPolicyRequest.newBuilder()
            .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
            .setOptions(GetPolicyOptions.newBuilder().build())
            .build();

    Policy actualResponse = client.getIamPolicy(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void getIamPolicyExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      client.getIamPolicy(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void testIamPermissionsTest() throws Exception {
    TestIamPermissionsResponse expectedResponse =
        TestIamPermissionsResponse.newBuilder().addAllPermissions(new ArrayList<String>()).build();
    mockService.addResponse(expectedResponse);

    TestIamPermissionsRequest request =
        TestIamPermissionsRequest.newBuilder()
            .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
            .addAllPermissions(new ArrayList<String>())
            .build();

    TestIamPermissionsResponse actualResponse = client.testIamPermissions(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<String> actualRequests = mockService.getRequestPaths();
    Assertions.assertEquals(1, actualRequests.size());

    String apiClientHeaderKey =
        mockService
            .getRequestHeaders()
            .get(ApiClientHeaderProvider.getDefaultApiClientHeaderKey())
            .iterator()
            .next();
    Assertions.assertTrue(
        GaxHttpJsonProperties.getDefaultApiClientHeaderPattern()
            .matcher(apiClientHeaderKey)
            .matches());
  }

  @Test
  void testIamPermissionsExceptionTest() throws Exception {
    ApiException exception =
        ApiExceptionFactory.createException(
            new Exception(), FakeStatusCode.of(StatusCode.Code.INVALID_ARGUMENT), false);
    mockService.addException(exception);

    try {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      client.testIamPermissions(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }
}
