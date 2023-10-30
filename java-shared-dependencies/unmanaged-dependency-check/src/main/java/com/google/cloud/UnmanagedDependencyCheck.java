package com.google.cloud;

import com.google.cloud.tools.opensource.dependencies.Bom;
import com.google.cloud.tools.opensource.dependencies.MavenRepositoryException;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.aether.resolution.ArtifactDescriptorException;

public class UnmanagedDependencyCheck {

  /**
   * Returns dependency coordinates that are not managed by shared dependency BOM.
   * @param sharedDependencyVersion
   * @param projectBomPath
   * @return
   * @throws ArtifactDescriptorException
   * @throws MavenRepositoryException
   */
  public static String outputUnmanagedDependencies(
      String sharedDependencyVersion, String projectBomPath)
      throws ArtifactDescriptorException, MavenRepositoryException {
    Set<String> sharedDependencies = getSharedDependencies(sharedDependencyVersion);
    Set<String> managedDependencies = getManagedDependencies(projectBomPath);

    return managedDependencies.stream()
        .filter(dependency -> !sharedDependencies.contains(dependency))
        .collect(Collectors.joining());
  }

  private static Set<String> getSharedDependencies(String sharedDependencyVersion)
      throws ArtifactDescriptorException {
    return getManagedDependenciesFromBom(
        Bom.readBom(
            String.format(
                "com.google.cloud:google-cloud-shared-dependencies:%s", sharedDependencyVersion)));
  }

  private static Set<String> getManagedDependencies(String projectBomPath)
      throws MavenRepositoryException {
    return getManagedDependenciesFromBom(Bom.readBom(Paths.get(projectBomPath)));
  }

  private static Set<String> getManagedDependenciesFromBom(Bom bom) {
    return bom.getManagedDependencies().stream()
        .filter(artifact -> !artifact.getClassifier().equals("tests"))
        .map(artifact -> String.format("%s:%s", artifact.getGroupId(), artifact.getArtifactId()))
        .collect(Collectors.toSet());
  }
}
