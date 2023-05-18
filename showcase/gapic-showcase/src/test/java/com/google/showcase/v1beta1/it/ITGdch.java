package com.google.showcase.v1beta1.it;

import static org.junit.Assert.*;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GdchCredentials;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ITGdch {

  private static final String TEST_GDCH_CREDENTIAL_FILE = "/test_gdch_credential.json";
  private static final String CA_CERT_RESOURCE_PATH = "/fake_cert.pem";
  private static final String CA_CERT_JSON_KEY = "ca_cert_path";
  private static final String TEMP_CREDENTIAL_JSON_FILENAME = "temp_gdch_credential.json";

  @Rule public TemporaryFolder tempFolder = new TemporaryFolder();

  private EchoSettings settings;
  private Credentials credentials;

  @Before
  public void setup() throws IOException, URISyntaxException {
    prepareCredentials();
    tempFolder.create();
    settings =
        EchoSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build();
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
    File tempGdchCredentialFile = tempFolder.newFile(TEMP_CREDENTIAL_JSON_FILENAME);
    try (FileWriter fileWriter = new FileWriter(tempGdchCredentialFile)) {
      String preparedJson = converted.toPrettyString();
      fileWriter.write(preparedJson);
    }

    // use temp location to instantiate credentials
    credentials = GdchCredentials.fromStream(new FileInputStream(tempGdchCredentialFile));
  }

  @Test
  public void testClientWithGdchCredentialNoAudience_correct() {
    Exception unexpected = getExceptionFromClientCreation();
    assertNull(unexpected);
  }

  @Test
  public void testClientWithGdchCredentialWithValidAudience_correct() throws IOException {
    settings = settings.toBuilder().setGdchApiAudience("valid-audience").build();
    Exception unexpected = getExceptionFromClientCreation();
    assertNull(unexpected);
  }

  @Test
  public void testClientWithGdchCredentialWithInvalidAudience_throws() throws IOException {
    settings = settings.toBuilder().setGdchApiAudience("$invalid-audience:").build();
    Exception expected = getExceptionFromClientCreation();
    assertNotNull(expected);
    assertTrue(expected instanceof IllegalArgumentException);
    assertTrue(expected.getMessage().contains("audience string is not a valid URI"));
  }

  @Test
  public void testClientWithNonGdchCredentialWithAnyAudience_throws() throws IOException {
    settings =
        settings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setGdchApiAudience("any-audience")
            .build();
    Exception expected = getExceptionFromClientCreation();
    assertNotNull(expected);
    assertTrue(expected instanceof IllegalArgumentException);
    assertTrue(
        expected.getMessage().contains("audience can only be set when using GdchCredentials"));
  }

  private Exception getExceptionFromClientCreation() {
    try {
      EchoClient.create(settings);
    } catch (Exception ex) {
      return ex;
    }
    return null;
  }
}
