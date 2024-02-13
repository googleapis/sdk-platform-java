package com.google.cloud;

import com.google.cloud.model.DependencyResponse;
import com.google.cloud.model.MavenCoordinate;
import com.google.cloud.model.PackageInfo;
import com.google.cloud.model.Relation;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class DependencyInfoCheck {

  private final static HttpClient client = HttpClient.newHttpClient();
  public final static Gson gson = new Gson();

  private final static String packageInfoUrlBase = "https://api.deps.dev/v3alpha/query?versionKey.system=maven&versionKey.name=%s:%s&versionKey.version=%s";

  private final static String dependencyUrlBase = "https://api.deps.dev/v3alpha/systems/maven/packages/%s:%s/versions/%s:dependencies";

  private DependencyInfoCheck() {

  }

  public static Map<String, List<String>> infoCheck(String groupId, String artifactId, String version)
      throws URISyntaxException, IOException, InterruptedException {
    MavenCoordinate mavenCoordinate = new MavenCoordinate(groupId, artifactId, version);
    Queue<MavenCoordinate> queue = new ArrayDeque<>();
    queue.offer(mavenCoordinate);
    List<MavenCoordinate> dependencies = new ArrayList<>();
    while (!queue.isEmpty()) {
      MavenCoordinate coordinate = queue.poll();
      dependencies.add(coordinate);
      List<MavenCoordinate> directDependencies = getDirectDependencies(coordinate);
      directDependencies.forEach(queue::offer);
    }

    Map<String, List<String>> res = new HashMap<>();
    for (MavenCoordinate coordinate : dependencies) {
      PackageInfo packageInfo = getPackageInfo(coordinate);
      List<String> licenses = packageInfo
          .getVersions()
          .stream()
          .flatMap(v -> v.getLicenses().stream())
          .collect(Collectors.toList());
      res.put(coordinate.toString(), licenses);
    }

    return res;
  }

  private static List<MavenCoordinate> getDirectDependencies(MavenCoordinate mavenCoordinate)
      throws URISyntaxException, IOException, InterruptedException {
    HttpResponse<String> response = getResponse(
        getDependencyUrl(
            mavenCoordinate.getGroupId(),
            mavenCoordinate.getArtifactId(),
            mavenCoordinate.getVersion()
        )
    );
    DependencyResponse dependencyResponse = gson.fromJson(response.body(),
        DependencyResponse.class);
    return dependencyResponse
        .getNodes()
        .stream()
        .filter(node -> node.getRelation().equals(Relation.DIRECT))
        .map(node -> node.getVersionKey().toMavenCoordinate())
        .collect(Collectors.toList());
  }

  private static PackageInfo getPackageInfo(MavenCoordinate mavenCoordinate)
      throws URISyntaxException, IOException, InterruptedException {
    HttpResponse<String> response = getResponse(
        getPackageInfoUrl(
            mavenCoordinate.getGroupId(),
            mavenCoordinate.getArtifactId(),
            mavenCoordinate.getVersion()
        )
    );
    return gson.fromJson(response.body(), PackageInfo.class);
  }

  private static String getPackageInfoUrl(String groupId, String artifactId, String version) {
    return String.format(packageInfoUrlBase, groupId, artifactId, version);
  }

  private static String getDependencyUrl(String groupId, String artifactId, String version) {
    return String.format(dependencyUrlBase, groupId, artifactId, version);
  }

  private static HttpResponse<String> getResponse(String endpoint)
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder().uri(new URI(endpoint)).GET().build();
    return client.send(request, BodyHandlers.ofString());
  }

  public static void main(String[] args)
      throws URISyntaxException, IOException, InterruptedException {
    System.out.println(infoCheck("io.opentelemetry", "opentelemetry-api", "1.35.0"));
  }
}
