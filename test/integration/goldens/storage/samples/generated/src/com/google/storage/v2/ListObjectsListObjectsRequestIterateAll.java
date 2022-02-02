package com.google.storage.v2.samples;

import com.google.protobuf.FieldMask;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.ListObjectsRequest;
import com.google.storage.v2.Object;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListObjectsListObjectsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listObjectsListObjectsRequestIterateAll();
  }

  public static void listObjectsListObjectsRequestIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ListObjectsRequest request =
          ListObjectsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setDelimiter("delimiter-250518009")
              .setIncludeTrailingDelimiter(true)
              .setPrefix("prefix-980110702")
              .setVersions(true)
              .setReadMask(FieldMask.newBuilder().build())
              .setLexicographicStart("lexicographicStart-2093413008")
              .setLexicographicEnd("lexicographicEnd1646968169")
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      for (Object element : storageClient.listObjects(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
