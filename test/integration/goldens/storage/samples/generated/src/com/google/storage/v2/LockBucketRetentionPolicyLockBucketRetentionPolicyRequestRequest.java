package com.google.storage.v2.samples;

import com.google.storage.v2.Bucket;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.LockBucketRetentionPolicyRequest;
import com.google.storage.v2.StorageClient;

public class LockBucketRetentionPolicyLockBucketRetentionPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    lockBucketRetentionPolicyLockBucketRetentionPolicyRequestRequest();
  }

  public static void lockBucketRetentionPolicyLockBucketRetentionPolicyRequestRequest()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      LockBucketRetentionPolicyRequest request =
          LockBucketRetentionPolicyRequest.newBuilder()
              .setBucket(BucketName.of("[PROJECT]", "[BUCKET]").toString())
              .setIfMetagenerationMatch(1043427781)
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      Bucket response = storageClient.lockBucketRetentionPolicy(request);
    }
  }
}
