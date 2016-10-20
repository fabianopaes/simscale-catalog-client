package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.circuitbreaker.CircuitBreaker;
import com.simscale.catalog.client.domain.JobRequest;
import com.simscale.catalog.client.domain.Server;
import com.simscale.catalog.client.helper.JobRequestGenerator;
import com.simscale.catalog.client.helper.JobRequestGeneratorCustomConfig;
import com.simscale.catalog.client.http.WSClient;
import com.simscale.catalog.client.http.WSResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
                new Server("http://localhost:8081", "server-1", cb),
                new Server("http://localhost:8082", "server-2", cb),
                new Server("http://localhost:8083", "server-3", cb),
                new Server( "http://localhost:8084", "server-4",cb)
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
        Server available = new Server("http://localhost:8080", "server-1", cb2);
        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(
                new Server( "http://localhost:8080", "server-1",cb),
                available,
                new Server("http://localhost:8080", "server-1",  cb),
                new Server("http://localhost:8080", "server-1", cb)
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

    @Test
    public void whenServerStopRespondingCallCircuitBreakerToOpen(){

        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        when(cb.isCallable()).thenReturn(true);

        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(
                new Server("http://localhost:8081", "server-1", cb),
                new Server("http://localhost:8083", "server-3", cb),
                new Server( "http://localhost:8082", "server-2",cb),
                new Server("http://localhost:8084", "server-4", cb),
                new Server("http://localhost:8085", "server-5",  cb)
        ));

        LoadBalance loadBalance = getLBInstance(servers);
        List<JobRequest> requests = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 15));

        when(client.doPerform(any(), any(), any())).thenReturn(WSResponse.empty());

        requests.parallelStream().forEachOrdered(request -> {
            WSResponse response = loadBalance.execute(request);
        });

        verify(cb, times(requests.size())).onFailure();

    }

    @Test
    public void whenServerResponseSuccessfulCallCircuitBreakerOnSuccess(){

        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        when(cb.isCallable()).thenReturn(true);

        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(
                new Server("http://localhost:8081", "server-1", cb),
                new Server("http://localhost:8083", "server-3", cb),
                new Server( "http://localhost:8082", "server-2",cb),
                new Server("http://localhost:8084", "server-4", cb),
                new Server("http://localhost:8085", "server-5",  cb)
        ));

        LoadBalance loadBalance = getLBInstance(servers);
        List<JobRequest> requests = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 15));

        when(client.doPerform(any(), any(), any())).thenReturn(WSResponse.ok());

        requests.parallelStream().forEachOrdered(request -> {
            WSResponse response = loadBalance.execute(request);
        });

        verify(cb, times(requests.size())).onSuccess();
    }




    @Ignore
    public void forEachIterationRelocateTheServerAtTheEndOfDeque(){

        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        when(cb.isCallable()).thenReturn(true);

        Server[] serverArray = new Server[] {
                new Server("http://localhost:8081", "server-a", cb),
                new Server("http://localhost:8083", "server-b", cb),
                new Server( "http://localhost:8082", "server-c",cb),
                new Server("http://localhost:8084", "server-d", cb)
        };

        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(serverArray));

        LoadBalance loadBalance = getLBInstance(servers);
        List<JobRequest> requests = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 15));

        when(client.doPerform(any(), any(), any())).thenReturn(new WSResponse("empty-content", 20));

        int index = 0;
        int iteration = 1;

        for(JobRequest request : requests){
/*
            WSResponse response = loadBalance.execute(request);
            if(useOriginal){
                verifyServerRelocate(servers, serverArray[index], serverArray[serverArray.length]);
            }else {
                verifyServerRelocate(servers, serverArray[index + 1], serverArray[index]);
            }


            if(ObjectUtils.equals(index, servers.size() - 1)){
                index = 0;
                useOriginal = true;
            }else {
                useOriginal = false;
                index ++;
            }*/

            WSResponse response = loadBalance.execute(request);

            verifyServerRelocate(servers, serverArray[index + 1], serverArray[index]);

            index ++;
            if(ObjectUtils.equals(index, servers.size() - 1)){
                index = 0;
                iteration ++;
            }
        }


        verify(cb, times(requests.size())).onSuccess();
    }

    @Test
    public void whenTheresNoAvailableServerReturnsWSResponselEmpty(){
        CircuitBreaker cb = Mockito.mock(CircuitBreaker.class);
        Deque<Server> servers = new ArrayDeque<Server>(Arrays.asList(
                new Server("http://localhost:8081", "server-1", cb),
                new Server("http://localhost:8082", "server-2", cb),
                new Server("http://localhost:8083", "server-3", cb),
                new Server( "http://localhost:8084", "server-4",cb)
        ));

        LoadBalance loadBalance = getLBInstance(servers);
        when(cb.isCallable()).thenReturn(false);

        List<JobRequest> requests = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 10));

        requests.parallelStream().forEach( request ->{
            assertEquals(WSResponse.empty().getCode(), loadBalance.execute(request).getCode());
            assertEquals(WSResponse.empty().getContent(), loadBalance.execute(request).getContent());
        });
    }

    private void verifyServerRelocate(Deque<Server> servers, Server shouldBeFirst, Server shouldBeLast){
        assertEquals(servers.getLast().getName(), shouldBeLast.getName());
        assertEquals(servers.getFirst().getName(), shouldBeFirst.getName());
    }

    private LoadBalance getLBInstance(Deque<Server> servers){
        return new LoadBalanceRoundRobin(new LoadBalanceInfo(servers, client));
    }

}
