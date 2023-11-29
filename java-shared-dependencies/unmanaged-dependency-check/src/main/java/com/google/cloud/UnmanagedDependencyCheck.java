package com.google.cloud;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.cloud.tools.opensource.dependencies.Bom;
import com.google.cloud.tools.opensource.dependencies.MavenRepositoryException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.aether.resolution.ArtifactDescriptorException;

public class UnmanagedDependencyCheck {

  public static void main(String[] args)
      throws MavenRepositoryException, ArtifactDescriptorException {
    checkArgument(args.length == 2, "The length of the inputs should be 2");
    System.out.println(getUnmanagedDependencies(args[0], args[1]));
  }

  /**
   * Returns dependency coordinates that are not managed by shared dependency BOM.
   *
   * @param sharedDependencyVersion the version of shared dependency BOM
   * @param projectBomPath the path of current project BOM
   * @return a list of unmanaged dependencies by the given version of shared dependency BOM
   * @throws ArtifactDescriptorException
   * @throws MavenRepositoryException
   */
  public static List<String> getUnmanagedDependencies(
      String sharedDependencyVersion, String projectBomPath)
      throws ArtifactDescriptorException, MavenRepositoryException {
    Set<String> sharedDependencies = getSharedDependencies(sharedDependencyVersion);
    Set<String> managedDependencies = getManagedDependencies(projectBomPath);

    return managedDependencies.stream()
        .filter(dependency -> !sharedDependencies.contains(dependency))
        .collect(Collectors.toList());
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

  private UnmanagedDependencyCheck() {
    throw new IllegalStateException("Utility class");
  }
}
