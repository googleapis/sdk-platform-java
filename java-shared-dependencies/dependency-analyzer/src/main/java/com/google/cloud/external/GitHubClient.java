package com.google.cloud.external;

import com.google.cloud.model.Interval;
import com.google.cloud.model.PullRequest;
import com.google.cloud.model.PullRequestStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GitHubClient {
  private final HttpClient client;
  private final Gson gson;
  private final static String PULL_REQUESTS_BASE = "https://api.github.com/repos/%s/%s/pulls?state=all&per_page=100&page=%s";
  private final static int MAX_PULL_REQUEST_NUM = 1000;
  private final static String OPEN_STATE = "open";

  public GitHubClient(HttpClient client) {
    this.client = client;
    this.gson = new GsonBuilder().create();
  }

  public PullRequestStatus listMonthlyPullRequestStatusOf(String organization, String repo)
      throws URISyntaxException, IOException, InterruptedException {
    return listPullRequestStatus(organization, repo, Interval.MONTHLY);
  }

  private PullRequestStatus listPullRequestStatus(String organization, String repo, Interval interval)
      throws URISyntaxException, IOException, InterruptedException {
    List<PullRequest> pullRequests = listPullRequests(organization, repo);
    ZonedDateTime now = ZonedDateTime.now();
    long created = pullRequests
        .stream()
        .distinct()
        .filter(pullRequest -> pullRequest.state().equals(OPEN_STATE))
        .filter(pullRequest -> {
          ZonedDateTime createdAt = utcTimeFrom(pullRequest.createdAt());
          return now.minusDays(interval.getDays()).isBefore(createdAt);
        })
        .count();

    long merged = pullRequests
        .stream()
        .distinct()
        .filter(pullRequest -> Objects.nonNull(pullRequest.mergedAt()))
        .filter(pullRequest -> {
          ZonedDateTime createdAt = utcTimeFrom(pullRequest.mergedAt());
          return now.minusDays(interval.getDays()).isBefore(createdAt);
        })
        .count();

    return new PullRequestStatus(created, merged, interval);
  }

  private List<PullRequest> listPullRequests(String organization, String repo)
      throws URISyntaxException, IOException, InterruptedException {
    List<PullRequest> pullRequests = new ArrayList<>();
    int page = 1;
    while (pullRequests.size() < MAX_PULL_REQUEST_NUM) {
      HttpResponse<String> response = getResponse(getPullRequestsUrl(organization, repo, page));
      pullRequests.addAll(gson.fromJson(response.body(), new TypeToken<List<PullRequest>>(){}.getType()));
      page++;
    }

    return pullRequests;
  }

  private String getPullRequestsUrl(String organization, String repo, int page) {
    return String.format(PULL_REQUESTS_BASE, organization, repo, page);
  }

  private ZonedDateTime utcTimeFrom(String time) {
    ZoneId zoneIdUTC = ZoneId.of("UTC");
    Instant instant = Instant.parse(time);
    return instant.atZone(zoneIdUTC);
  }

  private HttpResponse<String> getResponse(String endpoint)
      throws URISyntaxException, IOException, InterruptedException {
    HttpRequest request = HttpRequest
        .newBuilder()
        .header("Authorization", System.getenv("GITHUB_TOKEN"))
        .uri(new URI(endpoint))
        .GET()
        .build();
    return client.send(request, BodyHandlers.ofString());
  }
}
