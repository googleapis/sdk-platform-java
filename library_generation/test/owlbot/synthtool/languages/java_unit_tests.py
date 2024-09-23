# Copyright 2020 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import os
import shutil
import tempfile
import unittest
import xml.etree.ElementTree as elementTree
import yaml
from pathlib import Path
from synthtool.languages import java
import requests_mock
from . import util

TEST_OWLBOT = Path(__file__).parent.parent.parent.parent / "resources" / "test-owlbot"
FIXTURES = Path(__file__).parent.parent / "resources" / "test-owlbot" / "fixtures"
TEMPLATES_PATH = (
    Path(__file__).parent.parent.parent / "owlbot" / "synthtool" / "templates"
)

SAMPLE_METADATA = """
<metadata>
  <groupId>com.google.cloud</groupId>
  <artifactId>libraries-bom</artifactId>
  <versioning>
    <latest>3.3.0</latest>
    <release>3.3.0</release>
    <versions>
      <version>1.0.0</version>
      <version>1.1.0</version>
      <version>1.1.1</version>
      <version>1.2.0</version>
      <version>2.0.0</version>
      <version>2.1.0</version>
      <version>2.2.0</version>
      <version>2.2.1</version>
      <version>2.3.0</version>
      <version>2.4.0</version>
      <version>2.5.0</version>
      <version>2.6.0</version>
      <version>2.7.0</version>
      <version>2.7.1</version>
      <version>2.8.0</version>
      <version>2.9.0</version>
      <version>3.0.0</version>
      <version>3.1.0</version>
      <version>3.1.1</version>
      <version>3.2.0</version>
      <version>3.3.0</version>
    </versions>
    <lastUpdated>20191218182827</lastUpdated>
  </versioning>
</metadata>
"""


class JavaUnitTests(unittest.TestCase):

    def test_version_from_maven_metadata(self):
        self.assertEqual("3.3.0", java.version_from_maven_metadata(SAMPLE_METADATA))

    def test_latest_maven_version(self):
        with requests_mock.Mocker() as m:
            m.get(
                "https://repo1.maven.org/maven2/com/google/cloud/libraries-bom/maven-metadata.xml",
                text=SAMPLE_METADATA,
            )
            self.assertEqual(
                "3.3.0",
                java.latest_maven_version(
                    group_id="com.google.cloud", artifact_id="libraries-bom"
                ),
            )

    def test_working_common_templates(self):
        def assert_valid_xml(file):
            try:
                elementTree.parse(file)
            except elementTree.ParseError:
                self.fail(f"unable to parse XML: {file}")

        def assert_valid_yaml(file):
            with open(file, "r") as stream:
                try:
                    yaml.safe_load(stream)
                except yaml.YAMLError:
                    self.fail(f"unable to parse YAML: {file}")

        with util.copied_fixtures_dir(
            FIXTURES / "java_templates" / "standard"
        ) as workdir:
            # generate the common templates
            java.common_templates(template_path=TEMPLATES_PATH)
            assert os.path.isfile("renovate.json")

            # lint xml, yaml files
            # use os.walk because glob ignores hidden directories
            for dirpath, _, filenames in os.walk(workdir):
                for file in filenames:
                    (_, ext) = os.path.splitext(file)
                    if ext == ".xml":
                        assert_valid_xml(os.path.join(dirpath, file))
                    elif ext == ".yaml" or ext == ".yml":
                        assert_valid_yaml(os.path.join(dirpath, file))

    def test_remove_method(self):
        with tempfile.TemporaryDirectory() as tempdir:
            shutil.copyfile(
                "tests/testdata/SampleClass.java", tempdir + "/SampleClass.java"
            )

            java.remove_method(
                tempdir + "/SampleClass.java", "public static void foo()"
            )
            java.remove_method(tempdir + "/SampleClass.java", "public void asdf()")
            self.assert_matches_golden(
                "tests/testdata/SampleClassGolden.java", tempdir + "/SampleClass.java"
            )

    def test_copy_and_rename_method(self):
        with tempfile.TemporaryDirectory() as tempdir:
            cwd = os.getcwd()
            os.chdir(TEST_OWLBOT)
            shutil.copyfile("testdata/SampleClass.java", tempdir + "/SampleClass.java")

            java.copy_and_rename_method(
                tempdir + "/SampleClass.java",
                "public static void foo()",
                "foo",
                "foobar",
            )
            java.copy_and_rename_method(
                tempdir + "/SampleClass.java", "public void asdf()", "asdf", "xyz"
            )
            self.assert_matches_golden(
                "testdata/SampleCopyMethodGolden.java",
                tempdir + "/SampleClass.java",
            )
            os.chdir(cwd)

    def test_deprecate_method(self):
        # with tempfile.TemporaryDirectory() as tempdir:
        if True:
            cwd = os.getcwd()
            os.chdir(TEST_OWLBOT)
            tempdir = tempfile.mkdtemp()
            shutil.copyfile(
                "testdata/SampleDeprecateClass.java",
                tempdir + "/SampleDeprecateClass.java",
            )
            deprecation_warning = """This method will be removed in the next major version.\nUse {{@link #{new_method}()}} instead"""
            additional_comment = (
                """{new_method} has the same functionality as foobar."""
            )
            java.deprecate_method(
                tempdir + "/SampleDeprecateClass.java",
                "public void foo(String bar)",
                deprecation_warning.format(new_method="sample"),
            )

            # adding a comment when a javadoc and annotation already exists
            java.deprecate_method(
                tempdir + "/SampleDeprecateClass.java",
                "public void bar(String bar)",
                deprecation_warning.format(new_method="sample"),
            )
            java.deprecate_method(
                tempdir + "/SampleDeprecateClass.java",
                "public void cat(String bar)",
                additional_comment.format(new_method="sample"),
            )

            self.assert_matches_golden(
                "testdata/SampleDeprecateMethodGolden.java",
                tempdir + "/SampleDeprecateClass.java",
            )
            os.chdir(cwd)

    def test_fix_proto_license(self):
        with tempfile.TemporaryDirectory() as tempdir:
            temppath = Path(tempdir).resolve()
            os.mkdir(temppath / "src")
            shutil.copyfile(
                "tests/testdata/src/foo/FooProto.java", temppath / "src/FooProto.java"
            )

            java.fix_proto_headers(temppath)
            self.assert_matches_golden(
                "tests/testdata/FooProtoGolden.java", temppath / "src/FooProto.java"
            )

    def test_fix_proto_license_idempotent(self):
        with tempfile.TemporaryDirectory() as tempdir:
            temppath = Path(tempdir).resolve()
            os.mkdir(temppath / "src")
            shutil.copyfile(
                "tests/testdata/src/foo/FooProto.java", temppath / "src/FooProto.java"
            )

            # run the header fix twice
            java.fix_proto_headers(temppath)
            java.fix_proto_headers(temppath)
            self.assert_matches_golden(
                "tests/testdata/FooProtoGolden.java", temppath / "src/FooProto.java"
            )

    def test_fix_grpc_license(self):
        with tempfile.TemporaryDirectory() as tempdir:
            temppath = Path(tempdir).resolve()
            os.mkdir(temppath / "src")
            shutil.copyfile(
                "tests/testdata/src/foo/FooGrpc.java", temppath / "src/FooGrpc.java"
            )

            java.fix_grpc_headers(temppath)
            self.assert_matches_golden(
                "tests/testdata/FooGrpcGolden.java", temppath / "src/FooGrpc.java"
            )

    def test_fix_grpc_license_idempotent(self):
        with tempfile.TemporaryDirectory() as tempdir:
            temppath = Path(tempdir).resolve()
            os.mkdir(temppath / "src")
            shutil.copyfile(
                "tests/testdata/src/foo/FooGrpc.java", temppath / "src/FooGrpc.java"
            )

            # run the header fix twice
            java.fix_grpc_headers(temppath)
            java.fix_grpc_headers(temppath)
            self.assert_matches_golden(
                "tests/testdata/FooGrpcGolden.java", temppath / "src/FooGrpc.java"
            )

    def test_release_please_handle_releases(self):
        with util.copied_fixtures_dir(
            FIXTURES / "java_templates" / "release-please-update"
        ):
            # generate the common templates
            java.common_templates(template_path=TEMPLATES_PATH)

            assert os.path.isfile(".github/release-please.yml")
            with open(".github/release-please.yml") as fp:
                assert (
                    fp.read()
                    == """branches:
    - branch: 1.127.12-sp
      bumpMinorPreMajor: true
      handleGHRelease: true
      releaseType: java-lts
    bumpMinorPreMajor: true
    handleGHRelease: true
    releaseType: java-yoshi
    """
                )

    def test_defaults(self):
        with util.copied_fixtures_dir(FIXTURES / "java_templates" / "defaults_test"):
            java.common_templates(template_path=TEMPLATES_PATH)
            assert os.path.isfile(".kokoro/nightly/integration.cfg")
            self.assert_matches_golden(
                "nightly-integration-golden.cfg", ".kokoro/nightly/integration.cfg"
            )
            assert os.path.isfile(".kokoro/nightly/java11-integration.cfg")
            self.assert_matches_golden(
                "java11-integration-golden.cfg",
                ".kokoro/nightly/java11-integration.cfg",
            )
            assert os.path.isfile(".kokoro/presubmit/integration.cfg")
            self.assert_matches_golden(
                "presubmit-integration-golden.cfg", ".kokoro/presubmit/integration.cfg"
            )

    def test_merge_partials(self):
        with util.copied_fixtures_dir(FIXTURES / "java_templates" / "partials_test"):
            java.common_templates(template_path=TEMPLATES_PATH)
            assert os.path.isfile(".kokoro/nightly/integration.cfg")
            self.assert_matches_golden(
                "nightly-integration-golden.cfg", ".kokoro/nightly/integration.cfg"
            )
            assert os.path.isfile(".kokoro/nightly/java11-integration.cfg")
            self.assert_matches_golden(
                "java11-integration-golden.cfg",
                ".kokoro/nightly/java11-integration.cfg",
            )
            assert os.path.isfile(".kokoro/presubmit/integration.cfg")
            self.assert_matches_golden(
                "presubmit-integration-golden.cfg", ".kokoro/presubmit/integration.cfg"
            )

    @staticmethod
    def assert_matches_golden(expected, actual):
        matching_lines = 0
        with open(actual, "rt") as fp:
            with open(expected, "rt") as golden:
                while True:
                    matching_lines += 1
                    log_line = fp.readline()
                    expected = golden.readline()
                    assert repr(log_line) == repr(expected)
                    if not log_line:
                        break
        assert matching_lines > 0
