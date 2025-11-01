import unittest
from typing import List, Optional, Dict

from common.model.owlbot_yaml_config import (
    DeepCopyRegexItem,
    OwlbotYamlAdditionRemoval,
    OwlbotYamlConfig,
)


class TestDeepCopyRegexItem(unittest.TestCase):

    def test_to_dict(self):
        item = DeepCopyRegexItem(source="src/path", dest="dest/path")
        expected_dict = {"source": "src/path", "dest": "dest/path"}
        self.assertEqual(item.to_dict(), expected_dict)


class TestOwlbotYamlAdditionRemoval(unittest.TestCase):

    def test_to_dict_all_values(self):
        item1 = DeepCopyRegexItem(source="src1", dest="dest1")
        item2 = DeepCopyRegexItem(source="src2", dest="dest2")
        obj = OwlbotYamlAdditionRemoval(
            deep_copy_regex=[item1, item2],
            deep_remove_regex=["remove1", "remove2"],
            deep_preserve_regex=["preserve1", "preserve2"],
        )
        expected_dict = {
            "deep_copy_regex": [
                {"source": "src1", "dest": "dest1"},
                {"source": "src2", "dest": "dest2"},
            ],
            "deep_remove_regex": ["remove1", "remove2"],
            "deep_preserve_regex": ["preserve1", "preserve2"],
        }
        self.assertEqual(obj.to_dict(), expected_dict)

    def test_to_dict_some_values_none(self):
        obj = OwlbotYamlAdditionRemoval(deep_remove_regex=["remove1"])
        expected_dict = {"deep_remove_regex": ["remove1"]}
        self.assertEqual(obj.to_dict(), expected_dict)

    def test_to_dict_empty(self):
        obj = OwlbotYamlAdditionRemoval()
        expected_dict = {}
        self.assertEqual(obj.to_dict(), expected_dict)


class TestOwlbotYamlConfig(unittest.TestCase):

    def test_to_dict_all_values(self):
        item1 = DeepCopyRegexItem(source="src1", dest="dest1")
        addition_obj = OwlbotYamlAdditionRemoval(
            deep_copy_regex=[item1], deep_remove_regex=["remove1"]
        )
        remove_obj = OwlbotYamlAdditionRemoval(deep_preserve_regex=["preserve1"])
        config = OwlbotYamlConfig(additions=addition_obj, removals=remove_obj)
        expected_dict = {
            "additions": {
                "deep_copy_regex": [{"source": "src1", "dest": "dest1"}],
                "deep_remove_regex": ["remove1"],
            },
            "removals": {"deep_preserve_regex": ["preserve1"]},
        }
        self.assertEqual(config.to_dict(), expected_dict)

    def test_to_dict_addition_none(self):
        remove_obj = OwlbotYamlAdditionRemoval(deep_preserve_regex=["preserve1"])
        config = OwlbotYamlConfig(removals=remove_obj)
        expected_dict = {"removals": {"deep_preserve_regex": ["preserve1"]}}
        self.assertEqual(config.to_dict(), expected_dict)

    def test_to_dict_remove_none(self):
        item1 = DeepCopyRegexItem(source="src1", dest="dest1")
        addition_obj = OwlbotYamlAdditionRemoval(
            deep_copy_regex=[item1], deep_remove_regex=["remove1"]
        )
        config = OwlbotYamlConfig(additions=addition_obj)
        expected_dict = {
            "additions": {
                "deep_copy_regex": [{"source": "src1", "dest": "dest1"}],
                "deep_remove_regex": ["remove1"],
            }
        }
        self.assertEqual(config.to_dict(), expected_dict)

    def test_to_dict_empty(self):
        config = OwlbotYamlConfig()
        expected_dict = {}
        self.assertEqual(config.to_dict(), expected_dict)


if __name__ == "__main__":
    unittest.main()
