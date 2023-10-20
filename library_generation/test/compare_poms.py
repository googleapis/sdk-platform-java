"""
Utility to compare the contents of two XML files. 
This focuses on the tree structure of both XML files, meaning that element order and whitespace will be disregarded.
The only comparison points are: element path (e.g. project/dependencies) and element text
There is a special case for `dependency`, where the maven coordinates are prepared as well
"""

import sys
import xml.etree.ElementTree as ET
from collections import Counter

"""
Convenience method to access a node's child elements via path and get its text
"""
def get_text_from_element(node, element_name, namespace):
  child = node.find(namespace + element_name)
  return child.text if child is not None else ''

"""
Convenience method to pretty print the contents of a Counter (or dict)
"""
def print_counter(counter):
  for key, value in counter.items():
    eprint(f'{key}: {value}')

"""
Recursively traverses a node tree and appends element text to a given
`elements` array. If the element tag is `dependency`
then the maven coordinates for its children will be computed as well
"""
def append_to_element_list(node, path, elements):
  namespace_start, namespace_end, tag_name = node.tag.rpartition('}')
  namespace = namespace_start + namespace_end
  if tag_name == 'dependency':
    group_id = get_text_from_element(node, 'groupId', namespace)
    artifact_id = get_text_from_element(node, 'artifactId', namespace)
    artifact_str = ''
    artifact_str += group_id
    artifact_str += ':' + artifact_id
    elements.append(path + '/' + tag_name + '=' + artifact_str)
  if node.text and len(node.text.strip()) > 0:
    elements.append(path + '/' + tag_name + '=' + node.text)

  if tag_name == 'version':
    # versions may be yet to be processed, we disregard them
    return elements

  for child in node:
    child_path = path + '/' + tag_name
    append_to_element_list(child, child_path, elements)

  return elements

"""
compares two XMLs for content differences
the argument print_whole_trees determines if both trees should be printed
"""
def compare_xml(file1, file2, print_whole_trees):
  try:
    eprint('checkpoint 2')
    tree1 = ET.parse(file1)
    eprint('checkpoint 3')
    tree2 = ET.parse(file2)
    eprint('checkpoint 4')
  except ET.ParseError as e:
    eprint('checkpoint 5')
    return f'Error parsing XML: {e}'
  except FileNotFoundError as e:
    eprint('checkpoint 6')
    return f'Error reading file: {e}'

  tree1_elements = []
  tree2_elements = []

  eprint('checkpoint 7')
  append_to_element_list(tree1.getroot(), '/', tree1_elements)
  eprint('checkpoint 8')
  append_to_element_list(tree2.getroot(), '/', tree2_elements)
  eprint('checkpoint 9')

  tree1_counter = Counter(tree1_elements)
  tree2_counter = Counter(tree2_elements)
  intersection = tree1_counter & tree2_counter
  only_in_tree1 = tree1_counter - intersection
  only_in_tree2 = tree2_counter - intersection
  if print_whole_trees == 'true':
    eprint('tree1')
    print_counter(tree2_counter)
    eprint('tree2')
    print_counter(tree1_counter)
  if len(only_in_tree1) > 0 or len(only_in_tree2) > 0:
    eprint('only in ' + file1)
    print_counter(only_in_tree1)
    eprint('only in ' + file2)
    print_counter(only_in_tree2)
    return True
  else:
    return False

def eprint(*args, **kwargs):
  print(*args, file=sys.stderr, **kwargs)

if __name__ == "__main__":
  if len(sys.argv) != 4:
    eprint("Usage: python compare_xml.py <file1> <file2> <print_whole_trees(true|false)>")
    sys.exit(1)

  file1 = sys.argv[1]
  file2 = sys.argv[2]
  print_whole_trees = sys.argv[3]
  eprint('checkpoint 0')
  has_diff = compare_xml(file1, file2, print_whole_trees)
  eprint('checkpoint 1')

  if has_diff:
    eprint(f'The poms are different')
    sys.exit(1)
  else:
    eprint('The XML files are the same.')
    sys.exit(0)


