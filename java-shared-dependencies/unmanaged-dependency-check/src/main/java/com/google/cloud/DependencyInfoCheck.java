package com.google.cloud;

import com.google.cloud.model.PackageInfo;
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

public class DependencyInfoCheck {
  private final static HttpClient client = HttpClient.newHttpClient();
  public final static Gson gson = new Gson();

  private DependencyInfoCheck() {

  }
  public static List<String> licenseCheck(String groupId, String artifactId, String version)
      throws URISyntaxException, IOException, InterruptedException {

    String urlBase = "https://api.deps.dev/v3alpha/query?versionKey.system=maven&versionKey.name=";
    HttpRequest request = HttpRequest.newBuilder().uri(
        new URI(String.format("%s%s:%s&versionKey.version=%s", urlBase, groupId, artifactId, version))
    ).GET().build();
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    PackageInfo packageInfo = gson.fromJson(response.body(), PackageInfo.class);
    return packageInfo.getVersions().stream().flatMap(v -> v.getLicenses().stream()).collect(
        Collectors.toList());
  }

  private static List<String> getDependencies(String groupId, String artifactId, String version)
      throws URISyntaxException, IOException, InterruptedException {
    String urlBase = "https://api.deps.dev/v3alpha/systems/maven/packages";
    HttpRequest request = HttpRequest.newBuilder().uri(
        new URI(String.format("%s/%s:%s/versions/%s:dependencies", urlBase, groupId, artifactId, version))
    ).GET().build();
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    return null;
  }

  public static void main(String[] args)
      throws URISyntaxException, IOException, InterruptedException {
    System.out.println(licenseCheck("io.opentelemetry", "opentelemetry-api", "1.35.0"));
    System.out.println(getDependencies("io.opentelemetry", "opentelemetry-api", "1.35.0"));
  }
}
