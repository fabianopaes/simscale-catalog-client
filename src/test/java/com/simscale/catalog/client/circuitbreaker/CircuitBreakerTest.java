package com.simscale.catalog.client.circuitbreaker;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class CircuitBreakerTest {

    private CircuitBreakerCustomConfig config;
    private CircuitBreaker cb;
    private int timeToAllowRequests;

    @Before
    public void onSetup() {
        timeToAllowRequests = 1;
        config = new CircuitBreakerCustomConfig(TimeUnit.SECONDS.toMillis(timeToAllowRequests), 1);
        cb = new CircuitBreakerCustom(config);
    }

    @Test
    public void whenCreateACircuitBreakerItShouldBeClosed() {
        assertEquals(cb.getState(), CircuitBreakerState.CLOSED);
    }

    @Test
    public void whenCircuitBreakerIsOpenedItIsNotCallable(){
        cb.onFailure();
        assertFalse(cb.isCallable());
    }

    @Test
    public void whenCircuitBreakerIsClosedItIsCallable(){
        assertTrue(cb.isCallable());
    }

    @Test
    public void whenCircuitBreakerIsHalfOpenItIsCallable(){
        cb.onFailure();
        try {
            //To force circuit breaker change its state to HalfOpen
            Thread.sleep(TimeUnit.SECONDS.toMillis(timeToAllowRequests + 1));
        } catch (InterruptedException e) {
            fail();
        }
        assertTrue(cb.isCallable());
    }

    @Test
    public void whenOnFailureIsCalledCircuitBreakerIsOpened(){
        cb.onFailure();
        assertEquals(CircuitBreakerState.OPEN, cb.getState());
        assertTrue(cb.isOpen());
        assertEquals(new AtomicInteger(1).intValue(), cb.getCircuitBreakerMetrics().getFailureCount().intValue());
    }

    @Test
    public void whenOnSuccessIsCalledCircuitBreakerIsClosed(){
        cb.onSuccess();
        assertEquals(CircuitBreakerState.CLOSED, cb.getState());
        assertTrue(cb.isClosed());
        assertEquals(new AtomicInteger(1).intValue(), cb.getCircuitBreakerMetrics().getSuccessCount().intValue());
    }

    @Test
    public void whenCBIsHalOpenAndExecutionWasSuccessFullCloseCB(){
        cb.onFailure();
        try {
            //To force circuit breaker change its state to HalfOpen
            Thread.sleep(TimeUnit.SECONDS.toMillis(timeToAllowRequests + 1));
        } catch (InterruptedException e) {
            fail();
        }
        assertEquals(CircuitBreakerState.HALF_OPEN, cb.getState());
        cb.onSuccess();
        assertTrue(cb.isCallable());
        assertEquals(CircuitBreakerState.CLOSED, cb.getState());
        assertFalse(cb.isHalfOpen());
        assertTrue(cb.isClosed());
    }

    @Test
    public void onlyOpenCircuitBreakerWhenFailureThresholdGotExceeded(){

        CircuitBreakerCustomConfig config = new CircuitBreakerCustomConfig(TimeUnit.SECONDS.toMillis(timeToAllowRequests), 10);
        CircuitBreaker cbCustom = new CircuitBreakerCustom(config);

        int max = 15;

        for(int i=1; i < max; i++ ){
            cbCustom.onFailure();
            assertEquals(i >=  config.getFailureThreshold(), cbCustom.isOpen());
        }
    }
}
