package com.simscale.catalog.catalog.circuitbreaker;

import com.simscale.catalog.client.circuitbreaker.CircuitBreakerConfig;
import com.simscale.catalog.client.circuitbreaker.CircuitBreakerCustomConfig;
import com.simscale.catalog.client.circuitbreaker.DefaultCircuitBreakerConfig;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CircuitBreakerConfigTest {

    @Test
    public void makeSureDefaultConfigReturnsRightValueForFailureThreshold(){
        CircuitBreakerConfig config = new DefaultCircuitBreakerConfig();
        assertEquals(NumberUtils.INTEGER_ONE.intValue(), config.getFailureThreshold());
    }

    @Test
    public void makeSureDefaultConfigReturnsRightValueForTimeToAllowRequests(){
        CircuitBreakerConfig config = new DefaultCircuitBreakerConfig();
        assertEquals(TimeUnit.SECONDS.toMillis(30), config.getTimeToAllowRequests());
    }

    @Test
    public void makeSureCustomConfigReturnsRightValueForFailureThreshold(){
        CircuitBreakerConfig config = new CircuitBreakerCustomConfig(TimeUnit.SECONDS.toMillis(3), 100);
        assertEquals(TimeUnit.SECONDS.toMillis(3), config.getTimeToAllowRequests());
    }

    @Test
    public void makeSureCustomConfigReturnsRightValueForTimeToAllowRequests(){
        CircuitBreakerConfig config = new CircuitBreakerCustomConfig(TimeUnit.SECONDS.toMillis(3), 100);
        assertEquals(100, config.getFailureThreshold());
    }
}
