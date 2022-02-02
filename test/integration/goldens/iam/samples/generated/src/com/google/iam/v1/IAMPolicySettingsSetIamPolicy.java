package com.google.iam.v1.samples;

import com.google.iam.v1.IAMPolicySettings;
import java.time.Duration;

public class IAMPolicySettingsSetIamPolicy {

  public static void main(String[] args) throws Exception {
    iAMPolicySettingsSetIamPolicy();
  }

  public static void iAMPolicySettingsSetIamPolicy() throws Exception {
    IAMPolicySettings.Builder iAMPolicySettingsBuilder = IAMPolicySettings.newBuilder();
    iAMPolicySettingsBuilder
        .setIamPolicySettings()
        .setRetrySettings(
            iAMPolicySettingsBuilder
                .setIamPolicySettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    IAMPolicySettings iAMPolicySettings = iAMPolicySettingsBuilder.build();
  }
}
