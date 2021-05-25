/*
 * Copyright 2021 Google LLC
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

package com.google.pubsub.v1;

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
public class SubscriptionName implements ResourceName {
  private static final PathTemplate PROJECT_SUBSCRIPTION =
      PathTemplate.createWithoutUrlEncoding("projects/{project}/subscriptions/{subscription}");
  private volatile Map<String, String> fieldValuesMap;
  private final String project;
  private final String subscription;

  @Deprecated
  protected SubscriptionName() {
    project = null;
    subscription = null;
  }

  private SubscriptionName(Builder builder) {
    project = Preconditions.checkNotNull(builder.getProject());
    subscription = Preconditions.checkNotNull(builder.getSubscription());
  }

  public String getProject() {
    return project;
  }

  public String getSubscription() {
    return subscription;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return new Builder(this);
  }

  public static SubscriptionName of(String project, String subscription) {
    return newBuilder().setProject(project).setSubscription(subscription).build();
  }

  public static String format(String project, String subscription) {
    return newBuilder().setProject(project).setSubscription(subscription).build().toString();
  }

  public static SubscriptionName parse(String formattedString) {
    if (formattedString.isEmpty()) {
      return null;
    }
    Map<String, String> matchMap =
        PROJECT_SUBSCRIPTION.validatedMatch(
            formattedString, "SubscriptionName.parse: formattedString not in valid format");
    return of(matchMap.get("project"), matchMap.get("subscription"));
  }

  public static List<SubscriptionName> parseList(List<String> formattedStrings) {
    List<SubscriptionName> list = new ArrayList<>(formattedStrings.size());
    for (String formattedString : formattedStrings) {
      list.add(parse(formattedString));
    }
    return list;
  }

  public static List<String> toStringList(List<SubscriptionName> values) {
    List<String> list = new ArrayList<>(values.size());
    for (SubscriptionName value : values) {
      if (value == null) {
        list.add("");
      } else {
        list.add(value.toString());
      }
    }
    return list;
  }

  public static boolean isParsableFrom(String formattedString) {
    return PROJECT_SUBSCRIPTION.matches(formattedString);
  }

  @Override
  public Map<String, String> getFieldValuesMap() {
    if (fieldValuesMap == null) {
      synchronized (this) {
        if (fieldValuesMap == null) {
          ImmutableMap.Builder<String, String> fieldMapBuilder = ImmutableMap.builder();
          if (project != null) {
            fieldMapBuilder.put("project", project);
          }
          if (subscription != null) {
            fieldMapBuilder.put("subscription", subscription);
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
    return PROJECT_SUBSCRIPTION.instantiate("project", project, "subscription", subscription);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o != null || getClass() == o.getClass()) {
      SubscriptionName that = ((SubscriptionName) o);
      return Objects.equals(this.project, that.project)
          && Objects.equals(this.subscription, that.subscription);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= Objects.hashCode(project);
    h *= 1000003;
    h ^= Objects.hashCode(subscription);
    return h;
  }

  /** Builder for projects/{project}/subscriptions/{subscription}. */
  public static class Builder {
    private String project;
    private String subscription;

    protected Builder() {}

    public String getProject() {
      return project;
    }

    public String getSubscription() {
      return subscription;
    }

    public Builder setProject(String project) {
      this.project = project;
      return this;
    }

    public Builder setSubscription(String subscription) {
      this.subscription = subscription;
      return this;
    }

    private Builder(SubscriptionName subscriptionName) {
      project = subscriptionName.project;
      subscription = subscriptionName.subscription;
    }

    public SubscriptionName build() {
      return new SubscriptionName(this);
    }
  }
}
