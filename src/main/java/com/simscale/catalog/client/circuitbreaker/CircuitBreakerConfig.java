package com.simscale.catalog.client.circuitbreaker;

public interface CircuitBreakerConfig {

    public int getFailureThreshold();

    public long getTimeToAllowRequests();

}
