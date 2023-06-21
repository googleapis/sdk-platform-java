package com.google.showcase.v1beta1.it;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.rpc.ClientContext;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GdchCredentials;
import com.google.auth.oauth2.GdchCredentialsTestUtil;
import com.google.auth.oauth2.MockTokenServerTransportFactory;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.stub.EchoStub;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test suite to confirm a client can be instantiated with GDCH credentials. No calls are made since
 * it is not feasible to test against real GDCH servers (or replicate an environment)
 */
public class ITGdch {

  private static final String TEST_GDCH_CREDENTIAL_FILE = "/test_gdch_credential.json";
  private static final String CA_CERT_RESOURCE_PATH = "/fake_cert.pem";
  private static final String CA_CERT_JSON_KEY = "ca_cert_path";
  private static final String TEMP_CREDENTIAL_JSON_FILENAME = "temp_gdch_credential.json";
  private static final String GDCH_TOKEN_STRING = "1/MkSJoj1xsli0AccessToken_NKPY2";
  private static final String SID_NAME = "service-identity-name";

  @Rule public TemporaryFolder tempFolder = new TemporaryFolder();

  private EchoClient client;
  private EchoSettings settings;
  private EchoStubSettings stubSettings;
  private Credentials initialCredentials;
  private ClientContext context;
  private EchoStub stub;
  private MockTokenServerTransportFactory transportFactory;
  private String projectId;
  private URI tokenUri;

  @Before
  public void setup() throws IOException, URISyntaxException {
    transportFactory = new MockTokenServerTransportFactory();
    prepareCredentials();
    tempFolder.create();
    settings =
        EchoSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(initialCredentials))
            .build();
  }

  @After
  public void tearDown() {
    if (client != null) {
      client.close();
    }
  }

  private void prepareCredentials() throws IOException, URISyntaxException {
    // compute absolute path of the CA certificate
    Path caCertPath = Paths.get(getClass().getResource(CA_CERT_RESOURCE_PATH).toURI());

    // open gdch credential json (still needs its "ca_cert_path" to point to the CA certificate
    // obtained from above)
    JsonFactory factory = new GsonFactory();
    GenericJson converted =
        factory.fromInputStream(
            getClass().getResourceAsStream(TEST_GDCH_CREDENTIAL_FILE), GenericJson.class);

    // modify and save to a temporary folder
    converted.set(CA_CERT_JSON_KEY, caCertPath.toAbsolutePath().toString());
    projectId = converted.get("project").toString();
    tokenUri = URI.create(converted.get("token_uri").toString());

    File tempGdchCredentialFile = tempFolder.newFile(TEMP_CREDENTIAL_JSON_FILENAME);
    try (FileWriter fileWriter = new FileWriter(tempGdchCredentialFile)) {
      String preparedJson = converted.toPrettyString();
      fileWriter.write(preparedJson);
    }

    // use temp location to instantiate credentials
    initialCredentials = GdchCredentialsTestUtil.fromJson(converted, transportFactory);
  }

  /**
   * {@link com.google.api.gax.rpc.ClientContext} will create a new {@link GdchCredentials} with an
   * audience defaulted to the endpoint if the audience is not manually passed. This test confirms
   * that a new credential is created from the context and can be refreshed
   *
   * @throws IOException
   */
  @Test
  public void testCreateClient_withGdchCredentialAndNoAudience() throws IOException {
    context = ClientContext.create(settings);
    stubSettings = EchoStubSettings.newBuilder(context).build();
    client = EchoClient.create(stubSettings.createStub());
    Credentials fromContext = context.getCredentials();
    Credentials fromClient = initialCredentials;
    assertNotSame(fromContext, fromClient);
    NullPointerException expectedEx =
        assertThrows(NullPointerException.class, () -> initialCredentials.refresh());
    assertTrue(
        expectedEx.getMessage().contains("Audience are not configured for GDCH service account"));
    Exception unexpected = null;
    try {
      registerCredential(fromContext);
      ((GdchCredentials) fromContext).refreshAccessToken();
    } catch (Exception ex) {
      unexpected = ex;
    }
    assertNull(unexpected);
  }

  /**
   * Confirms creating a client with a valid audience is successful. We cannot confirm which
   * audience is chosen (our passed audience or the endpoint) but this is confirmed in the unit
   * tests.
   *
   * @throws IOException
   */
  @Test
  public void testCreateClient_withGdchCredentialWithValidAudience() throws IOException {
    String audience = "valid-audience";
    settings = settings.toBuilder().setGdchApiAudience(audience).build();
    context = ClientContext.create(settings);
    stubSettings = EchoStubSettings.newBuilder(context).build();
    client = EchoClient.create(stubSettings.createStub());
    Credentials fromContext = context.getCredentials();
    assertNotSame(fromContext, initialCredentials);
    NullPointerException thrownByClientCreds =
        assertThrows(NullPointerException.class, () -> initialCredentials.refresh());
    assertTrue(
        thrownByClientCreds
            .getMessage()
            .contains("Audience are not configured for GDCH service account"));
    Exception unexpected = null;
    try {
      registerCredential(fromContext);
      ((GdchCredentials) fromContext).refreshAccessToken();
    } catch (Exception ex) {
      unexpected = ex;
    }
    assertNull(unexpected);
  }

  @Test
  public void testCreateClient_withGdchCredentialWithInvalidAudience_throws() throws IOException {
    settings = settings.toBuilder().setGdchApiAudience("$invalid-audience:").build();
    Exception expected =
        assertThrows(IllegalArgumentException.class, () -> client = EchoClient.create(settings));
    assertTrue(expected.getMessage().contains("audience string is not a valid URI"));
  }

  @Test
  public void testCreateClient_withNonGdchCredentialWithAnyAudience_throws() throws IOException {
    settings =
        settings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setGdchApiAudience("any-audience")
            .build();
    Exception expected =
        assertThrows(IllegalArgumentException.class, () -> client = EchoClient.create(settings));
    assertTrue(
        expected.getMessage().contains("audience can only be set when using GdchCredentials"));
  }

  private void registerCredential(Credentials fromContext) {
    GdchCredentialsTestUtil.registerGdchCredentialWithMockTransport(
        (GdchCredentials) fromContext,
        transportFactory.transport,
        projectId,
        SID_NAME,
        GDCH_TOKEN_STRING,
        tokenUri);
  }
}
