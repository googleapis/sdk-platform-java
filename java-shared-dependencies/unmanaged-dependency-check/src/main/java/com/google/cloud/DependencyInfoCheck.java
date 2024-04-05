package com.google.cloud;

import com.google.cloud.external.DepsDevClient;
import com.google.cloud.model.MavenCoordinate;
import com.google.cloud.model.PackageInfo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class DependencyInfoCheck {
  private final DepsDevClient depsDevClient;

  public DependencyInfoCheck(DepsDevClient depsDevClient) {
    this.depsDevClient = depsDevClient;
  }

  public Map<String, List<String>> infoCheck(String groupId, String artifactId, String version)
      throws URISyntaxException, IOException, InterruptedException {
    MavenCoordinate initial = new MavenCoordinate(groupId, artifactId, version);
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

    Map<String, List<String>> res = new HashMap<>();
    for (MavenCoordinate coordinate : dependencies) {
      PackageInfo packageInfo = depsDevClient.getPackageInfo(coordinate);
      List<String> licenses = packageInfo
          .getVersions()
          .stream()
          .flatMap(v -> v.getLicenses().stream())
          .collect(Collectors.toList());
      res.put(coordinate.toString(), licenses);
    }

    return res;
  }
}
