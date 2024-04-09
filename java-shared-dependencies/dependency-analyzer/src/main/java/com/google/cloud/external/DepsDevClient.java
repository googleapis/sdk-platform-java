package com.google.cloud.external;

import com.google.cloud.model.Advisory;
import com.google.cloud.model.DependencyResponse;
import com.google.cloud.model.Node;
import com.google.cloud.model.QueryResult;
import com.google.cloud.model.Relation;
import com.google.cloud.model.VersionKey;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.stream.Collectors;

public class DepsDevClient {
  private final HttpClient client;
  public final Gson gson;
  private final static String advisoryUrlBase = "https://api.deps.dev/v3/advisories/%s";
  private final static String queryUrlBase = "https://api.deps.dev/v3/query?versionKey.system=%s&versionKey.name=%s&versionKey.version=%s";
  private final static String dependencyUrlBase = "https://api.deps.dev/v3/systems/%s/packages/%s/versions/%s:dependencies";

  public DepsDevClient(HttpClient client,  Gson gson) {
    this.client = client;
    this.gson = gson;
  }

  public List<VersionKey> getDirectDependencies(VersionKey versionKey)
      throws URISyntaxException, IOException, InterruptedException {
    HttpResponse<String> response = getResponse(
        getDependencyUrl(
            versionKey.getSystem().toString(),
            versionKey.getName(),
            versionKey.getVersion()
        )
    );
    DependencyResponse dependencyResponse = gson.fromJson(response.body(),
        DependencyResponse.class);
    return dependencyResponse
        .getNodes()
        .stream()
        .filter(node -> Relation.DIRECT.equals(node.getRelation()))
        .map(Node::getVersionKey)
        .collect(Collectors.toList());
  }

  public QueryResult getQueryResult(VersionKey versionKey)
      throws URISyntaxException, IOException, InterruptedException {
    HttpResponse<String> response = getResponse(
        getQueryUrl(
            versionKey.getSystem().toString(),
            versionKey.getName(),
            versionKey.getVersion()
        )
    );
    return gson.fromJson(response.body(), QueryResult.class);
  }

  public Advisory getAdvisory(String advisoryId)
      throws URISyntaxException, IOException, InterruptedException {
    HttpResponse<String> response = getResponse(getAdvisoryUrl(advisoryId));
    return gson.fromJson(response.body(), Advisory.class);
  }

  private String getAdvisoryUrl(String advisoryId) {
    return String.format(advisoryUrlBase, advisoryId);
  }

  private String getQueryUrl(String system, String name, String version) {
    return String.format(queryUrlBase, system, name, version);
  }

  private String getDependencyUrl(String system, String name, String version) {
    return String.format(dependencyUrlBase, system, name, version);
  }

  private HttpResponse<String> getResponse(String endpoint)
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().uri(new URI(endpoint)).GET().build();
    return client.send(request, BodyHandlers.ofString());
  }
}
