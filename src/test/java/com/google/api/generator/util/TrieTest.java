// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.util;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.junit.Test;

public class TrieTest {
  @Test
  public void stringTrie() {
    Trie<String> trie = new Trie<>();

    Function<String, List<String>> wordToCharListFn = w -> Arrays.asList(w.split("(?!^)"));
    List<String> wordCat = wordToCharListFn.apply("cat");
    assertFalse(trie.search(wordCat));

    trie.insert(wordCat);
    assertTrue(trie.search(wordCat));
    assertFalse(trie.search(wordToCharListFn.apply("car")));
    assertTrue(trie.hasPrefix(wordToCharListFn.apply("ca")));

    trie.insert(wordToCharListFn.apply("car"));
    trie.insert(wordToCharListFn.apply("dog"));
    assertTrue(trie.search(wordToCharListFn.apply("car")));
    assertTrue(trie.search(wordToCharListFn.apply("dog")));
  }

  @Test
  public void multiStringTrie() {
    Trie<String> trie = new Trie<>();
    assertFalse(trie.search(Arrays.asList("user", "identity", "name")));

    trie.insert(Arrays.asList("user", "identity", "name"));
    trie.insert(Arrays.asList("user", "identity", "age"));
    trie.insert(Arrays.asList("user", "contact", "email"));

    assertTrue(trie.search(Arrays.asList("user", "identity", "name")));
    assertTrue(trie.search(Arrays.asList("user", "identity", "age")));
    assertFalse(trie.search(Arrays.asList("user", "identity", "eyeColor")));
    assertTrue(trie.search(Arrays.asList("user", "contact", "email")));
    assertTrue(trie.hasPrefix(Arrays.asList("user", "identity")));
    assertTrue(trie.hasPrefix(Arrays.asList("user", "contact")));
    assertTrue(trie.hasPrefix(Arrays.asList("user")));

    assertFalse(trie.hasPrefix(Arrays.asList("identity")));
    assertFalse(trie.hasPrefix(Arrays.asList("contact")));
  }
}
