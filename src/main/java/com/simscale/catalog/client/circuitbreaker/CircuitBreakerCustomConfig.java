package com.simscale.catalog.client.circuitbreaker;

public class CircuitBreakerCustomConfig implements CircuitBreakerConfig{

    private final int failureThreshold;

    private final long timeToAllowRequests;

    public CircuitBreakerCustomConfig(long timeToAllowRequests, int failureThreshold) {
        this.timeToAllowRequests = timeToAllowRequests;
        this.failureThreshold = failureThreshold;
    }

    @Override
    public int getFailureThreshold() {
        return failureThreshold;
    }

    @Override
    public long getTimeToAllowRequests() {
        return timeToAllowRequests;
    }
}
