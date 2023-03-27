package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.util.JsonFormat;
import com.google.showcase.v1beta1.ComplianceClient;
import com.google.showcase.v1beta1.ComplianceGroup;
import com.google.showcase.v1beta1.ComplianceSettings;
import com.google.showcase.v1beta1.ComplianceSuite;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.RepeatRequest;
import com.google.showcase.v1beta1.RepeatResponse;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.function.Function;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITCompliance {

  private ComplianceSuite complianceSuite;
  private ComplianceClient complianceClient;
  private Map<String, Function<RepeatRequest, RepeatResponse>> complianceValidRpcSet;

  @Before
  public void createClient() throws IOException, GeneralSecurityException {
    ComplianceSuite.Builder builder = ComplianceSuite.newBuilder();
    JsonFormat.parser().merge(new FileReader("src/test/resources/compliance_suite.json"), builder);
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

    complianceValidRpcSet =
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
                    x -> complianceClient.repeatDataPathResource(x),
                    "Compliance.RepeatDataPathTrailingResource",
                    x -> complianceClient.repeatDataPathTrailingResource(x));
  }

  @After
  public void destroyClient() {
    complianceClient.close();
  }

  /*
  Note: For PATCH requests, Gax HttpJson sets the HttpVerb as POST and sets the
  `x-http-method-override` method as PATCH.
   */
  @Test
  public void testCompliance() {
    for (ComplianceGroup compliancegroup : complianceSuite.getGroupList()) {
      ProtocolStringList protocolStringList = compliancegroup.getRpcsList();
      for (RepeatRequest repeatRequest : compliancegroup.getRequestsList()) {
        for (String rpcName : protocolStringList) {
          if (complianceValidRpcSet.containsKey(rpcName)) {
            System.out.printf(
                    "Testing group: `%s`- RPC Name: `%s` with Request Name: `%s`\n",
                    compliancegroup.getName(), rpcName, repeatRequest.getName());
            RepeatResponse response = complianceValidRpcSet.get(rpcName).apply(repeatRequest);
            assertThat(response.getRequest().getInfo()).isEqualTo(repeatRequest.getInfo());
          }
        }
      }
    }
  }
}
