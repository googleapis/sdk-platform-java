/*
 * Copyright 2025 Google LLC
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

package com.google.iam.v3;

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
public class PrincipalAccessBoundaryPolicyName implements ResourceName {
  private static final PathTemplate ORGANIZATION_LOCATION_PRINCIPAL_ACCESS_BOUNDARY_POLICY =
      PathTemplate.createWithoutUrlEncoding(
          "organizations/{organization}/locations/{location}/principalAccessBoundaryPolicies/{principal_access_boundary_policy}");
  private volatile Map<String, String> fieldValuesMap;
  private final String organization;
  private final String location;
  private final String principalAccessBoundaryPolicy;

  @Deprecated
  protected PrincipalAccessBoundaryPolicyName() {
    organization = null;
    location = null;
    principalAccessBoundaryPolicy = null;
  }

  private PrincipalAccessBoundaryPolicyName(Builder builder) {
    organization = Preconditions.checkNotNull(builder.getOrganization());
    location = Preconditions.checkNotNull(builder.getLocation());
    principalAccessBoundaryPolicy =
        Preconditions.checkNotNull(builder.getPrincipalAccessBoundaryPolicy());
  }

  public String getOrganization() {
    return organization;
  }

  public String getLocation() {
    return location;
  }

  public String getPrincipalAccessBoundaryPolicy() {
    return principalAccessBoundaryPolicy;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return new Builder(this);
  }

  public static PrincipalAccessBoundaryPolicyName of(
      String organization, String location, String principalAccessBoundaryPolicy) {
    return newBuilder()
        .setOrganization(organization)
        .setLocation(location)
        .setPrincipalAccessBoundaryPolicy(principalAccessBoundaryPolicy)
        .build();
  }

  public static String format(
      String organization, String location, String principalAccessBoundaryPolicy) {
    return newBuilder()
        .setOrganization(organization)
        .setLocation(location)
        .setPrincipalAccessBoundaryPolicy(principalAccessBoundaryPolicy)
        .build()
        .toString();
  }

  public static PrincipalAccessBoundaryPolicyName parse(String formattedString) {
    if (formattedString.isEmpty()) {
      return null;
    }
    Map<String, String> matchMap =
        ORGANIZATION_LOCATION_PRINCIPAL_ACCESS_BOUNDARY_POLICY.validatedMatch(
            formattedString,
            "PrincipalAccessBoundaryPolicyName.parse: formattedString not in valid format");
    return of(
        matchMap.get("organization"),
        matchMap.get("location"),
        matchMap.get("principal_access_boundary_policy"));
  }

  public static List<PrincipalAccessBoundaryPolicyName> parseList(List<String> formattedStrings) {
    List<PrincipalAccessBoundaryPolicyName> list = new ArrayList<>(formattedStrings.size());
    for (String formattedString : formattedStrings) {
      list.add(parse(formattedString));
    }
    return list;
  }

  public static List<String> toStringList(List<PrincipalAccessBoundaryPolicyName> values) {
    List<String> list = new ArrayList<>(values.size());
    for (PrincipalAccessBoundaryPolicyName value : values) {
      if (value == null) {
        list.add("");
      } else {
        list.add(value.toString());
      }
    }
    return list;
  }

  public static boolean isParsableFrom(String formattedString) {
    return ORGANIZATION_LOCATION_PRINCIPAL_ACCESS_BOUNDARY_POLICY.matches(formattedString);
  }

  @Override
  public Map<String, String> getFieldValuesMap() {
    if (fieldValuesMap == null) {
      synchronized (this) {
        if (fieldValuesMap == null) {
          ImmutableMap.Builder<String, String> fieldMapBuilder = ImmutableMap.builder();
          if (organization != null) {
            fieldMapBuilder.put("organization", organization);
          }
          if (location != null) {
            fieldMapBuilder.put("location", location);
          }
          if (principalAccessBoundaryPolicy != null) {
            fieldMapBuilder.put("principal_access_boundary_policy", principalAccessBoundaryPolicy);
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
    return ORGANIZATION_LOCATION_PRINCIPAL_ACCESS_BOUNDARY_POLICY.instantiate(
        "organization",
        organization,
        "location",
        location,
        "principal_access_boundary_policy",
        principalAccessBoundaryPolicy);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o != null && getClass() == o.getClass()) {
      PrincipalAccessBoundaryPolicyName that = ((PrincipalAccessBoundaryPolicyName) o);
      return Objects.equals(this.organization, that.organization)
          && Objects.equals(this.location, that.location)
          && Objects.equals(this.principalAccessBoundaryPolicy, that.principalAccessBoundaryPolicy);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= Objects.hashCode(organization);
    h *= 1000003;
    h ^= Objects.hashCode(location);
    h *= 1000003;
    h ^= Objects.hashCode(principalAccessBoundaryPolicy);
    return h;
  }

  /**
   * Builder for
   * organizations/{organization}/locations/{location}/principalAccessBoundaryPolicies/{principal_access_boundary_policy}.
   */
  public static class Builder {
    private String organization;
    private String location;
    private String principalAccessBoundaryPolicy;

    protected Builder() {}

    public String getOrganization() {
      return organization;
    }

    public String getLocation() {
      return location;
    }

    public String getPrincipalAccessBoundaryPolicy() {
      return principalAccessBoundaryPolicy;
    }

    public Builder setOrganization(String organization) {
      this.organization = organization;
      return this;
    }

    public Builder setLocation(String location) {
      this.location = location;
      return this;
    }

    public Builder setPrincipalAccessBoundaryPolicy(String principalAccessBoundaryPolicy) {
      this.principalAccessBoundaryPolicy = principalAccessBoundaryPolicy;
      return this;
    }

    private Builder(PrincipalAccessBoundaryPolicyName principalAccessBoundaryPolicyName) {
      this.organization = principalAccessBoundaryPolicyName.organization;
      this.location = principalAccessBoundaryPolicyName.location;
      this.principalAccessBoundaryPolicy =
          principalAccessBoundaryPolicyName.principalAccessBoundaryPolicy;
    }

    public PrincipalAccessBoundaryPolicyName build() {
      return new PrincipalAccessBoundaryPolicyName(this);
    }
  }
}
