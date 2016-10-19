package com.simscale.catalog.client.circuitbreaker;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.concurrent.TimeUnit;

public class DefaultCircuitBreakerConfig implements CircuitBreakerConfig{

    @Override
    public long getTimeToAllowRequests() {
        return TimeUnit.SECONDS.toMillis(30);
    }

    @Override
    public int getFailureThreshold() {
        return NumberUtils.INTEGER_ONE.intValue();
    }

    @Override
    public long getTimeOut() {
        return 1000;
    }

    @Override
    public int getRetryInterval() {
        return 0;
    }
}
