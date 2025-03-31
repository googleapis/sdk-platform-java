import os
import argparse
import yaml
import difflib
from typing import List, Optional, Dict


def normalize_source(source: str) -> str:
    """Normalizes the source string by replacing v\\d and v.* with v*."""
    source = source.replace("v\\d", "v*")
    source = source.replace("v.*", "v*")
    return source


class DockerConfig:
    def __init__(self, image: str):
        self.image = image

    def __eq__(self, other):
        if not isinstance(other, DockerConfig):
            return False
        return self.image == other.image

    def __str__(self):
        return f"DockerConfig(image={self.image})"


class DeepCopyRegexItem:
    def __init__(self, source: str, dest: str):
        self.source = source
        self.dest = dest

    def __eq__(self, other):
        if not isinstance(other, DeepCopyRegexItem):
            return False
        return (
            normalize_source(self.source) == normalize_source(other.source)
            and self.dest == other.dest
        )

    def __lt__(self, other):
        if not isinstance(other, DeepCopyRegexItem):
            return NotImplemented
        if normalize_source(self.source) < normalize_source(other.source):
            return True
        elif normalize_source(self.source) == normalize_source(other.source):
            return self.dest < other.dest
        else:
            return False

    def __str__(self):
        return f"(source={self.source}, dest={self.dest})"


class OwlBotConfig:
    def __init__(
        self,
        api_name: Optional[str] = None,
        begin_after_commit_hash: Optional[str] = None,
        docker: Optional[DockerConfig] = None,
        squash: Optional[bool] = None,
        deep_copy_regex: Optional[List[DeepCopyRegexItem]] = None,
        deep_remove_regex: Optional[List[str]] = None,
        deep_preserve_regex: Optional[List[str]] = None,
    ):
        self.api_name = api_name
        self.begin_after_commit_hash = begin_after_commit_hash
        self.docker = docker
        self.squash = squash
        self.deep_copy_regex = deep_copy_regex
        self.deep_remove_regex = deep_remove_regex
        self.deep_preserve_regex = deep_preserve_regex

    def __eq__(self, other):
        if not isinstance(other, OwlBotConfig):
            return False

        def compare_lists(list1, list2):
            if list1 is None and list2 is None:
                return True
            if list1 is None or list2 is None:
                return False

            if not list1 or not list2:
                return not list1 and not list2

            return sorted(list1) == sorted(list2)

        return (
            self.api_name == other.api_name
            and self.begin_after_commit_hash == other.begin_after_commit_hash
            and self.docker == other.docker
            and self.squash == other.squash
            and compare_lists(self.deep_copy_regex, other.deep_copy_regex)
            and compare_lists(self.deep_remove_regex, other.deep_remove_regex)
            and compare_lists(self.deep_preserve_regex, other.deep_preserve_regex)
        )

    def __str__(self):
        """String representation for debugging and diff output."""
        return (
            f"OwlBotConfig(\n"
            f"  api_name={self.api_name},\n"
            f"  begin_after_commit_hash={self.begin_after_commit_hash},\n"
            f"  docker={self.docker},\n"
            f"  squash={self.squash},\n"
            f"  deep_copy_regex={self.deep_copy_regex},\n"
            f"  deep_remove_regex={self.deep_remove_regex},\n"
            f"  deep_preserve_regex={self.deep_preserve_regex}\n"
            f")"
        )


def read_yaml_as_object(file_path: str) -> Optional[OwlBotConfig]:
    """Reads a YAML file, removes comment lines, and returns an OwlBotConfig object."""
    try:
        with open(file_path, "r") as file:
            lines = file.readlines()
        non_comment_lines = [line for line in lines if not line.strip().startswith("#")]
        yaml_string = "".join(non_comment_lines)
        data = yaml.safe_load(yaml_string)

        if not data:
            return None

        docker_data = data.get("docker")
        docker = DockerConfig(image=docker_data["image"]) if docker_data else None

        deep_copy_regex_data = data.get("deep-copy-regex")
        deep_copy_regex = (
            [
                DeepCopyRegexItem(source=item["source"], dest=item["dest"])
                for item in deep_copy_regex_data
            ]
            if deep_copy_regex_data
            else None
        )

        return OwlBotConfig(
            api_name=data.get("api-name"),
            begin_after_commit_hash=data.get("begin-after-commit-hash"),
            docker=docker,
            squash=data.get("squash"),
            deep_copy_regex=deep_copy_regex,
            deep_remove_regex=data.get("deep-remove-regex"),
            deep_preserve_regex=data.get("deep-preserve-regex"),
        )
    except FileNotFoundError:
        print(f"Error: File not found: {file_path}")
        return None
    except yaml.YAMLError as e:
        print(f"Error parsing YAML: {e}")
        return None
    except Exception as e:
        print(f"Error creating object: {e}")
        return None


def generate_diff(config1: OwlBotConfig, config2: OwlBotConfig) -> List[str]:
    """Generates a list of strings representing the differences between two OwlBotConfig objects."""
    diff_lines = []
    if config1.api_name != config2.api_name:
        diff_lines.append(f"- api_name: {config1.api_name}")
        diff_lines.append(f"+ api_name: {config2.api_name}")
    if config1.begin_after_commit_hash != config2.begin_after_commit_hash:
        diff_lines.append(
            f"- begin_after_commit_hash: {config1.begin_after_commit_hash}"
        )
        diff_lines.append(
            f"+ begin_after_commit_hash: {config2.begin_after_commit_hash}"
        )
    if config1.docker != config2.docker:
        diff_lines.append(f"- docker: {config1.docker}")
        diff_lines.append(f"+ docker: {config2.docker}")
    if config1.squash != config2.squash:
        diff_lines.append(f"- squash: {config1.squash}")
        diff_lines.append(f"+ squash: {config2.squash}")

    def diff_lists(key, list1, list2):
        if list1 is None and list2 is None:
            return
        if list1 is None or list2 is None:
            diff_lines.append(f"- {key}: {list1}")
            diff_lines.append(f"+ {key}: {list2}")
            return
        sorted_list1 = sorted(list1)
        sorted_list2 = sorted(list2)
        for item in sorted_list1:
            if item not in sorted_list2:
                diff_lines.append(f"- {key}: {item}")
        for item in sorted_list2:
            if item not in sorted_list1:
                diff_lines.append(f"+ {key}: {item}")

    diff_lists("deep_copy_regex", config1.deep_copy_regex, config2.deep_copy_regex)
    diff_lists(
        "deep_remove_regex", config1.deep_remove_regex, config2.deep_remove_regex
    )
    diff_lists(
        "deep_preserve_regex", config1.deep_preserve_regex, config2.deep_preserve_regex
    )

    return diff_lines


def compare_owlbot_yaml_files(
    input_dir: str, original_dir: str, output_diff: bool = False
):
    """
    Compares .Owlbot-hermetic.yaml files (case-insensitive) in 'input' and 'original' directories,
    using YAML parsing for comparison.

    Args:
        input_dir: Path to the 'input' directory.
        original_dir: Path to the 'original' directory.
        output_diff: If True, output file differences.
    """

    input_dir = os.path.abspath(input_dir)
    original_dir = os.path.abspath(original_dir)

    diff_files = []
    total_files = 0

    for root, _, files in os.walk(input_dir):
        for file in files:
            if file.lower() == ".owlbot-hermetic.yaml":
                total_files += 1
                input_file_path = os.path.join(root, file)
                relative_path = os.path.relpath(input_file_path, input_dir)
                original_file_path = os.path.join(original_dir, relative_path)

                if os.path.exists(original_file_path):
                    config1 = read_yaml_as_object(input_file_path)
                    config2 = read_yaml_as_object(original_file_path)

                    if config1 is not None and config2 is not None:
                        # Use the __eq__ method for comparison
                        if config1 != config2:
                            diff_files.append(relative_path)
                            if output_diff:
                                print(f"\nYAML Differences in: {relative_path}")
                                diff_output = generate_diff(config1, config2)
                                for line in diff_output:
                                    print(line)

                    else:
                        print(
                            f"Warning: Could not compare YAML files: {input_file_path} or {original_file_path}"
                        )

                else:
                    print(
                        f"Warning: Corresponding original file not found: {original_file_path}"
                    )

    if diff_files:
        print("Files with YAML differences (relative to 'input'):")
        for file_path in diff_files:
            print(file_path)
        print(f"\nTotal YAML differences found: {len(diff_files)}")
    else:
        print("No YAML differences found.")

    print(f"\nTotal files compared: {total_files}")


def main():
    """
    Main function to parse command-line arguments and run the comparison.
    """
    parser = argparse.ArgumentParser(
        description="Compare .Owlbot-hermetic.yaml files in two directories."
    )
    parser.add_argument("input_dir", help="Path to the 'input' directory.")
    parser.add_argument("original_dir", help="Path to the 'original' directory.")
    parser.add_argument(
        "-d", "--diff", action="store_true", help="Output file differences."
    )

    args = parser.parse_args()

    compare_owlbot_yaml_files(args.input_dir, args.original_dir, args.diff)


if __name__ == "__main__":
    main()
