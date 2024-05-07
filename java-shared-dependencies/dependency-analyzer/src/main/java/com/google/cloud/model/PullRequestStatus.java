package com.google.cloud.model;

public record PullRequestStatus(long created, long merged, Interval interval) {

}
