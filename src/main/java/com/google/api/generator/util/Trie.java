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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A common-prefix trie. T represents the type of each "char" in a word (which is a T-typed list).
 */
public class Trie<T> {
  private class Node<T> {
    final T chr;
    Map<T, Node> children = new HashMap<>();
    boolean isLeaf;

    Node() {
      chr = null;
    }

    Node(T chr) {
      this.chr = chr;
    }
  }

  private Node<T> root;

  public Trie() {
    root = new Node();
  }

  public void insert(List<T> word) {
    Map<T, Node> children = root.children;
    for (int i = 0; i < word.size(); i++) {
      T chr = word.get(i);
      Node t;
      if (children.containsKey(chr)) {
        t = children.get(chr);
      } else {
        t = new Node(chr);
        children.put(chr, t);
      }
      children = t.children;
      if (i == word.size() - 1) {
        t.isLeaf = true;
      }
    }
  }

  /** Returns true if the word is in the trie. */
  public boolean search(List<T> word) {
    Node node = searchNode(word);
    return node != null && node.isLeaf;
  }

  /** Returns true if some word in the trie begins with the given prefix. */
  public boolean hasPrefix(List<T> prefix) {
    return searchNode(prefix) != null;
  }

  private Node searchNode(List<T> word) {
    Map<T, Node> children = root.children;
    Node t = null;
    for (int i = 0; i < word.size(); i++) {
      T chr = word.get(i);
      if (children.containsKey(chr)) {
        t = children.get(chr);
        children = t.children;
      } else {
        return null;
      }
    }
    return t;
  }
}
