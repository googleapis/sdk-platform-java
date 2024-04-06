package com.google.cloud;

import com.google.cloud.external.DepsDevClient;
import com.google.cloud.model.Advisory;
import com.google.cloud.model.AdvisoryKey;
import com.google.cloud.model.PackageInfo;
import com.google.cloud.model.MavenCoordinate;
import com.google.cloud.model.QueryResult;
import com.google.cloud.model.Version;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class PackageInfoCheck {
  private final DepsDevClient depsDevClient;

  public PackageInfoCheck(DepsDevClient depsDevClient) {
    this.depsDevClient = depsDevClient;
  }

  public List<PackageInfo> check(String groupId, String artifactId, String artifactVersion)
      throws URISyntaxException, IOException, InterruptedException {
    MavenCoordinate initial = new MavenCoordinate(groupId, artifactId, artifactVersion);
    Set<MavenCoordinate> seenCoordinate = new HashSet<>();
    seenCoordinate.add(initial);
    Queue<MavenCoordinate> queue = new ArrayDeque<>();
    queue.offer(initial);
    List<MavenCoordinate> dependencies = new ArrayList<>();
    while (!queue.isEmpty()) {
      MavenCoordinate coordinate = queue.poll();
      dependencies.add(coordinate);
      List<MavenCoordinate> directDependencies = depsDevClient.getDirectDependencies(coordinate);
      // only add unseen dependencies to the queue.
      directDependencies
          .stream()
          .filter(seenCoordinate::add)
          .forEach(queue::offer);
    }

    List<PackageInfo> checkResult = new ArrayList<>();
    for (MavenCoordinate coordinate : dependencies) {
      QueryResult packageInfo = depsDevClient.getQueryResult(coordinate);
      List<String> licenses = new ArrayList<>();
      List<Advisory> advisories = new ArrayList<>();
      for (Version version : packageInfo.getVersions()) {
        licenses.addAll(version.getLicenses());
        for (AdvisoryKey advisoryKey : version.getAdvisoryKeys()) {
          advisories.add(depsDevClient.getAdvisory(advisoryKey.getId()));
        }
      }

      checkResult.add(new PackageInfo(coordinate, licenses, advisories));
    }

    return checkResult;
  }
}
