import os
import argparse
import xml.etree.ElementTree as ET
import difflib
from typing import List, Optional, Dict

# Constants for POM namespace
POM_NS = {"": "http://maven.apache.org/POM/4.0.0"}

# --- Helper Functions ---


def get_text(element: Optional[ET.Element], tag: str) -> Optional[str]:
    """Safely extracts text from an element, considering namespaces."""
    if element is None:
        return None
    elem = element.find(tag, namespaces=POM_NS)
    return elem.text if elem is not None else None


def get_properties(properties_elem: Optional[ET.Element]) -> Optional[Dict[str, str]]:
    """Extracts properties from a 'properties' element."""
    if properties_elem is None:
        return None
    return {
        prop.tag.split("}", 1)[1] if "}" in prop.tag else prop.tag: prop.text.strip()
        for prop in properties_elem
        if prop.text
    }


def get_dependencies(
    dependencies_elem: Optional[ET.Element],
) -> Optional[List["Dependency"]]:
    """Extracts dependencies from a 'dependencies' element."""
    if dependencies_elem is None:
        return None
    return [
        Dependency(
            groupId=get_text(dep, "groupId"),
            artifactId=get_text(dep, "artifactId"),
            version=get_text(dep, "version"),
            scope=get_text(dep, "scope"),
        )
        for dep in dependencies_elem.findall("dependency", namespaces=POM_NS)
        if get_text(dep, "groupId")
        and get_text(dep, "artifactId")
        and get_text(dep, "version")
    ]


def compare_elements(elem1: Optional[ET.Element], elem2: Optional[ET.Element]) -> bool:
    """Compares two XML elements, ignoring order of children."""
    if elem1 is None and elem2 is None:
        return True
    if elem1 is None or elem2 is None:
        return False
    return (
        elem1.tag == elem2.tag
        and elem1.attrib == elem2.attrib
        and (elem1.text or "").strip() == (elem2.text or "").strip()
        and all(
            compare_elements(child1, child2)
            for child1, child2 in zip(
                sorted(elem1, key=ET.tostring), sorted(elem2, key=ET.tostring)
            )
        )
    )


def compare_element_lists(
    list1: Optional[List[ET.Element]], list2: Optional[List[ET.Element]]
) -> bool:
    """Compares two lists of XML elements."""
    if list1 is None and list2 is None:
        return True
    if list1 is None or list2 is None:
        return False
    return len(list1) == len(list2) and all(
        compare_elements(p1, p2)
        for p1, p2 in zip(
            sorted(list1, key=ET.tostring), sorted(list2, key=ET.tostring)
        )
    )


# --- Model Classes ---


class Dependency:
    def __init__(
        self,
        groupId: Optional[str],
        artifactId: Optional[str],
        version: Optional[str],
        scope: Optional[str] = None,
    ):
        self.groupId = groupId
        self.artifactId = artifactId
        self.version = version
        self.scope = scope

    def __eq__(self, other):
        if not isinstance(other, Dependency):
            return False
        return (
            self.groupId == other.groupId
            and self.artifactId == other.artifactId
            and self.version == other.version
            and self.scope == other.scope
        )

    def __lt__(self, other):
        if not isinstance(other, Dependency):
            return NotImplemented
        return (self.groupId, self.artifactId, self.version, self.scope) < (
            other.groupId,
            other.artifactId,
            other.version,
            other.scope,
        )

    def __str__(self):
        return f"{self.groupId}:{self.artifactId}:{self.version}:{self.scope}"


class POM:
    def __init__(
        self,
        modelVersion: Optional[str] = None,
        groupId: Optional[str] = None,
        artifactId: Optional[str] = None,
        version: Optional[str] = None,
        properties: Optional[Dict[str, str]] = None,
        dependencies: Optional[List[Dependency]] = None,
        build: Optional[ET.Element] = None,
        profiles: Optional[List[ET.Element]] = None,
        repositories: Optional[List[ET.Element]] = None,
        pluginRepositories: Optional[List[ET.Element]] = None,
        dependencyManagement: Optional[ET.Element] = None,
    ):
        self.modelVersion = modelVersion
        self.groupId = groupId
        self.artifactId = artifactId
        self.version = version
        self.properties = properties
        self.dependencies = dependencies
        self.build = build
        self.profiles = profiles
        self.repositories = repositories
        self.pluginRepositories = pluginRepositories
        self.dependencyManagement = dependencyManagement

    def __eq__(self, other):
        if not isinstance(other, POM):
            return False

        def compare_elements(
            elem1: Optional[ET.Element], elem2: Optional[ET.Element]
        ) -> bool:
            if elem1 is None and elem2 is None:
                return True
            if elem1 is None or elem2 is None:
                return False
            return ET.tostring(elem1, encoding="unicode", method="xml") == ET.tostring(
                elem2, encoding="unicode", method="xml"
            )

        def compare_dicts(
            dict1: Optional[Dict[str, str]], dict2: Optional[Dict[str, str]]
        ) -> bool:
            if dict1 is None and dict2 is None:
                return True
            if dict1 is None or dict2 is None:
                return False
            return dict1 == dict2

        def compare_dependency_lists(
            list1: Optional[List[Dependency]], list2: Optional[List[Dependency]]
        ) -> bool:
            if list1 is None and list2 is None:
                return True
            if list1 is None or list2 is None:
                return False
            return sorted(list1) == sorted(list2)

        def compare_dependency_management(
            dm1: Optional[ET.Element], dm2: Optional[ET.Element]
        ) -> bool:
            if dm1 is None and dm2 is None:
                return True
            if dm1 is None or dm2 is None:
                return False

            if ET.tostring(dm1, encoding="unicode", method="xml") != ET.tostring(
                dm2, encoding="unicode", method="xml"
            ):
                return False

            deps1 = get_dependencies(dm1)
            deps2 = get_dependencies(dm2)
            return compare_dependency_lists(deps1, deps2)

        return (
            self.modelVersion == other.modelVersion
            and self.groupId == other.groupId
            and self.artifactId == other.artifactId
            and self.version == other.version
            and compare_dicts(self.properties, other.properties)
            and compare_dependency_lists(self.dependencies, other.dependencies)
            and compare_elements(self.build, other.build)
            and compare_element_lists(self.profiles, other.profiles)
            and compare_element_lists(self.repositories, other.repositories)
            and compare_element_lists(self.pluginRepositories, other.pluginRepositories)
            and compare_dependency_management(
                self.dependencyManagement, other.dependencyManagement
            )
        )

    def __str__(self):
        return (
            f"POM(\n"
            f"  modelVersion={self.modelVersion},\n"
            f"  groupId={self.groupId},\n"
            f"  artifactId={self.artifactId},\n"
            f"  version={self.version},\n"
            f"  properties={self.properties},\n"
            f"  dependencies={self.dependencies},\n"
            f"  build={self.build},\n"
            f"  profiles={self.profiles},\n"
            f"  repositories={self.repositories},\n"
            f"  pluginRepositories={self.pluginRepositories},\n"
            f"  dependencyManagement={self.dependencyManagement}\n"
            f")"
        )


# --- POM Reading ---


def read_pom_as_object(file_path: str) -> Optional[POM]:
    """Reads a pom.xml file and returns a POM object."""
    try:
        tree = ET.parse(file_path)
        root = tree.getroot()

        return POM(
            modelVersion=get_text(root, "modelVersion"),
            groupId=get_text(root, "groupId"),
            artifactId=get_text(root, "artifactId"),
            version=get_text(root, "version"),
            properties=get_properties(root.find("properties", namespaces=POM_NS)),
            dependencies=get_dependencies(root.find("dependencies", namespaces=POM_NS)),
            build=root.find("build", namespaces=POM_NS),
            profiles=root.findall("profiles", namespaces=POM_NS),
            repositories=root.findall("repositories", namespaces=POM_NS),
            pluginRepositories=root.findall("pluginRepositories", namespaces=POM_NS),
            dependencyManagement=root.find("dependencyManagement", namespaces=POM_NS),
        )

    except FileNotFoundError:
        print(f"Error: File not found: {file_path}")
        return None
    except ET.ParseError as e:
        print(f"Error parsing XML: {e}")
        return None
    except Exception as e:
        print(f"Error creating object: {e}")
        return None


# --- Diff Generation ---


def generate_pom_diff(config1: POM, config2: POM) -> List[str]:
    """Generates a list of strings representing the differences between two POM objects."""
    diff_lines = []

    def diff_field(key: str, value1, value2):
        if value1 != value2:
            if isinstance(value1, list):
                diff_lines.append(f"- {key}: {[str(item) for item in value1]}")
                diff_lines.append(f"+ {key}: {[str(item) for item in value2]}")
            elif isinstance(value1, ET.Element):
                diff_lines.append(
                    f"- {key}: {ET.tostring(value1, encoding='unicode', method='xml')}"
                )
                diff_lines.append(
                    f"+ {key}: {ET.tostring(value2, encoding='unicode', method='xml')}"
                )
            else:
                diff_lines.append(f"- {key}: {str(value1)}")
                diff_lines.append(f"+ {key}: {str(value2)}")

    diff_field("modelVersion", config1.modelVersion, config2.modelVersion)
    diff_field("groupId", config1.groupId, config2.groupId)
    diff_field("artifactId", config1.artifactId, config2.artifactId)
    diff_field("version", config1.version, config2.version)
    diff_field("properties", config1.properties, config2.properties)
    diff_field("dependencies", config1.dependencies, config2.dependencies)
    diff_field("build", config1.build, config2.build)
    diff_field("profiles", config1.profiles, config2.profiles)
    diff_field("repositories", config1.repositories, config2.repositories)
    diff_field(
        "pluginRepositories", config1.pluginRepositories, config2.pluginRepositories
    )

    # --- Diff for dependencyManagement ---
    if (
        config1.dependencyManagement is not None
        or config2.dependencyManagement is not None
    ):
        if config1.dependencyManagement is None or config2.dependencyManagement is None:
            diff_lines.append(f"- dependencyManagement: {config1.dependencyManagement}")
            diff_lines.append(f"+ dependencyManagement: {config2.dependencyManagement}")
        else:
            deps1 = get_dependencies(
                config1.dependencyManagement.find("dependencies", namespaces=POM_NS)
            )
            deps2 = get_dependencies(
                config2.dependencyManagement.find("dependencies", namespaces=POM_NS)
            )
            diff_field("dependencyManagement/dependencies", deps1, deps2)

    return diff_lines


# --- Main Comparison Logic ---


def compare_pom_files_in_dirs(
    input_dir: str, original_dir: str, output_diff: bool = False
):
    """Compares pom.xml files in 'input' and 'original' directories."""

    input_dir = os.path.abspath(input_dir)
    original_dir = os.path.abspath(original_dir)

    diff_files = []
    total_files = 0
    different_files = 0

    for root, _, files in os.walk(input_dir):
        for file in files:
            if file.lower() == "pom.xml":
                total_files += 1
                input_file_path = os.path.join(root, file)
                relative_path = os.path.relpath(input_file_path, input_dir)
                original_file_path = os.path.join(original_dir, relative_path)

                if os.path.exists(original_file_path):
                    pom1 = read_pom_as_object(input_file_path)
                    pom2 = read_pom_as_object(original_file_path)

                    if pom1 is not None and pom2 is not None:
                        if pom1 != pom2:
                            different_files += 1
                            diff_files.append(relative_path)
                            if output_diff:
                                print(f"\nPOM Differences in: {relative_path}")
                                diff_output = generate_pom_diff(pom1, pom2)
                                for line in diff_output:
                                    print(line)

                    else:
                        print(
                            f"Warning: Could not compare POM files: {input_file_path} or {original_file_path}"
                        )

                else:
                    print(
                        f"Warning: Corresponding original file not found: {original_file_path}"
                    )

    if diff_files:
        print("\nFiles with POM differences (relative to 'input'):")
        for file_path in diff_files:
            print(file_path)
    else:
        print("No POM differences found.")

    print(f"\nTotal files compared: {total_files}")
    print(f"\nTotal different files found: {different_files}")


# --- Command Line Handling ---


def main():
    """Main function to parse command-line arguments."""
    parser = argparse.ArgumentParser(
        description="Compare pom.xml files in two directories."
    )
    parser.add_argument("input_dir", help="Path to the 'input' directory.")
    parser.add_argument("original_dir", help="Path to the 'original' directory.")
    parser.add_argument(
        "-d", "--diff", action="store_true", help="Output file differences."
    )
    args = parser.parse_args()
    compare_pom_files_in_dirs(args.input_dir, args.original_dir, args.diff)


if __name__ == "__main__":
    main()
