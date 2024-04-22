package com.google.cloud.external;

import com.google.cloud.model.Advisory;
import com.google.cloud.model.DependencyResponse;
import com.google.cloud.model.Node;
import com.google.cloud.model.QueryResult;
import com.google.cloud.model.Relation;
import com.google.cloud.model.VersionKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.lang.reflect.Type;
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

  public DepsDevClient(HttpClient client) {
    this.client = client;
    this.gson = new GsonBuilder()
        .registerTypeAdapter(VersionKey.class, new VersionKeyDeserializer())
        .create();
  }

  public List<VersionKey> getDirectDependencies(VersionKey versionKey)
      throws URISyntaxException, IOException, InterruptedException {
    HttpResponse<String> response = getResponse(
        getDependencyUrl(
            versionKey.pkgManagement().toString(),
            versionKey.name(),
            versionKey.version()
        )
    );
    DependencyResponse dependencyResponse = gson.fromJson(response.body(),
        DependencyResponse.class);
    return dependencyResponse
        .nodes()
        .stream()
        .filter(node -> Relation.DIRECT.equals(node.relation()))
        .map(Node::versionKey)
        .collect(Collectors.toList());
  }

  public QueryResult getQueryResult(VersionKey versionKey)
      throws URISyntaxException, IOException, InterruptedException {
    HttpResponse<String> response = getResponse(
        getQueryUrl(
            versionKey.pkgManagement().toString(),
            versionKey.name(),
            versionKey.version()
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

  static class VersionKeyDeserializer implements JsonDeserializer<VersionKey> {

    @Override
    public VersionKey deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      String system = context.deserialize(json.getAsJsonObject().get("system"), String.class);
      String name = context.deserialize(json.getAsJsonObject().get("name"), String.class);
      String version = context.deserialize(json.getAsJsonObject().get("version"), String.class);
      return VersionKey.from(system, name, version);
    }
  }
}
