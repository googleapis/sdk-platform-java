/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.showcase.v1beta1;

import com.google.api.pathtemplate.PathTemplate;
import com.google.api.resourcenames.ResourceName;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
@Generated("by gapic-generator-java")
public class ProfileName implements ResourceName {
  private static final PathTemplate USER =
      PathTemplate.createWithoutUrlEncoding("users/{user}/profile/blurbs");
  private volatile Map<String, String> fieldValuesMap;
  private final String user;

  @Deprecated
  protected ProfileName() {
    user = null;
  }

  private ProfileName(Builder builder) {
    user = Preconditions.checkNotNull(builder.getUser());
  }

  public String getUser() {
    return user;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return new Builder(this);
  }

  public static ProfileName of(String user) {
    return newBuilder().setUser(user).build();
  }

  public static String format(String user) {
    return newBuilder().setUser(user).build().toString();
  }

  public static ProfileName parse(String formattedString) {
    if (formattedString.isEmpty()) {
      return null;
    }
    Map<String, String> matchMap =
        USER.validatedMatch(
            formattedString, "ProfileName.parse: formattedString not in valid format");
    return of(matchMap.get("user"));
  }

  public static List<ProfileName> parseList(List<String> formattedStrings) {
    List<ProfileName> list = new ArrayList<>(formattedStrings.size());
    for (String formattedString : formattedStrings) {
      list.add(parse(formattedString));
    }
    return list;
  }

  public static List<String> toStringList(List<ProfileName> values) {
    List<String> list = new ArrayList<>(values.size());
    for (ProfileName value : values) {
      if (value == null) {
        list.add("");
      } else {
        list.add(value.toString());
      }
    }
    return list;
  }

  public static boolean isParsableFrom(String formattedString) {
    return USER.matches(formattedString);
  }

  @Override
  public Map<String, String> getFieldValuesMap() {
    if (fieldValuesMap == null) {
      synchronized (this) {
        if (fieldValuesMap == null) {
          ImmutableMap.Builder<String, String> fieldMapBuilder = ImmutableMap.builder();
          if (user != null) {
            fieldMapBuilder.put("user", user);
          }
          fieldValuesMap = fieldMapBuilder.build();
        }
      }
    }
    return fieldValuesMap;
  }

  public String getFieldValue(String fieldName) {
    return getFieldValuesMap().get(fieldName);
  }

  @Override
  public String toString() {
    return USER.instantiate("user", user);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o != null || getClass() == o.getClass()) {
      ProfileName that = ((ProfileName) o);
      return Objects.equals(this.user, that.user);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= Objects.hashCode(user);
    return h;
  }

  /** Builder for users/{user}/profile/blurbs. */
  public static class Builder {
    private String user;

    protected Builder() {}

    public String getUser() {
      return user;
    }

    public Builder setUser(String user) {
      this.user = user;
      return this;
    }

    private Builder(ProfileName profileName) {
      this.user = profileName.user;
    }

    public ProfileName build() {
      return new ProfileName(this);
    }
  }
}
