package com.google.cloud.external;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.cloud.model.MavenCoordinate;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DepsDevClientTest {

  private HttpClient httpClient;
  private HttpResponse<String> response;
  private DepsDevClient client;

  @Before
  public void setUp() {
    httpClient = mock(HttpClient.class);
    client = new DepsDevClient(httpClient, new Gson());
    response = mock(HttpResponse.class);
  }

  @Test
  public void testGetDirectDependenciesOnlyReturnsDirectDeps()
      throws IOException, InterruptedException, URISyntaxException {
    String responseBody = "{\"nodes\":[{\"versionKey\":{\"system\":\"MAVEN\", \"name\":\"com.example:example\", \"version\":\"1.2.3\"}, \"bundled\":false, \"relation\":\"SELF\", \"errors\":[]}, {\"versionKey\":{\"system\":\"MAVEN\", \"name\":\"com.example:indirect-dep\", \"version\":\"4.0.0\"}, \"bundled\":false, \"relation\":\"INDIRECT\", \"errors\":[]}, {\"versionKey\":{\"system\":\"MAVEN\", \"name\":\"com.example:direct-dep\", \"version\":\"1.4.0\"}, \"bundled\":false, \"relation\":\"DIRECT\", \"errors\":[]}], \"edges\":[{\"fromNode\":0, \"toNode\":2, \"requirement\":\"^1.1.0\"}, {\"fromNode\":2, \"toNode\":1, \"requirement\":\"^3.0.0 || ^4.0.0\"}], \"error\":\"\"}";
    when(response.body()).thenReturn(responseBody);
    when(httpClient.send(any(HttpRequest.class), any(BodyHandler.class)))
        .thenReturn(response);
    MavenCoordinate base = new MavenCoordinate("com.example", "example", "1.2.3");
    MavenCoordinate direct = new MavenCoordinate("com.example", "direct-dep", "1.4.0");
    List<MavenCoordinate> coordinates = client.getDirectDependencies(base);
    assertThat(coordinates).containsExactlyElementsIn(List.of(direct));
  }
}
