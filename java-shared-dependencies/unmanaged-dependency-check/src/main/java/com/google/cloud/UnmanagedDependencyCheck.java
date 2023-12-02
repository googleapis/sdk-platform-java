package com.google.cloud;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.cloud.tools.opensource.classpath.ClassPathBuilder;
import com.google.cloud.tools.opensource.classpath.DependencyMediation;
import com.google.cloud.tools.opensource.dependencies.Bom;
import com.google.cloud.tools.opensource.dependencies.MavenRepositoryException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.eclipse.aether.version.InvalidVersionSpecificationException;

public class UnmanagedDependencyCheck {

  public static void main(String[] args)
      throws MavenRepositoryException, ArtifactDescriptorException, InvalidVersionSpecificationException {
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
      throws ArtifactDescriptorException, MavenRepositoryException, InvalidVersionSpecificationException {
    Set<String> sharedDependencies = getSharedDependencies(sharedDependencyVersion);
    Set<String> managedDependencies = getManagedDependencies(projectBomPath);

    return managedDependencies.stream()
        .filter(dependency -> !sharedDependencies.contains(dependency))
        .collect(Collectors.toList());
  }

  private static Set<String> getSharedDependencies(String sharedDependencyVersion)
      throws ArtifactDescriptorException, InvalidVersionSpecificationException {
    return getManagedDependenciesFromBom(
        Bom.readBom(
            String.format(
                "com.google.cloud:google-cloud-shared-dependencies:%s", sharedDependencyVersion)));
  }

  private static Set<String> getManagedDependencies(String projectBomPath)
      throws MavenRepositoryException, InvalidVersionSpecificationException {
    return getManagedDependenciesFromBom(Bom.readBom(Paths.get(projectBomPath)));
  }

  private static Set<String> getManagedDependenciesFromBom(Bom bom)
      throws InvalidVersionSpecificationException {
    Set<String> res = new HashSet<>();
    new ClassPathBuilder()
        .resolve(bom.getManagedDependencies(), true, DependencyMediation.MAVEN)
        .getClassPath()
        .forEach(
          classPath -> {
            String coordinate = classPath.toString();
            // ignore the version.
            int index = coordinate.lastIndexOf(":");
            res.add(coordinate.substring(0, index));
          });

    return res;
  }

  private UnmanagedDependencyCheck() {
    throw new IllegalStateException("Utility class");
  }
}
