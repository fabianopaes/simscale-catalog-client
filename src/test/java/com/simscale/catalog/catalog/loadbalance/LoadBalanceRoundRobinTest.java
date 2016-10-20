package com.simscale.catalog.catalog.loadbalance;

import com.simscale.catalog.client.circuitbreaker.CircuitBreaker;
import com.simscale.catalog.client.domain.Server;
import com.simscale.catalog.client.http.WSClient;
import com.simscale.catalog.client.loadbalance.LoadBalance;
import com.simscale.catalog.client.loadbalance.LoadBalanceInfo;
import com.simscale.catalog.client.loadbalance.LoadBalanceRoundRobin;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class LoadBalanceRoundRobinTest {

    private WSClient client;

    @Before
    public void initialize(){
        client = Mockito.mock(WSClient.class);
    }

    @Test
    public void whenDequeServerIsEmptyReturnsOptionalEmpty(){
        LoadBalance loadBalance = getLBInstance(new ArrayDeque<>());
        Optional<Server> server = loadBalance.getAvailableServer();
        assertEquals(Optional.empty(), server);
    }

    @Test
    public void whenTheresNoAvailableServerReturnsOptionalEmpty(){
        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(
                new Server("server-1", "http://localhost:8081", cb),
                new Server("server-2", "http://localhost:8082", cb),
                new Server("server-3", "http://localhost:8083", cb),
                new Server("server-4", "http://localhost:8084", cb)
        ));

        LoadBalance loadBalance = getLBInstance(servers);
        when(cb.isCallable()).thenReturn(false);
        Optional<Server> server = loadBalance.getAvailableServer();
        assertEquals(Optional.empty(), server);
    }

    @Test
    public void whenTheresOnlyOneAvailableAlwaysReturnIt(){
        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        CircuitBreaker cb2 = Mockito.mock(CircuitBreaker.class);
        Server available = new Server("server-1", "http://localhost:8080", cb2);
        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(
                new Server("server-1", "http://localhost:8080", cb),
                available,
                new Server("server-1", "http://localhost:8080", cb),
                new Server("server-1", "http://localhost:8080", cb)
        ));
        when(cb.isCallable()).thenReturn(false);
        when(cb2.isCallable()).thenReturn(true);
        LoadBalance loadBalance = getLBInstance(servers);

        for(int i = 0; i <= 10; i++){
            Optional<Server> server = loadBalance.getAvailableServer();
            assertEquals(available.getName(), server.get().getName());
            assertEquals(available.getCircuitBreaker(), server.get().getCircuitBreaker());
            assertEquals(available.getEncondedUrl(), server.get().getEncondedUrl());
            assertEquals(available.getPort(), server.get().getPort());
        }
    }

    @Test
    public void neverChoiceAnUnavailableServer(){

        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        when(cb.isCallable()).thenReturn(false);

        CircuitBreaker cb2 = Mockito.mock(CircuitBreaker.class);
        when(cb2.isCallable()).thenReturn(true);

        Server unavailable = new Server("http://localhost:8081", "server-1", cb);
        Server unavailable2 = new Server( "http://localhost:8082", "server-2",cb);

        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(
                unavailable,
                new Server("http://localhost:8083", "server-3", cb2),
                unavailable,
                new Server("http://localhost:8084", "server-4", cb2),
                new Server("http://localhost:8085", "server-5",  cb2)
        ));
        LoadBalance loadBalance = getLBInstance(servers);

        for(int i = 0; i <= 15; i++){
            Optional<Server> server = loadBalance.getAvailableServer();
            assertNotEquals(unavailable.getName(), server.get().getName());
            assertNotEquals(unavailable2.getName(), server.get().getName());
        }

    }

    private LoadBalance getLBInstance(Deque<Server> servers){
        return new LoadBalanceRoundRobin(new LoadBalanceInfo(servers, client));
    }

}
