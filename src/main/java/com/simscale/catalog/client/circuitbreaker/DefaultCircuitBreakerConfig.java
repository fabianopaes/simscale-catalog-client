package com.simscale.catalog.client.circuitbreaker;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.concurrent.TimeUnit;

public class DefaultCircuitBreakerConfig implements CircuitBreakerConfig{

    private final long timeToAllowRequests =
            TimeUnit.SECONDS.toMillis(30);

    private final int failureThreshold =
             NumberUtils.INTEGER_ONE.intValue();

    @Override
    public long getTimeToAllowRequests() {
        return timeToAllowRequests;
    }

    @Override
    public int getFailureThreshold() {
        return failureThreshold;
    }

}
