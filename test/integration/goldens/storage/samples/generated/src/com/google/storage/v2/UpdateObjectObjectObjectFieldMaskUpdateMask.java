package com.google.storage.v2.samples;

import com.google.protobuf.FieldMask;
import com.google.storage.v2.Object;
import com.google.storage.v2.StorageClient;

public class UpdateObjectObjectObjectFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateObjectObjectObjectFieldMaskUpdateMask();
  }

  public static void updateObjectObjectObjectFieldMaskUpdateMask() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      Object object = Object.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      Object response = storageClient.updateObject(object, updateMask);
    }
  }
}
