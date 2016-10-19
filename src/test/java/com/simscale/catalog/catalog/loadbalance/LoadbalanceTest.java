package com.simscale.catalog.catalog.loadbalance;

import com.simscale.catalog.client.loadbalance.LoadBalance;

import static junit.framework.TestCase.assertEquals;

public class LoadbalanceTest {

    private LoadBalance loadBalance;
/*

    @Before
    public void initialize(){
        loadBalance = new LoadBalanceRoundRobin();
    }

    @Test
    public void whenDequeServerIsEmptyReturnsOptionalEmpty(){
        Optional<Server> server = loadBalance.getAvailableServer(new ArrayDeque<>());
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
        when(cb.isCallable()).thenReturn(false);
        Optional<Server> server = loadBalance.getAvailableServer(new ArrayDeque<>());
        assertEquals(Optional.empty(), server);
    }

    @Test
    public void whenTheresOnlyOneAvailableAlwaysReturnIt(){
        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        CircuitBreaker cb2 = Mockito.mock(CircuitBreaker.class);
        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(
                new Server("server-1", "http://localhost:8080", cb),
                new Server("server-1", "http://localhost:8080", cb2),
                new Server("server-1", "http://localhost:8080", cb),
                new Server("server-1", "http://localhost:8080", cb)
        ));
        when(cb.isCallable()).thenReturn(false);
        when(cb2.isCallable()).thenReturn(true);

        for(int i = 0; i <= 10; i++){
            Optional<Server> server = loadBalance.getAvailableServer(new ArrayDeque<>());
            assertEquals(Optional.empty(), server);
        }
    }
*/


}
