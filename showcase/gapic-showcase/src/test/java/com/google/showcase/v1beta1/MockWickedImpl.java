/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.showcase.v1beta1;

import com.google.api.core.BetaApi;
import com.google.protobuf.AbstractMessage;
import com.google.showcase.v1beta1.WickedGrpc.WickedImplBase;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.annotation.Generated;

@BetaApi
@Generated("by gapic-generator-java")
public class MockWickedImpl extends WickedImplBase {
  private List<AbstractMessage> requests;
  private Queue<Object> responses;

  public MockWickedImpl() {
    requests = new ArrayList<>();
    responses = new LinkedList<>();
  }

  public List<AbstractMessage> getRequests() {
    return requests;
  }

  public void addResponse(AbstractMessage response) {
    responses.add(response);
  }

  public void setResponses(List<AbstractMessage> responses) {
    this.responses = new LinkedList<Object>(responses);
  }

  public void addException(Exception exception) {
    responses.add(exception);
  }

  public void reset() {
    requests = new ArrayList<>();
    responses = new LinkedList<>();
  }

  @Override
  public void craftEvilPlan(EvilRequest request, StreamObserver<EvilResponse> responseObserver) {
    Object response = responses.poll();
    if (response instanceof EvilResponse) {
      requests.add(request);
      responseObserver.onNext(((EvilResponse) response));
      responseObserver.onCompleted();
    } else if (response instanceof Exception) {
      responseObserver.onError(((Exception) response));
    } else {
      responseObserver.onError(
          new IllegalArgumentException(
              String.format(
                  "Unrecognized response type %s for method CraftEvilPlan, expected %s or %s",
                  response == null ? "null" : response.getClass().getName(),
                  EvilResponse.class.getName(),
                  Exception.class.getName())));
    }
  }

  @Override
  public StreamObserver<EvilRequest> brainstormEvilPlans(
      final StreamObserver<EvilResponse> responseObserver) {
    StreamObserver<EvilRequest> requestObserver =
        new StreamObserver<EvilRequest>() {
          @Override
          public void onNext(EvilRequest value) {
            requests.add(value);
            final Object response = responses.remove();
            if (response instanceof EvilResponse) {
              responseObserver.onNext(((EvilResponse) response));
            } else if (response instanceof Exception) {
              responseObserver.onError(((Exception) response));
            } else {
              responseObserver.onError(
                  new IllegalArgumentException(
                      String.format(
                          "Unrecognized response type %s for method BrainstormEvilPlans, expected %s or %s",
                          response == null ? "null" : response.getClass().getName(),
                          EvilResponse.class.getName(),
                          Exception.class.getName())));
            }
          }

          @Override
          public void onError(Throwable t) {
            responseObserver.onError(t);
          }

          @Override
          public void onCompleted() {
            responseObserver.onCompleted();
          }
        };
    return requestObserver;
  }

  @Override
  public StreamObserver<EvilRequest> persuadeEvilPlan(
      final StreamObserver<EvilResponse> responseObserver) {
    StreamObserver<EvilRequest> requestObserver =
        new StreamObserver<EvilRequest>() {
          @Override
          public void onNext(EvilRequest value) {
            requests.add(value);
            final Object response = responses.remove();
            if (response instanceof EvilResponse) {
              responseObserver.onNext(((EvilResponse) response));
            } else if (response instanceof Exception) {
              responseObserver.onError(((Exception) response));
            } else {
              responseObserver.onError(
                  new IllegalArgumentException(
                      String.format(
                          "Unrecognized response type %s for method PersuadeEvilPlan, expected %s or %s",
                          response == null ? "null" : response.getClass().getName(),
                          EvilResponse.class.getName(),
                          Exception.class.getName())));
            }
          }

          @Override
          public void onError(Throwable t) {
            responseObserver.onError(t);
          }

          @Override
          public void onCompleted() {
            responseObserver.onCompleted();
          }
        };
    return requestObserver;
  }
}
