package com.simscale.catalog.catalog.loadbalance;

import com.simscale.catalog.client.http.WSClient;
import com.simscale.catalog.client.loadbalance.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;

import static org.junit.Assert.assertTrue;

public class LoadBalanceFactoryTest {

    private LoadBalanceInfo lbi;

    @Before
    public void initialize(){
        lbi = new LoadBalanceInfo( new ArrayDeque<>(), Mockito.mock(WSClient.class));
    }

    @Test(expected=IllegalArgumentException.class)
    public void whenUnknownAlgorithmIsSelectedThrowIllegalArgumentException(){
        assertInstance(LoadBalanceFactory.getInstance(null, lbi));
    }

    @Test
    public void returnsRoundRobinInstanceWhenThisAlgorithmIsSelected(){
        assertInstance(LoadBalanceFactory.getInstance(LoadBalanceAlgorithm.ROUND_ROBIN, lbi));
    }

    @Test
    public void returnsRoundRobinInstanceWhenThisNoAlgorithmIsSelected(){
        assertInstance(LoadBalanceFactory.getInstance(lbi));

    }

    private void assertInstance(LoadBalance lb){
        assertTrue(lb instanceof LoadBalanceRoundRobin);
    }

}
