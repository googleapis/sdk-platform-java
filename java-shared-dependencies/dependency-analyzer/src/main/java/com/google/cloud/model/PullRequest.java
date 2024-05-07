package com.google.cloud.model;

import com.google.gson.annotations.SerializedName;

public record PullRequest(
    String url,
    String state,
    @SerializedName("created_at")
    String createdAt,
    @SerializedName("merged_at")
    String mergedAt) {

}
