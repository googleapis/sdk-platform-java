import unittest
from typing import List, Optional, Dict

from common.model.owlbot_yaml_config import (
    DeepCopyRegexItem,
    OwlbotYamlAdditionRemove,
    OwlbotYamlConfig,
)


class TestDeepCopyRegexItem(unittest.TestCase):

    def test_to_dict(self):
        item = DeepCopyRegexItem(source="src/path", dest="dest/path")
        expected_dict = {"source": "src/path", "dest": "dest/path"}
        self.assertEqual(item.to_dict(), expected_dict)


class TestOwlbotYamlAdditionRemove(unittest.TestCase):

    def test_to_dict_all_values(self):
        item1 = DeepCopyRegexItem(source="src1", dest="dest1")
        item2 = DeepCopyRegexItem(source="src2", dest="dest2")
        obj = OwlbotYamlAdditionRemove(
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
        obj = OwlbotYamlAdditionRemove(deep_remove_regex=["remove1"])
        expected_dict = {"deep_remove_regex": ["remove1"]}
        self.assertEqual(obj.to_dict(), expected_dict)

    def test_to_dict_empty(self):
        obj = OwlbotYamlAdditionRemove()
        expected_dict = {}
        self.assertEqual(obj.to_dict(), expected_dict)


class TestOwlbotYamlConfig(unittest.TestCase):

    def test_to_dict_all_values(self):
        item1 = DeepCopyRegexItem(source="src1", dest="dest1")
        addition_obj = OwlbotYamlAdditionRemove(
            deep_copy_regex=[item1], deep_remove_regex=["remove1"]
        )
        remove_obj = OwlbotYamlAdditionRemove(deep_preserve_regex=["preserve1"])
        config = OwlbotYamlConfig(addition=addition_obj, remove=remove_obj)
        expected_dict = {
            "addition": {
                "deep_copy_regex": [{"source": "src1", "dest": "dest1"}],
                "deep_remove_regex": ["remove1"],
            },
            "remove": {"deep_preserve_regex": ["preserve1"]},
        }
        self.assertEqual(config.to_dict(), expected_dict)

    def test_to_dict_addition_none(self):
        remove_obj = OwlbotYamlAdditionRemove(deep_preserve_regex=["preserve1"])
        config = OwlbotYamlConfig(remove=remove_obj)
        expected_dict = {"remove": {"deep_preserve_regex": ["preserve1"]}}
        self.assertEqual(config.to_dict(), expected_dict)

    def test_to_dict_remove_none(self):
        item1 = DeepCopyRegexItem(source="src1", dest="dest1")
        addition_obj = OwlbotYamlAdditionRemove(
            deep_copy_regex=[item1], deep_remove_regex=["remove1"]
        )
        config = OwlbotYamlConfig(addition=addition_obj)
        expected_dict = {
            "addition": {
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
