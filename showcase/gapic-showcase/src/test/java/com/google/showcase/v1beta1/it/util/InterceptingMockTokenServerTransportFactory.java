package com.google.showcase.v1beta1.it.util;

import com.google.api.client.http.HttpTransport;
import com.google.auth.oauth2.MockTokenServerTransport;
import com.google.auth.oauth2.MockTokenServerTransportFactory;

public class InterceptingMockTokenServerTransportFactory extends MockTokenServerTransportFactory {

    public InterceptingMockTokenServerTransport transport = new InterceptingMockTokenServerTransport();

    @Override
    public HttpTransport create() {
        return transport;
    }
}
