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
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.RepeatRequest;
import com.google.showcase.v1beta1.RepeatResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITHttpAnnotation {

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
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
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
            x -> complianceClient.repeatDataBody(x),
            "Compliance.RepeatDataBodyInfo",
            x -> complianceClient.repeatDataBodyInfo(x),
            "Compliance.RepeatDataQuery",
            x -> complianceClient.repeatDataQuery(x),
            "Compliance.RepeatDataSimplePath",
            x -> complianceClient.repeatDataSimplePath(x),
            "Compliance.RepeatDataBodyPut",
            x -> complianceClient.repeatDataBodyPut(x),
            "Compliance.RepeatDataBodyPatch",
            x -> complianceClient.repeatDataBodyPatch(x),
            "Compliance.RepeatDataPathResource",
            x -> complianceClient.repeatDataPathResource(x));
  }

  @After
  public void destroyClient() {
    complianceClient.close();
  }

  @Test
  public void testCompliance() {
    for (ComplianceGroup compliancegroup : complianceSuite.getGroupList()) {
      List<String> validRpcList =
          compliancegroup.getRpcsList().stream()
              .filter(complianceValidRpcMap::containsKey)
              .collect(Collectors.toList());
      for (String rpcName : validRpcList) {
        for (RepeatRequest repeatRequest : compliancegroup.getRequestsList()) {
          ComplianceData expectedData = repeatRequest.getInfo();
          RepeatResponse response = complianceValidRpcMap.get(rpcName).apply(repeatRequest);
          assertThat(response.getRequest().getInfo()).isEqualTo(expectedData);
        }
      }
    }
  }
}
