package com.google.storage.v2.samples;

import com.google.storage.v2.QueryWriteStatusResponse;
import com.google.storage.v2.StorageClient;

public class QueryWriteStatusStringUploadId {

  public static void main(String[] args) throws Exception {
    queryWriteStatusStringUploadId();
  }

  public static void queryWriteStatusStringUploadId() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String uploadId = "uploadId1563990780";
      QueryWriteStatusResponse response = storageClient.queryWriteStatus(uploadId);
    }
  }
}
