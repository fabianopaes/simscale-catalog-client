package com.simscale.catalog.client.circuitbreaker;

public interface CircuitBreaker {

    public boolean isCallable();

    public CircuitBreakerState getState();

    public boolean isClosed();

    public boolean isOpen();

    public boolean isHalfOpen();

    public void onSuccess();

    public void onFailure();

    public CircuitBreakerMetrics getCircuitBreakerMetrics();

}
