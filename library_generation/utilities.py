
from collections.abc import Sequence
import sys



"""
Helper function for generate_composed_library.py

It takes a single "query" argument
"query" is a string of key=value entries separated by a comma
example: "key1=value1,key2=value2,etc"

it writes the same KVs in a format that generate_library.sh accepts to stdout
example: "--key1 value1 --key2 value2"

it ensures that both keys and values don't contain spaces
"""
def get_generate_library_arguments(query: str):
  result = ''
  raw_arguments_kv = [kv.strip() for kv in query.split(',')]
  for raw_argument_kv in raw_arguments_kv:
    key = _get_raw_argument_component(raw_argument_kv, 0)
    value = _get_raw_argument_component(raw_argument_kv, 1)
    result += f'--{key} {value} '
  return result

def get_argument_value_from_query(query: str, argument :str):
  found_argument = list(filter(lambda x: argument in x, query.split(',')))
  if len(found_argument) == 0:
    raise ValueError(f'query string does not contain the argument {argument}')
  return _get_raw_argument_component(found_argument[0], 1)

def _get_raw_argument_component(raw_argument: str, index: int):
  result = raw_argument.split('=')[index]
  if ' ' in result:
    raise ValueError(f'argument key or value contains a space: {result}')
  return result

def main(argv: Sequence[str]) -> None:
  if len(argv) < 1:
    raise ValueError('Usage: python generate_composed_library_args.py function_name arg1...argN')

  function_name = argv[1]
  arguments = argv[2:]
  function = getattr(sys.modules[__name__], function_name)
  print(function(*arguments))


if __name__ == "__main__":
  main(sys.argv)
