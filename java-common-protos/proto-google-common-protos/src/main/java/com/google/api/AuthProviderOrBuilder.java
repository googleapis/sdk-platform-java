/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/auth.proto

// Protobuf Java Version: 3.25.2
package com.google.api;

public interface AuthProviderOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.AuthProvider)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The unique identifier of the auth provider. It will be referred to by
   * `AuthRequirement.provider_id`.
   *
   * Example: "bookstore_auth".
   * </pre>
   *
   * <code>string id = 1;</code>
   *
   * @return The id.
   */
  java.lang.String getId();
  /**
   *
   *
   * <pre>
   * The unique identifier of the auth provider. It will be referred to by
   * `AuthRequirement.provider_id`.
   *
   * Example: "bookstore_auth".
   * </pre>
   *
   * <code>string id = 1;</code>
   *
   * @return The bytes for id.
   */
  com.google.protobuf.ByteString getIdBytes();

  /**
   *
   *
   * <pre>
   * Identifies the principal that issued the JWT. See
   * https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-32#section-4.1.1
   * Usually a URL or an email address.
   *
   * Example: https://securetoken.google.com
   * Example: 1234567-compute&#64;developer.gserviceaccount.com
   * </pre>
   *
   * <code>string issuer = 2;</code>
   *
   * @return The issuer.
   */
  java.lang.String getIssuer();
  /**
   *
   *
   * <pre>
   * Identifies the principal that issued the JWT. See
   * https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-32#section-4.1.1
   * Usually a URL or an email address.
   *
   * Example: https://securetoken.google.com
   * Example: 1234567-compute&#64;developer.gserviceaccount.com
   * </pre>
   *
   * <code>string issuer = 2;</code>
   *
   * @return The bytes for issuer.
   */
  com.google.protobuf.ByteString getIssuerBytes();

  /**
   *
   *
   * <pre>
   * URL of the provider's public key set to validate signature of the JWT. See
   * [OpenID
   * Discovery](https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata).
   * Optional if the key set document:
   *  - can be retrieved from
   *    [OpenID
   *    Discovery](https://openid.net/specs/openid-connect-discovery-1_0.html)
   *    of the issuer.
   *  - can be inferred from the email domain of the issuer (e.g. a Google
   *  service account).
   *
   * Example: https://www.googleapis.com/oauth2/v1/certs
   * </pre>
   *
   * <code>string jwks_uri = 3;</code>
   *
   * @return The jwksUri.
   */
  java.lang.String getJwksUri();
  /**
   *
   *
   * <pre>
   * URL of the provider's public key set to validate signature of the JWT. See
   * [OpenID
   * Discovery](https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata).
   * Optional if the key set document:
   *  - can be retrieved from
   *    [OpenID
   *    Discovery](https://openid.net/specs/openid-connect-discovery-1_0.html)
   *    of the issuer.
   *  - can be inferred from the email domain of the issuer (e.g. a Google
   *  service account).
   *
   * Example: https://www.googleapis.com/oauth2/v1/certs
   * </pre>
   *
   * <code>string jwks_uri = 3;</code>
   *
   * @return The bytes for jwksUri.
   */
  com.google.protobuf.ByteString getJwksUriBytes();

  /**
   *
   *
   * <pre>
   * The list of JWT
   * [audiences](https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-32#section-4.1.3).
   * that are allowed to access. A JWT containing any of these audiences will
   * be accepted. When this setting is absent, JWTs with audiences:
   *   - "https://[service.name]/[google.protobuf.Api.name]"
   *   - "https://[service.name]/"
   * will be accepted.
   * For example, if no audiences are in the setting, LibraryService API will
   * accept JWTs with the following audiences:
   *   -
   *   https://library-example.googleapis.com/google.example.library.v1.LibraryService
   *   - https://library-example.googleapis.com/
   *
   * Example:
   *
   *     audiences: bookstore_android.apps.googleusercontent.com,
   *                bookstore_web.apps.googleusercontent.com
   * </pre>
   *
   * <code>string audiences = 4;</code>
   *
   * @return The audiences.
   */
  java.lang.String getAudiences();
  /**
   *
   *
   * <pre>
   * The list of JWT
   * [audiences](https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-32#section-4.1.3).
   * that are allowed to access. A JWT containing any of these audiences will
   * be accepted. When this setting is absent, JWTs with audiences:
   *   - "https://[service.name]/[google.protobuf.Api.name]"
   *   - "https://[service.name]/"
   * will be accepted.
   * For example, if no audiences are in the setting, LibraryService API will
   * accept JWTs with the following audiences:
   *   -
   *   https://library-example.googleapis.com/google.example.library.v1.LibraryService
   *   - https://library-example.googleapis.com/
   *
   * Example:
   *
   *     audiences: bookstore_android.apps.googleusercontent.com,
   *                bookstore_web.apps.googleusercontent.com
   * </pre>
   *
   * <code>string audiences = 4;</code>
   *
   * @return The bytes for audiences.
   */
  com.google.protobuf.ByteString getAudiencesBytes();

  /**
   *
   *
   * <pre>
   * Redirect URL if JWT token is required but not present or is expired.
   * Implement authorizationUrl of securityDefinitions in OpenAPI spec.
   * </pre>
   *
   * <code>string authorization_url = 5;</code>
   *
   * @return The authorizationUrl.
   */
  java.lang.String getAuthorizationUrl();
  /**
   *
   *
   * <pre>
   * Redirect URL if JWT token is required but not present or is expired.
   * Implement authorizationUrl of securityDefinitions in OpenAPI spec.
   * </pre>
   *
   * <code>string authorization_url = 5;</code>
   *
   * @return The bytes for authorizationUrl.
   */
  com.google.protobuf.ByteString getAuthorizationUrlBytes();

  /**
   *
   *
   * <pre>
   * Defines the locations to extract the JWT.  For now it is only used by the
   * Cloud Endpoints to store the OpenAPI extension [x-google-jwt-locations]
   * (https://cloud.google.com/endpoints/docs/openapi/openapi-extensions#x-google-jwt-locations)
   *
   * JWT locations can be one of HTTP headers, URL query parameters or
   * cookies. The rule is that the first match wins.
   *
   * If not specified,  default to use following 3 locations:
   *    1) Authorization: Bearer
   *    2) x-goog-iap-jwt-assertion
   *    3) access_token query parameter
   *
   * Default locations can be specified as followings:
   *    jwt_locations:
   *    - header: Authorization
   *      value_prefix: "Bearer "
   *    - header: x-goog-iap-jwt-assertion
   *    - query: access_token
   * </pre>
   *
   * <code>repeated .google.api.JwtLocation jwt_locations = 6;</code>
   */
  java.util.List<com.google.api.JwtLocation> getJwtLocationsList();
  /**
   *
   *
   * <pre>
   * Defines the locations to extract the JWT.  For now it is only used by the
   * Cloud Endpoints to store the OpenAPI extension [x-google-jwt-locations]
   * (https://cloud.google.com/endpoints/docs/openapi/openapi-extensions#x-google-jwt-locations)
   *
   * JWT locations can be one of HTTP headers, URL query parameters or
   * cookies. The rule is that the first match wins.
   *
   * If not specified,  default to use following 3 locations:
   *    1) Authorization: Bearer
   *    2) x-goog-iap-jwt-assertion
   *    3) access_token query parameter
   *
   * Default locations can be specified as followings:
   *    jwt_locations:
   *    - header: Authorization
   *      value_prefix: "Bearer "
   *    - header: x-goog-iap-jwt-assertion
   *    - query: access_token
   * </pre>
   *
   * <code>repeated .google.api.JwtLocation jwt_locations = 6;</code>
   */
  com.google.api.JwtLocation getJwtLocations(int index);
  /**
   *
   *
   * <pre>
   * Defines the locations to extract the JWT.  For now it is only used by the
   * Cloud Endpoints to store the OpenAPI extension [x-google-jwt-locations]
   * (https://cloud.google.com/endpoints/docs/openapi/openapi-extensions#x-google-jwt-locations)
   *
   * JWT locations can be one of HTTP headers, URL query parameters or
   * cookies. The rule is that the first match wins.
   *
   * If not specified,  default to use following 3 locations:
   *    1) Authorization: Bearer
   *    2) x-goog-iap-jwt-assertion
   *    3) access_token query parameter
   *
   * Default locations can be specified as followings:
   *    jwt_locations:
   *    - header: Authorization
   *      value_prefix: "Bearer "
   *    - header: x-goog-iap-jwt-assertion
   *    - query: access_token
   * </pre>
   *
   * <code>repeated .google.api.JwtLocation jwt_locations = 6;</code>
   */
  int getJwtLocationsCount();
  /**
   *
   *
   * <pre>
   * Defines the locations to extract the JWT.  For now it is only used by the
   * Cloud Endpoints to store the OpenAPI extension [x-google-jwt-locations]
   * (https://cloud.google.com/endpoints/docs/openapi/openapi-extensions#x-google-jwt-locations)
   *
   * JWT locations can be one of HTTP headers, URL query parameters or
   * cookies. The rule is that the first match wins.
   *
   * If not specified,  default to use following 3 locations:
   *    1) Authorization: Bearer
   *    2) x-goog-iap-jwt-assertion
   *    3) access_token query parameter
   *
   * Default locations can be specified as followings:
   *    jwt_locations:
   *    - header: Authorization
   *      value_prefix: "Bearer "
   *    - header: x-goog-iap-jwt-assertion
   *    - query: access_token
   * </pre>
   *
   * <code>repeated .google.api.JwtLocation jwt_locations = 6;</code>
   */
  java.util.List<? extends com.google.api.JwtLocationOrBuilder> getJwtLocationsOrBuilderList();
  /**
   *
   *
   * <pre>
   * Defines the locations to extract the JWT.  For now it is only used by the
   * Cloud Endpoints to store the OpenAPI extension [x-google-jwt-locations]
   * (https://cloud.google.com/endpoints/docs/openapi/openapi-extensions#x-google-jwt-locations)
   *
   * JWT locations can be one of HTTP headers, URL query parameters or
   * cookies. The rule is that the first match wins.
   *
   * If not specified,  default to use following 3 locations:
   *    1) Authorization: Bearer
   *    2) x-goog-iap-jwt-assertion
   *    3) access_token query parameter
   *
   * Default locations can be specified as followings:
   *    jwt_locations:
   *    - header: Authorization
   *      value_prefix: "Bearer "
   *    - header: x-goog-iap-jwt-assertion
   *    - query: access_token
   * </pre>
   *
   * <code>repeated .google.api.JwtLocation jwt_locations = 6;</code>
   */
  com.google.api.JwtLocationOrBuilder getJwtLocationsOrBuilder(int index);
}