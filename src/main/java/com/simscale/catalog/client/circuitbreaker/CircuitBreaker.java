package com.simscale.catalog.client.circuitbreaker;

public interface CircuitBreaker {

    boolean isCallable();

    void tripBreaker(int timeout);

    void tripBreaker();

    void reset();

    boolean isClosed();

    boolean isOpen();

    boolean isHalfOpen();

    void onSuccess();

    int onFailure();

    int onFailure(String type);

    long getLastOpenTimestamp = 0;

    String getName();

    int getSuccessCount();

}
