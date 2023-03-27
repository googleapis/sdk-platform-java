/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gcloud;

/**
 * Interface for Google Cloud resource's fields. Implementations of this interface can be used to
 * select only desired fields when getting or listing Google Cloud resources.
 */
public interface FieldSelector {

  /**
   * Returns a string selector. This selector is passed to a Google Cloud service (possibly with
   * other field selectors) to specify which resource fields should be returned by an API call.
   */
  String selector();
}
