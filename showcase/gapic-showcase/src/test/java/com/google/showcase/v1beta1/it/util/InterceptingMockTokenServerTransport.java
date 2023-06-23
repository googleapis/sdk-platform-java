package com.google.showcase.v1beta1.it.util;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.util.StreamingContent;
import com.google.auth.TestUtils;
import com.google.auth.oauth2.MockTokenServerTransport;

import java.io.IOException;
import java.util.Map;

public class InterceptingMockTokenServerTransport extends MockTokenServerTransport {
    private MockLowLevelHttpRequest lastRequest;
    private static final JsonFactory JSON_FACTORY = new GsonFactory();

    @Override
    public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        MockLowLevelHttpRequest baseRequest = (MockLowLevelHttpRequest) super.buildRequest(method, url);
        lastRequest = baseRequest;
        return baseRequest;
    }

    public String getLastAudienceSent() {
        try {
            String contentString = lastRequest.getContentAsString();
            Map<String, String> query = TestUtils.parseQuery(contentString);
            String assertion = query.get("assertion");
            JsonWebSignature signature = JsonWebSignature.parse(JSON_FACTORY, assertion);
            String foundTargetAudience = (String) signature.getPayload().get("api_audience");
            return foundTargetAudience;
        } catch (Exception ex) {
            return null;
        }
    }
}
