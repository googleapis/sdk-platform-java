
import sys
import subprocess
import os
import shutil
from collections.abc import Sequence
from model.GenerationConfig import GenerationConfig

script_dir = os.path.dirname(os.path.realpath(__file__))


"""
Generates a list of two elements [argument, value], or returns
an empty array if arg_val is None
"""
def create_argument(arg_key: str, arg_container: object) -> str:
  arg_val = getattr(arg_container, arg_key, None)
  if arg_val is not None:
    return [f'--{arg_key}', f'{arg_val}']
  return []

"""
For a given configuration yaml path, it returns a space-separated list of
the api_shortnames contained in such configuration_yaml
"""
def get_configuration_yaml_library_api_shortnames(generation_config_yaml):
  config = GenerationConfig.from_yaml(generation_config_yaml)
  result = ''
  for library in config.libraries:
    result += f'{library.api_shortname} '
  return result[:-1]

"""
For a given configuration yaml path, it returns the destination_path
entry at the root of the yaml
"""
def get_configuration_yaml_destination_path(generation_config_yaml):
  config = GenerationConfig.from_yaml(generation_config_yaml)
  return config.destination_path or ''

"""
For a given configuration yaml path, it returns the python_version
entry at the root of the yaml
"""
def get_configuration_yaml_python_version(generation_config_yaml):
  config = GenerationConfig.from_yaml(generation_config_yaml)
  return config.python_version


"""
Calls a function defined in library_generation/utilities.sh
"""
def sh_util(statement: str, **kwargs) -> str:
  if 'stdout' not in kwargs:
    kwargs['stdout'] = subprocess.PIPE
  if 'stderr' not in kwargs:
    kwargs['stderr'] = subprocess.PIPE
  output = ''
  with subprocess.Popen(
      ['bash', '-exc', f'source {script_dir}/utilities.sh && {statement}'],
      **kwargs,
  ) as proc:
    print('command stderr:')
    for line in proc.stderr:
      print(line.decode(), end='', flush=True)
    print('command stdout:')
    for line in proc.stdout:
      print(line.decode(), end='', flush=True)
      output += line.decode()
    proc.wait()
    if proc.returncode != 0:
      raise RuntimeError(f'function {statement} failed with exit code {proc.returncode}')
  # captured stdout may contain a newline at the end, we remove it
  if len(output) > 0 and output[-1] == '\n':
    output = output[:-1]
  return output

"""
prints to stderr
"""
def eprint(*args, **kwargs):
  print(*args, file=sys.stderr, **kwargs)


"""Deletes a file or folder if it exists.

  Args:
      path: The path to the file or folder.
"""
def delete_if_exists(path):
  if os.path.isfile(path):  # Check if it's a file
    os.remove(path)
    print(f"File deleted: {path}")
  elif os.path.isdir(path):  # Check if it's a directory
    shutil.rmtree(path)
    print(f"Folder deleted: {path}")
  else:
    print(f"Path does not exist: {path}")

def main(argv: Sequence[str]) -> None:
  if len(argv) < 1:
    raise ValueError('Usage: python generate_composed_library_args.py function_name arg1...argN')

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
