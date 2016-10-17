package com.simscale.catalog.client;

import com.simscale.catalog.client.circuitbreaker.CircuitBreaker;

public class Server {

    private String url;
    private CircuitBreaker circuitBreaker;

    public Server(String url, CircuitBreaker circuitBreaker) {
        this.url = url;
        this.circuitBreaker = circuitBreaker;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }

    public void setCircuitBreaker(CircuitBreaker circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }
}
