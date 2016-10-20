package com.simscale.catalog.catalog.loadbalance;

import com.simscale.catalog.client.circuitbreaker.CircuitBreaker;
import com.simscale.catalog.client.domain.Server;
import com.simscale.catalog.client.http.WSClient;
import com.simscale.catalog.client.loadbalance.LoadBalanceInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static org.junit.Assert.assertEquals;

public class LoadBalanceInfoTest {

    private Deque<Server> servers;

    private WSClient client;

    @Before
    public void initialize(){
        client = Mockito.mock(WSClient.class);
        servers = new ArrayDeque<Server>(Arrays.asList(
                new Server("http://localhost:8081", "server-1",
                           Mockito.mock(CircuitBreaker.class))
        ));
    }

    @Test
    public void makeSureGetClientReturnRightValue(){
        LoadBalanceInfo info = new LoadBalanceInfo(servers, client);
        assertEquals(client, info.getWsClient());
    }

    @Test
    public void makeSureGetServerReturnRightValue(){
        LoadBalanceInfo info = new LoadBalanceInfo(servers, client);
        assertEquals(servers, info.getServers());
    }
}
