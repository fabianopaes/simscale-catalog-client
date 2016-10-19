package com.simscale.catalog.client.http;

import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

public interface WSHttpClientConfig {

    public AsyncHttpClientConfig CONNECTION_POOL_PER_HOST = new DefaultAsyncHttpClientConfig.Builder()
            .setMaxConnections(500)
            .setMaxConnectionsPerHost(200)
            .setPooledConnectionIdleTimeout(100)
            .setConnectionTtl(500)
            .build();
}
