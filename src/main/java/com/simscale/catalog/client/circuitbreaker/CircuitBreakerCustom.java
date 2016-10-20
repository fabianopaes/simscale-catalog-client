package com.simscale.catalog.client.circuitbreaker;

import org.apache.commons.lang3.ObjectUtils;

public class CircuitBreakerCustom implements CircuitBreaker {

    private int failureCount;

    private CircuitBreakerState state;

    private long lastOpenTimestamp = Long.MAX_VALUE;

    private CircuitBreakerConfig config;

    private CircuitBreakerMetrics metrics;

    public CircuitBreakerCustom() {
        this.state = CircuitBreakerState.CLOSED;
        this.config = new DefaultCircuitBreakerConfig();
        this.metrics = new CircuitBreakerMetrics();
    }

    public CircuitBreakerCustom(CircuitBreakerConfig config) {
        this();
        this.config = config;
        this.state = CircuitBreakerState.CLOSED;
        this.metrics = new CircuitBreakerMetrics();
    }

    @Override
    public boolean isCallable() {
        return isClosed() || isHalfOpen();
    }

    public CircuitBreakerState getState() {

        if (ObjectUtils.equals(CircuitBreakerState.OPEN, state) && canBeClosed()){
            state = CircuitBreakerState.HALF_OPEN;
        }

        return state;
    }

    @Override
    public boolean isClosed() {
        return ObjectUtils.equals(CircuitBreakerState.CLOSED, getState()) ;
    }

    @Override
    public boolean isHalfOpen() {
        return ObjectUtils.equals(CircuitBreakerState.HALF_OPEN, getState()) ;
    }

    @Override
    public boolean isOpen() {
        return ObjectUtils.equals(CircuitBreakerState.OPEN, getState()) ;
    }

    @Override
    public void onSuccess() {

        //It could be in half open state
        if (!isClosed()) {
            close();
        }

        metrics.increaseSuccessCount();
    }

    @Override
    public void onFailure() {
        increaseFailureCount();
        if(ObjectUtils.equals(failureCount, config.getFailureThreshold())){
            this.open();
        }
    }

    @Override
    public CircuitBreakerMetrics getCircuitBreakerMetrics() {
        return this.metrics;
    }

    private void close(){
        setState(CircuitBreakerState.CLOSED);
        setFailureCount(0);
    }


    private void open(){
        setState(CircuitBreakerState.OPEN);
        metrics.increaseFailureCount();
        lastOpenTimestamp = System.currentTimeMillis();
    }

    private void setState(CircuitBreakerState state){
        this.state = state;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    private void increaseFailureCount(){
        this.failureCount++;
    }

    private boolean canBeClosed(){
        Long now = System.currentTimeMillis();
/*
        System.out.println("Diff: " + String.valueOf(now  - lastOpenTimestamp) + " config to halfopen: " + config.getTimeToAllowRequests());
*/
        return now  - lastOpenTimestamp >= config.getTimeToAllowRequests();
    }

}
