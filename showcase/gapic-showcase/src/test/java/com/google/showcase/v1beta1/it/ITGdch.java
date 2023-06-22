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
  public void testCreateClient_withGdchCredentialAndNoAudience_defaultsToEndpointBasedAudience() throws IOException {

    // we create the client as usual - no audience passed
    context = ClientContext.create(settings);
    stubSettings = EchoStubSettings.newBuilder(context).build();
    client = EchoClient.create(stubSettings.createStub());

    // We retrieve from context and from client
    // the client has only access to creds provider, which may differ from the actual credentials used in the Context
    Credentials fromContext = context.getCredentials();
    Credentials fromClient = initialCredentials;

    // Since ClientContext.create() uses a modified version of GdchCredentials
    // via GdchCredentials.createWithGdchAudience(), they should be different objects
    assertNotSame(fromContext, fromClient);

    // When credentials don't have an audience (such as the ones we passed to client creation and now stored in the
    // provider) they will throw if we try to refresh them
    NullPointerException expectedEx =
        assertThrows(NullPointerException.class, () -> initialCredentials.refresh());
    assertTrue(
        expectedEx.getMessage().contains("Audience are not configured for GDCH service account"));

    // However, the credentials prepared in ClientContext should be able to refresh since the audience would be
    // internally defaulted the endpoint of the StubSettings
    registerCredential(fromContext);
    ((GdchCredentials) fromContext).refreshAccessToken();
  }

  /**
   * Confirms creating a client with a valid audience is successful. We cannot confirm which
   * audience is chosen (our passed audience or the endpoint) but this is confirmed in the unit
   * tests.
   *
   * @throws IOException
   */
  @Test
  public void testCreateClient_withGdchCredentialWithValidAudience_usesCredentialWithPassedAudience() throws IOException {

    // Similar to the previous test, create a client as usual but this time we pass a explicit audience. It should
    // be created without issues
    String audience = "valid-audience";
    settings = settings.toBuilder().setGdchApiAudience(audience).build();
    context = ClientContext.create(settings);
    stubSettings = EchoStubSettings.newBuilder(context).build();
    client = EchoClient.create(stubSettings.createStub());

    // We retrieve both creds from the creds provider and the ones prepared in the context (which should have been
    // re-created using GdchCredentials.createWithAudience("valid-audience"))
    Credentials fromContext = context.getCredentials();
    assertNotSame(fromContext, initialCredentials);

    // Again, since the initial credentials don't have an audience, we should not be able to refresh them
    NullPointerException thrownByClientCreds =
        assertThrows(NullPointerException.class, () -> initialCredentials.refresh());
    assertTrue(
        thrownByClientCreds
            .getMessage()
            .contains("Audience are not configured for GDCH service account"));
    Exception unexpected = null;

    // But the credentials prepared in ClientContext should be able to refresh since the audience would be internally
    // set to the one passed in stub settings
    registerCredential(fromContext);
    ((GdchCredentials) fromContext).refreshAccessToken();
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
