package com.google.storage.v2.samples;

import com.google.protobuf.FieldMask;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.StorageClient;

public class UpdateHmacKeyHmacKeyMetadataHmacKeyFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateHmacKeyHmacKeyMetadataHmacKeyFieldMaskUpdateMask();
  }

  public static void updateHmacKeyHmacKeyMetadataHmacKeyFieldMaskUpdateMask() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      HmacKeyMetadata hmacKey = HmacKeyMetadata.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      HmacKeyMetadata response = storageClient.updateHmacKey(hmacKey, updateMask);
    }
  }
}
