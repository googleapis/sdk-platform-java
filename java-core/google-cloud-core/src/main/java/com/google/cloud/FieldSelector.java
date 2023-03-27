/*
 * Copyright 2016 Google LLC
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

package com.google.cloud;

import com.google.api.core.InternalApi;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Interface for Google Cloud resource's fields. Implementations of this interface can be used to
 * select only desired fields from a returned Google Cloud resource.
 */
public interface FieldSelector {


  /**
   * Returns a string selector. This selector is passed to a Google Cloud service (possibly with
   * other field selectors) to specify which resource fields should be returned by an API call.
   */
  String getSelector();

  /**
   * A helper class used to build composite selectors given a number of fields. This class is not
   * supposed to be used directly by users.
   */
  @InternalApi
  class Helper {

    private static final String[] EMPTY_FIELDS = {};

    private Helper() {}

    private static final Function<FieldSelector, String> FIELD_TO_STRING_FUNCTION =
        new Function<FieldSelector, String>() {
          @Override
          public String apply(FieldSelector fieldSelector) {
            return fieldSelector.getSelector();
          }
        };

    private static String selector(List<? extends FieldSelector> required, FieldSelector[] others,
        String... extraResourceFields) {
      Set<String> fieldStrings = Sets.newHashSetWithExpectedSize(required.size() + others.length);
      fieldStrings.addAll(Lists.transform(required, FIELD_TO_STRING_FUNCTION));
      fieldStrings.addAll(Lists.transform(Arrays.asList(others), FIELD_TO_STRING_FUNCTION));
      fieldStrings.addAll(Arrays.asList(extraResourceFields));
      return Joiner.on(',').join(fieldStrings);
    }

    /**
     * Returns a composite selector given a number of resource fields. The string selector returned
     * by this method can be used for field selection in API calls that return a single resource.
     * This method is not supposed to be used directly by users.
     */
    public static String selector(List<? extends FieldSelector> required, FieldSelector... others) {
      return selector(required, others, new String[]{});
    }

    /**
     * Returns a composite selector given a number of resource fields and a container name. The
     * string selector returned by this method can be used for field selection in API calls that
     * return a  list of resources. This method is not supposed to be used directly by users.
     */
    public static String listSelector(String containerName, List<? extends FieldSelector> required,
        FieldSelector... others) {
      return "nextPageToken," + containerName + '(' + selector(required, others) + ')';
    }

    /**
     * Returns a composite selector given a number of resource fields and a container name. This
     * method also takes an {@code extraResourceFields} parameter to specify some extra resource
     * fields as strings. The string selector returned by this method can be used for field
     * selection in API calls that return a list of resources. This method is not supposed to be
     * used directly by users.
     */
    public static String listSelector(String containerName, List<? extends FieldSelector> required,
        FieldSelector[] others, String... extraResourceFields) {
      return listSelector(EMPTY_FIELDS, containerName, required, others, extraResourceFields);
    }

    /**
     * Returns a composite selector given a number of top level fields as strings, a number of
     * resource fields and a container name. This method also takes an {@code extraResourceFields}
     * parameter  to specify some extra resource fields as strings. The string selector returned by
     * this method can be used for field selection in API calls that return a list of resources.
     * This method is not supposed to be used directly by users.
     */
    public static String listSelector(String[] topLevelFields, String containerName,
        List<? extends FieldSelector> required, FieldSelector[] others,
        String... extraResourceFields) {
      Set<String> topLevelStrings = Sets.newHashSetWithExpectedSize(topLevelFields.length + 1);
      topLevelStrings.addAll(Lists.asList("nextPageToken", topLevelFields));
      return Joiner.on(',').join(topLevelStrings) + "," + containerName + '('
          + selector(required, others, extraResourceFields) + ')';
    }
  }
}
