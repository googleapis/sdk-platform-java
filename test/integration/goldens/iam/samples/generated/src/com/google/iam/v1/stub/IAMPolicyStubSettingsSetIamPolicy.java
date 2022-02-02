package com.google.iam.v1.stub.samples;

import com.google.iam.v1.stub.IAMPolicyStubSettings;
import java.time.Duration;

public class IAMPolicyStubSettingsSetIamPolicy {

  public static void main(String[] args) throws Exception {
    iAMPolicyStubSettingsSetIamPolicy();
  }

  public static void iAMPolicyStubSettingsSetIamPolicy() throws Exception {
    IAMPolicyStubSettings.Builder iAMPolicySettingsBuilder = IAMPolicyStubSettings.newBuilder();
    iAMPolicySettingsBuilder
        .setIamPolicySettings()
        .setRetrySettings(
            iAMPolicySettingsBuilder
                .setIamPolicySettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    IAMPolicyStubSettings iAMPolicySettings = iAMPolicySettingsBuilder.build();
  }
}
