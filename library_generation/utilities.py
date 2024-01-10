
from collections.abc import Sequence
import sys
import subprocess
import os

script_dir = os.path.dirname(os.path.realpath(__file__))

"""
Helper function for generate_composed_library.py

It takes a single "query" argument
"query" is a string of key=value entries separated by a comma
example: "key1=value1,key2=value2,etc"

it writes the same KVs in a format that generate_library.sh accepts to stdout
example: "--key1 value1 --key2 value2"

it ensures that both keys and values don't contain spaces
"""
def get_generate_library_arguments(query: str) -> str:
  result = []
  raw_arguments_kv = [kv.strip() for kv in query.split(',')]
  for raw_argument_kv in raw_arguments_kv:
    key = _get_raw_argument_component(raw_argument_kv, 0)
    value = _get_raw_argument_component(raw_argument_kv, 1)
    result += [f'--{key}', f'{value}']
  return result

"""
Obtains the value of a single argument in an argument query string.
An argument query string is a comma-separated list of key-value pairs,
for example "key1=val1,key2=val2".
It returns the value for "argument"
"""
def get_argument_value_from_query(query: str, argument :str) -> str:
  found_argument = list(filter(lambda x: f'{argument}=' in x, query.split(',')))
  if len(found_argument) == 0:
    raise ValueError(f'query string does not contain the argument {argument}')
  return _get_raw_argument_component(found_argument[0], 1)

"""
Given the input parameter "arguments" (example "--arg1 val1 -arg2 val2"),
this function adds another argument "--arg_key arg_value" to the end
of the argument string
"""
def add_argument(arguments: list[str], arg_key: str, arg_val: str) -> str:
  return arguments + [f'--{arg_key}', f'{arg_val}']

def sh_util(statement: str, **kwargs) -> str:
  if 'stdout' not in kwargs:
    kwargs['stdout'] = subprocess.PIPE
  if 'stderr' not in kwargs:
    kwargs['stderr'] = subprocess.PIPE
  output = ''
  with subprocess.Popen(
      ['bash', '-c', f'source {script_dir}/utilities.sh && {statement}'],
      **kwargs,
  ) as proc:
    print('command stderr:')
    for line in proc.stderr:
      print(line.decode(), end='', flush=True)
    print('command stdout:')
    for line in proc.stdout:
      print(line.decode(), end='', flush=True)
      output += line.decode()
  # captured stdout may contain a newline at the end, we remove it
  if len(output) > 0 and output[-1] == '\n':
    output = output[:-1]
  return output

def _get_raw_argument_component(raw_argument: str, index: int) -> str:
  result = raw_argument.split('=')[index]
  return result

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
