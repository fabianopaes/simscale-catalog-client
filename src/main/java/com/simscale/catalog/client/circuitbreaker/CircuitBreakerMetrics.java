package com.simscale.catalog.client.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreakerMetrics {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerMetrics.class);

    private AtomicInteger successCount;

    private AtomicInteger failureCount;

    public CircuitBreakerMetrics() {
        this.successCount = new AtomicInteger();
        this.failureCount = new AtomicInteger();
    }

    public void increaseSuccessCount(){
        this.successCount.incrementAndGet();
    }

    public AtomicInteger getSuccessCount(){
        return this.successCount;
    }

    public AtomicInteger getFailureCount(){
        return this.failureCount;
    }

    public void increaseFailureCount(){
        this.failureCount.incrementAndGet();
    }


}
