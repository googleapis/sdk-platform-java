"""
Unit tests for utilities.py
"""

import unittest
import os
import io
import sys
import contextlib
import subprocess
current = os.path.dirname(os.path.realpath(__file__))
parent = os.path.dirname(current)
sys.path.append(parent)
import utilities as util
from model.GAPIC import GAPIC
from model.GenerationConfig import GenerationConfig


class UtilitiesTest(unittest.TestCase):

  CONFIGURATION_YAML_PATH = os.path.join(current, 'resources', 'integration',
                                         'google-cloud-java', 'generation_config.yaml')

  def test_create_argument_valid_container_succeeds(self):
    container_value = 'google/test/v1'
    container = GAPIC(container_value)
    argument_key = 'proto_path'
    result = util.create_argument(argument_key, container)
    self.assertEqual([ f'--{argument_key}', container_value], result)

  def test_create_argument_empty_container_returns_empty_list(self):
    container = dict()
    argument_key = 'proto_path'
    result = util.create_argument(argument_key, container)
    self.assertEqual([], result)

  def test_create_argument_none_container_fails(self):
    container = None
    argument_key = 'proto_path'
    result = util.create_argument(argument_key, container)
    self.assertEqual([], result)

  def test_get_configuration_yaml_library_api_shortnames_valid_input_returns_valid_list(self):
    result = util.get_configuration_yaml_library_api_shortnames(self.CONFIGURATION_YAML_PATH)
    self.assertEqual('asset speech apigee-connect dialogflow compute kms '
                     + 'redis containeranalysis iam iamcredentials', result)

  def test_sh_util_existent_function_succeeds(self):
    result = util.sh_util('extract_folder_name path/to/folder_name')
    self.assertEqual('folder_name', result)

  def test_sh_util_nonexistent_function_fails(self):
    with self.assertRaises(RuntimeError):
      result = util.sh_util('nonexistent_function')

  def test_eprint_valid_input_succeeds(self):
    test_input='This is some test input'
    # create a stdio capture object
    stderr_capture = io.StringIO()
    # run eprint() with the capture object
    with contextlib.redirect_stderr(stderr_capture):
      util.eprint(test_input)
    result = stderr_capture.getvalue()
    # print() appends a `\n` each time it's called
    self.assertEqual(test_input + '\n', result)

  def test_delete_if_exists_preexisting_temp_files_succeeds(self):
    # create temporary directory
    # also remove last character (\n)
    temp_dir = subprocess.check_output(['mktemp', '-d']).decode()[:-1]

    # add a file and a folder to the temp dir
    file = os.path.join(temp_dir, 'temp_file')
    with open(file, 'a'):
      os.utime(file, None)
    folder = os.path.join(temp_dir, 'temp_child_dir')
    os.mkdir(folder)
    self.assertEqual(2, len(os.listdir(temp_dir)))

    # remove file and folder
    util.delete_if_exists(file)
    util.delete_if_exists(folder)
    self.assertEqual(0, len(os.listdir(temp_dir)))






if __name__ == "__main__":
  unittest.main()
