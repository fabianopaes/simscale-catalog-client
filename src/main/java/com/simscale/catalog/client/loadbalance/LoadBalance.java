package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.domain.JobRequest;
import com.simscale.catalog.client.domain.Server;
import com.simscale.catalog.client.http.WSResponse;

import java.util.Optional;

public interface LoadBalance {

    public Optional<Server> getAvailableServer();

    public WSResponse execute(JobRequest jobRequest);

    public void prettyPrintExecutionMetrics();

}
