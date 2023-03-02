package com.google.showcase.v1beta1.it;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.showcase.v1beta1.ComplianceClient;
import com.google.showcase.v1beta1.ComplianceSettings;
import com.google.showcase.v1beta1.EchoSettings;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ITCompliance {
  private static ComplianceClient httpjsonClient;

  @BeforeClass
  public static void createClient() throws IOException, GeneralSecurityException {
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
    httpjsonClient = ComplianceClient.create(httpjsonComplianceSettings);
  }

  @AfterClass
  public static void destroyClient() {
    httpjsonClient.close();
  }
}
