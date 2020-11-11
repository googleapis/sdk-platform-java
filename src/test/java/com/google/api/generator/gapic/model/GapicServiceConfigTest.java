// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.gapic.model;

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.protoparser.ServiceConfigParser;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.util.Durations;
import com.google.rpc.Code;
import com.google.showcase.v1beta1.EchoOuterClass;
import io.grpc.serviceconfig.MethodConfig;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;

public class GapicServiceConfigTest {
  private static final double EPSILON = 1e-4;
  private static final String JSON_DIRECTORY =
      "src/test/java/com/google/api/generator/gapic/testdata/";

  @Test
  public void serviceConfig_noConfigsFound() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    ServiceDescriptor echoServiceDescriptor = echoFileDescriptor.getServices().get(0);
    Service service = parseService(echoFileDescriptor);

    String jsonFilename = "retrying_grpc_service_config.json";
    Path jsonPath = Paths.get(JSON_DIRECTORY, jsonFilename);
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(serviceConfigOpt.isPresent());
    GapicServiceConfig serviceConfig = serviceConfigOpt.get();

    Map<String, GapicRetrySettings> retrySettings = serviceConfig.getAllGapicRetrySettings(service);
    assertEquals(1, retrySettings.size());
    String retryParamsName = serviceConfig.getRetryParamsName(service, service.methods().get(0));
    assertEquals("no_retry_params", retryParamsName);

    assertEquals(GapicServiceConfig.EMPTY_TIMEOUT, retrySettings.get(retryParamsName).timeout());
    assertEquals(
        GapicServiceConfig.EMPTY_RETRY_POLICY, retrySettings.get(retryParamsName).retryPolicy());
    assertEquals(GapicRetrySettings.Kind.NONE, retrySettings.get(retryParamsName).kind());

    Map<String, List<Code>> retryCodes = serviceConfig.getAllRetryCodes(service);
    assertEquals(1, retryCodes.size());
    assertEquals(
        "no_retry_codes", serviceConfig.getRetryCodeName(service, service.methods().get(0)));
    assertEquals(GapicServiceConfig.EMPTY_RETRY_CODES, retryCodes.get("no_retry_codes"));
  }

  @Test
  public void serviceConfig_basic() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    ServiceDescriptor echoServiceDescriptor = echoFileDescriptor.getServices().get(0);
    Service service = parseService(echoFileDescriptor);

    String jsonFilename = "showcase_grpc_service_config.json";
    Path jsonPath = Paths.get(JSON_DIRECTORY, jsonFilename);
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(serviceConfigOpt.isPresent());
    GapicServiceConfig serviceConfig = serviceConfigOpt.get();

    Map<String, GapicRetrySettings> retrySettings = serviceConfig.getAllGapicRetrySettings(service);
    assertEquals(2, retrySettings.size());
    Map<String, List<Code>> retryCodes = serviceConfig.getAllRetryCodes(service);
    assertEquals(2, retryCodes.size());

    // Echo method has an explicitly-defined setting.
    Method method = findMethod(service, "Echo");
    assertThat(method).isNotNull();
    String retryParamsName = serviceConfig.getRetryParamsName(service, method);
    assertEquals("retry_policy_1_params", retryParamsName);
    GapicRetrySettings settings = retrySettings.get(retryParamsName);
    assertThat(settings).isNotNull();
    assertEquals(10, settings.timeout().getSeconds());
    assertEquals(GapicRetrySettings.Kind.FULL, settings.kind());

    MethodConfig.RetryPolicy retryPolicy = settings.retryPolicy();
    assertEquals(3, retryPolicy.getMaxAttempts());
    assertEquals(100, Durations.toMillis(retryPolicy.getInitialBackoff()));
    assertEquals(3000, Durations.toMillis(retryPolicy.getMaxBackoff()));
    assertEquals(2.0, retryPolicy.getBackoffMultiplier(), EPSILON);

    String retryCodeName = serviceConfig.getRetryCodeName(service, method);
    assertEquals("retry_policy_1_codes", retryCodeName);
    List<Code> retryCode = retryCodes.get(retryCodeName);
    assertThat(retryCode).containsExactly(Code.UNAVAILABLE, Code.UNKNOWN);

    // Chat method defaults to the service-defined setting.
    method = findMethod(service, "Chat");
    assertThat(method).isNotNull();
    retryParamsName = serviceConfig.getRetryParamsName(service, method);
    assertEquals("no_retry_0_params", retryParamsName);

    settings = retrySettings.get(retryParamsName);
    assertThat(settings).isNotNull();
    assertEquals(5, settings.timeout().getSeconds());
    assertEquals(GapicServiceConfig.EMPTY_RETRY_POLICY, settings.retryPolicy());
    assertEquals(GapicRetrySettings.Kind.NO_RETRY, settings.kind());

    retryCodeName = serviceConfig.getRetryCodeName(service, method);
    assertEquals("no_retry_0_codes", retryCodeName);
    assertEquals(GapicServiceConfig.EMPTY_RETRY_CODES, retryCodes.get(retryCodeName));
  }

  private static Service parseService(FileDescriptor fileDescriptor) {
    Map<String, Message> messageTypes = Parser.parseMessages(fileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(fileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(fileDescriptor, messageTypes, resourceNames, outputResourceNames);
    assertEquals(1, services.size());

    return services.get(0);
  }

  private static Method findMethod(Service service, String methodName) {
    for (Method m : service.methods()) {
      if (m.name().equals(methodName)) {
        return m;
      }
    }
    return null;
  }
}
