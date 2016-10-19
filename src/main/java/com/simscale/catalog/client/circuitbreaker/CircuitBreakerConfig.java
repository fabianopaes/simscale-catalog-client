package com.simscale.catalog.client.circuitbreaker;

public interface CircuitBreakerConfig {

    public long getTimeOut();

    public int getFailureThreshold();

    public long getTimeToAllowRequests();

    public int getRetryInterval();

}
