package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.util.JsonFormat;
import com.google.showcase.v1beta1.ComplianceClient;
import com.google.showcase.v1beta1.ComplianceData;
import com.google.showcase.v1beta1.ComplianceGroup;
import com.google.showcase.v1beta1.ComplianceSettings;
import com.google.showcase.v1beta1.ComplianceSuite;
import com.google.showcase.v1beta1.RepeatRequest;
import com.google.showcase.v1beta1.RepeatResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ITHttpAnnotation {

  @Parameterized.Parameters(name = "Compliance Group Name: {0}")
  public static String[] data() {
    return new String[] {
      "Fully working conversions, resources",
      "Binding selection testing",
      "Cases that apply to non-path requests",
      "Fully working conversions, no resources"
    };
  }

  @Parameterized.Parameter(0)
  public String groupName;

  private ComplianceSuite complianceSuite;
  private ComplianceClient complianceClient;
  private Map<String, Function<RepeatRequest, RepeatResponse>> complianceValidRpcMap;

  @Before
  public void createClient() throws IOException, GeneralSecurityException {
    ComplianceSuite.Builder builder = ComplianceSuite.newBuilder();
    JsonFormat.parser()
        .merge(
            new InputStreamReader(
                ITHttpAnnotation.class
                    .getClassLoader()
                    .getResourceAsStream("compliance_suite.json")),
            builder);
    complianceSuite = builder.build();

    ComplianceSettings httpjsonComplianceSettings =
        ComplianceSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                ComplianceSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    complianceClient = ComplianceClient.create(httpjsonComplianceSettings);

    // Mapping of Compliance Suite file RPC Names to ComplianceClient methods
    complianceValidRpcMap =
        ImmutableMap.of(
            "Compliance.RepeatDataBody",
            complianceClient::repeatDataBody,
            "Compliance.RepeatDataBodyInfo",
            complianceClient::repeatDataBodyInfo,
            "Compliance.RepeatDataQuery",
            complianceClient::repeatDataQuery,
            "Compliance.RepeatDataSimplePath",
            complianceClient::repeatDataSimplePath,
            "Compliance.RepeatDataBodyPut",
            complianceClient::repeatDataBodyPut,
            "Compliance.RepeatDataBodyPatch",
            complianceClient::repeatDataBodyPatch,
            "Compliance.RepeatDataPathResource",
            complianceClient::repeatDataPathResource);
  }

  @After
  public void destroyClient() {
    complianceClient.close();
  }

  @Test
  public void testComplianceGroup() {
    Optional<ComplianceGroup> complianceGroupOptional =
        complianceSuite.getGroupList().stream()
            .filter(x -> x.getName().equals(groupName))
            .findFirst();
    assertThat(complianceGroupOptional.isPresent()).isTrue();
    ComplianceGroup complianceGroup = complianceGroupOptional.get();
    List<String> validRpcList =
        complianceGroup.getRpcsList().stream()
            .filter(complianceValidRpcMap::containsKey)
            .collect(Collectors.toList());
    for (String rpcName : validRpcList) {
      Function<RepeatRequest, RepeatResponse> rpc = complianceValidRpcMap.get(rpcName);
      for (RepeatRequest repeatRequest : complianceGroup.getRequestsList()) {
        ComplianceData expectedData = repeatRequest.getInfo();
        RepeatResponse response = rpc.apply(repeatRequest);
        assertThat(response.getRequest().getInfo()).isEqualTo(expectedData);
      }
    }
  }
}
