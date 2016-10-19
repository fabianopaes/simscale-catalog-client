package com.simscale.catalog.client.circuitbreaker;

import java.util.concurrent.atomic.AtomicInteger;

public class CircuitBreakerMetrics {

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
