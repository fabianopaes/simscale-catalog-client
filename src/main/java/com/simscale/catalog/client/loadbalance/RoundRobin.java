package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.Server;

import java.util.Deque;
import java.util.Optional;

public class RoundRobin implements LoadBalance{

    @Override
    public Optional<Server> getAvailableServer(Deque<Server> servers) {

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
            servers.remove(server.get());
            System.out.println("Choice server " + server.get().getName());
            return server;
        }

        return Optional.empty();
    }
}
