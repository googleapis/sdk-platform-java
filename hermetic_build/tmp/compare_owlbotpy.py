import os
import argparse
import difflib
import re
from typing import List


def normalize_python_line(line: str) -> str:
    """
    Normalizes a line of Python code by removing whitespace, comments, and
    handling newline characters.
    """
    line = line.strip()
    line = re.sub(r"[ \t]+", " ", line)  # Normalize spaces and tabs
    line = re.sub(r"#.*", "", line)  # Remove comments
    line = line.replace("\r\n", "\n").replace("\r", "\n")  # Normalize newlines
    line = line.rstrip("\n")  # Remove trailing newline
    return line


def compare_python_files_hybrid(file1_path: str, file2_path: str) -> List[str]:
    """
    Compares two Python files, ignoring whitespace, comments, and trailing newlines.
    Returns a list of diff lines.
    """
    try:
        with open(file1_path, "r") as file1, open(file2_path, "r") as file2:
            lines1 = file1.readlines()
            lines2 = file2.readlines()

        normalized_lines1 = [normalize_python_line(line) for line in lines1]
        normalized_lines2 = [normalize_python_line(line) for line in lines2]

        normalized_lines1 = [
            line for line in normalized_lines1 if line
        ]  # Remove empty lines
        normalized_lines2 = [
            line for line in normalized_lines2 if line
        ]  # Remove empty lines

        diff = list(
            difflib.ndiff(normalized_lines1, normalized_lines2)
        )  # Convert to list immediately

        return diff

    except FileNotFoundError:
        print(f"Error: One or both files not found.")
        return []
    except Exception as e:
        print(f"An error occurred: {e}")
        return []


def compare_python_files_in_dirs(
    input_dir: str, original_dir: str, output_diff: bool = False
):
    """
    Compares Python files (owlbot.py) in 'input' and 'original' directories.

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
            if file.lower() == "owlbot.py":
                total_files += 1
                input_file_path = os.path.join(root, file)
                relative_path = os.path.relpath(input_file_path, input_dir)
                original_file_path = os.path.join(original_dir, relative_path)

                if os.path.exists(original_file_path):
                    diff_output = compare_python_files_hybrid(
                        input_file_path, original_file_path
                    )

                    if any(
                        line.startswith("+ ")
                        or line.startswith("- ")
                        or line.startswith("-? ")
                        or line.startswith("+? ")
                        for line in diff_output
                    ):
                        diff_files.append(relative_path)
                        if output_diff:
                            print(f"\nPython Differences in: {relative_path}")
                            for line in diff_output:
                                if (
                                    line.startswith("+ ")
                                    or line.startswith("- ")
                                    or line.startswith("-? ")
                                    or line.startswith("+? ")
                                ):
                                    print(line)

                else:
                    print(
                        f"Warning: Corresponding original file not found: {original_file_path}"
                    )

    if diff_files:
        print("Files with Python differences (relative to 'input'):")
        for file_path in diff_files:
            print(file_path)
        print(f"\nTotal Python differences found: {len(diff_files)}")
    else:
        print("No Python differences found.")

    print(f"\nTotal files compared: {total_files}")


def main():
    """
    Main function to parse command-line arguments and run the comparison.
    """
    parser = argparse.ArgumentParser(
        description="Compare owlbot.py files in two directories."
    )
    parser.add_argument("input_dir", help="Path to the 'input' directory.")
    parser.add_argument("original_dir", help="Path to the 'original' directory.")
    parser.add_argument(
        "-d", "--diff", action="store_true", help="Output file differences."
    )

    args = parser.parse_args()

    compare_python_files_in_dirs(args.input_dir, args.original_dir, args.diff)


if __name__ == "__main__":
    main()
