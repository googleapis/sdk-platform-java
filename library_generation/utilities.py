import sys
import subprocess
import os
import shutil
from collections.abc import Sequence

from library_generation.model.generation_config import GenerationConfig
from model.generation_config import from_yaml
from typing import List

script_dir = os.path.dirname(os.path.realpath(__file__))


def create_argument(arg_key: str, arg_container: object) -> List[str]:
    """
    Generates a list of two elements [argument, value], or returns
    an empty array if arg_val is None
    """
    arg_val = getattr(arg_container, arg_key, None)
    if arg_val is not None:
        return [f"--{arg_key}", f"{arg_val}"]
    return []


def get_configuration_yaml_library_api_shortnames(
    generation_config_yaml: str,
) -> List[str]:
    """
    For a given configuration yaml path, it returns a space-separated list of
    the api_shortnames contained in such configuration_yaml
    """
    config = from_yaml(generation_config_yaml)
    result = ""
    for library in config.libraries:
        result += f"{library.api_shortname} "
    return result[:-1]


def get_configuration_yaml_destination_path(
    generation_config_yaml: str
) -> str:
    """
    For a given configuration yaml path, it returns the destination_path
    entry at the root of the yaml
    """
    config = from_yaml(generation_config_yaml)
    return config.destination_path or ""


def run_process_and_print_output(arguments: List[str], job_name: str = "Job"):
    """
    Runs a process with the given "arguments" list and prints its output.
    If the process fails, then the whole program exits
    """
    # check_output() raises an exception if it exited with a nonzero code
    try:
        output = subprocess.check_output(arguments, stderr=subprocess.STDOUT)
        print(output.decode(), end="", flush=True)
        print(f"{job_name} finished successfully")
    except subprocess.CalledProcessError as ex:
        print(ex.output.decode(), end="", flush=True)
        print(f"{job_name} failed")
        sys.exit(1)


def sh_util(statement: str, **kwargs) -> str:
    """
    Calls a function defined in library_generation/utilities.sh
    """
    if "stdout" not in kwargs:
        kwargs["stdout"] = subprocess.PIPE
    if "stderr" not in kwargs:
        kwargs["stderr"] = subprocess.PIPE
    output = ""
    with subprocess.Popen(
        ["bash", "-exc", f"source {script_dir}/utilities.sh && {statement}"],
        **kwargs,
    ) as proc:
        print("command stderr:")
        for line in proc.stderr:
            print(line.decode(), end="", flush=True)
        print("command stdout:")
        for line in proc.stdout:
            print(line.decode(), end="", flush=True)
            output += line.decode()
        proc.wait()
        if proc.returncode != 0:
            raise RuntimeError(
                f"function {statement} failed with exit code {proc.returncode}"
            )
    # captured stdout may contain a newline at the end, we remove it
    if len(output) > 0 and output[-1] == "\n":
        output = output[:-1]
    return output


def eprint(*args, **kwargs):
    """
    prints to stderr
    """
    print(*args, file=sys.stderr, **kwargs)


def delete_if_exists(path: str) -> None:
    """
    Deletes a file or folder if it exists.
    :param path: the path to the file or folder
    :return: None
    """
    if os.path.isfile(path):  # Check if it's a file
        os.remove(path)
        print(f"File deleted: {path}")
    elif os.path.isdir(path):  # Check if it's a directory
        shutil.rmtree(path)
        print(f"Folder deleted: {path}")
    else:
        print(f"Path does not exist: {path}")


def check_monorepo(config: GenerationConfig) -> bool:
    """
    Check whether to generate a monorepo according to the
    generation config.
    :param config: the generation configuration
    :return: True if it's to generate a monorepo
    """
    return len(config.libraries) > 1


def main(argv: Sequence[str]) -> None:
    if len(argv) < 1:
        raise ValueError(
            "Usage: python generate_composed_library_args.py function_name arg1...argN"
        )

    function_name = argv[1]
    arguments = argv[2:]
    try:
        function = getattr(sys.modules[__name__], function_name)
        print(function(*arguments))
    except AttributeError:
        print(f'function name "{function_name}" not found in utilities.py')
        sys.exit(1)


if __name__ == "__main__":
    main(sys.argv)
