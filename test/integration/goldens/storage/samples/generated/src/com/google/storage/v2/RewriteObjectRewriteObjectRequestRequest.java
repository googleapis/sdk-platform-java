package com.google.storage.v2.samples;

import com.google.protobuf.ByteString;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.Object;
import com.google.storage.v2.PredefinedObjectAcl;
import com.google.storage.v2.RewriteObjectRequest;
import com.google.storage.v2.RewriteResponse;
import com.google.storage.v2.StorageClient;

public class RewriteObjectRewriteObjectRequestRequest {

  public static void main(String[] args) throws Exception {
    rewriteObjectRewriteObjectRequestRequest();
  }

  public static void rewriteObjectRewriteObjectRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      RewriteObjectRequest request =
          RewriteObjectRequest.newBuilder()
              .setDestination(Object.newBuilder().build())
              .setRewriteMask(FieldMask.newBuilder().build())
              .setSourceBucket("sourceBucket841604581")
              .setSourceObject("sourceObject1196439354")
              .setSourceGeneration(1232209852)
              .setRewriteToken("rewriteToken80654285")
              .setDestinationPredefinedAcl(PredefinedObjectAcl.forNumber(0))
              .setIfGenerationMatch(-1086241088)
              .setIfGenerationNotMatch(1475720404)
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setIfSourceGenerationMatch(-1427877280)
              .setIfSourceGenerationNotMatch(1575612532)
              .setIfSourceMetagenerationMatch(1143319909)
              .setIfSourceMetagenerationNotMatch(1900822777)
              .setMaxBytesRewrittenPerCall(1178170730)
              .setCopySourceEncryptionAlgorithm("copySourceEncryptionAlgorithm-1524952548")
              .setCopySourceEncryptionKeyBytes(ByteString.EMPTY)
              .setCopySourceEncryptionKeySha256Bytes(ByteString.EMPTY)
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      RewriteResponse response = storageClient.rewriteObject(request);
    }
  }
}
