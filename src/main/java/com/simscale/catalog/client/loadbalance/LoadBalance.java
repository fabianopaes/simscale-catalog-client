package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.Server;

import java.util.Deque;
import java.util.Optional;

public interface LoadBalance {

    public  Optional<Server> getAvailableServer(Deque<Server> servers);

}
