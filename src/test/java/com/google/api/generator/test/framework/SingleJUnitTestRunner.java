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

package com.google.api.generator.test.framework;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

public class SingleJUnitTestRunner {
  public static void main(String... args) {
    String className = args[0];
    boolean update_golden = args[1].equals("true");
    System.setProperty(className + ".update_golden", "true");
    Class clazz = null;
    try {
      clazz = Class.forName(className);
    } catch (ClassNotFoundException e) {
      System.exit(1);
    }
    Request request = Request.aClass(clazz);
    Result result = new JUnitCore().run(request);
    System.setProperty(className + ".update_golden", "false");
    System.exit(result.wasSuccessful() ? 0 : 1);
  }
}
