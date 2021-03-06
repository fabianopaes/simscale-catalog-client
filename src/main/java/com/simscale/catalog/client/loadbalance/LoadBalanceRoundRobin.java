package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.domain.JobRequest;
import com.simscale.catalog.client.domain.Server;
import com.simscale.catalog.client.http.WSClient;
import com.simscale.catalog.client.http.WSResponse;

import java.util.Deque;
import java.util.Optional;

public class LoadBalanceRoundRobin implements LoadBalance{

    private final WSClient client;

    private Deque<Server> servers;

    public LoadBalanceRoundRobin(LoadBalanceInfo info) {
        this.client = info.getWsClient();
        this.servers = info.getServers();
    }

    @Override
    public Optional<Server> getAvailableServer() {

        if(servers.isEmpty()){
            return Optional.empty();
        }

        System.out.println("Server's snapshot");
        for(Server server : servers){
            System.out.println(server.getName() + " " +
            server.getCircuitBreaker().isCallable());
        }

        Optional<Server> server = servers
                .parallelStream()
                .filter(s -> s.getCircuitBreaker().isCallable())
                .findFirst();

        if(server.isPresent()){
            return server;
        }

        return Optional.empty();
    }

    @Override
    public WSResponse execute(JobRequest jobRequest) {

        Optional<Server> serverOptional = getAvailableServer();

        if(! serverOptional.isPresent()){
            return WSResponse.empty();
        }

        Server server = serverOptional.get();

        System.out.println("Using " + server.getName());

        WSResponse response = client.doPerform(
                jobRequest.getMethod(),
                server.getEncondedUrl() + jobRequest.getEndpoint(),
                jobRequest.getUser()
        );

        if (response.isServerResponding()) {
            server.getCircuitBreaker().onSuccess();
        } else {
            System.out.println("Error on server " + server.getName() + ". Response: " + response);
            server.getCircuitBreaker().onFailure();
        }

        reallocatingServer(server);
        return response;
    }

    private void reallocatingServer(Server server){
        servers.remove(server);
        servers.addLast(server);
    }

    @Override
    public void prettyPrintExecutionMetrics() {
        System.out.println("******************  Metrics  **********************");
        servers.parallelStream().forEachOrdered(server -> {

            System.out.println("");
            System.out.println("Server Name: " + server.getName());
            System.out.println("Total Requests Success: " + server.getCircuitBreaker()
                    .getCircuitBreakerMetrics()
                    .getSuccessCount());
            System.out.println("Total Requests Failure: " + server.getCircuitBreaker()
                    .getCircuitBreakerMetrics()
                    .getFailureCount());

        });
        System.out.println("****************************************");
    }

}
