package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.Job;
import com.simscale.catalog.client.Server;
import com.simscale.catalog.client.http.WSResponse;

import java.util.Deque;
import java.util.Optional;

public interface LoadBalance {

    public Optional<Server> getAvailableServer();

    public WSResponse execute(Job job);

    public void prettyPrintExecutionMetrics();

}
