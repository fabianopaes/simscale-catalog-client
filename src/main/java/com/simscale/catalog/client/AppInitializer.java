package com.simscale.catalog.client;

import com.simscale.catalog.client.http.WSClientImpl;
import com.simscale.catalog.client.loadbalance.JobFailureDummyHandler;
import com.simscale.catalog.client.loadbalance.LoadBalanceAlgorithm;
import com.simscale.catalog.client.loadbalance.LoadBalanceFactory;
import com.simscale.catalog.client.loadbalance.LoadBalanceInfo;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class AppInitializer {


    public static void main(String[] args) throws InterruptedException, IOException {

        // Here comes all servers that we have
        List<Server> servers = Arrays.asList(
                new Server("http://localhost", 9000,  "GED"),
                new Server("http://localhost", 9001,  "Patient-Data"),
                new Server("http://localhost",  9002, "Report")
        );

        // Here comes our jobs that we have to balance between our servers
        List<Job> jobs = Arrays.asList(
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null),
                new Job("/v1/ping",HttpMethod.GET, null)
        );


       LoadBalanceInfo info = new LoadBalanceInfo(
               new ArrayDeque<>(servers),
               new WSClientImpl()
       );

       ExecutionManager executionManager = new ExecutionManager(
               LoadBalanceFactory.getInstance(LoadBalanceAlgorithm.ROUND_ROBIN, info),
               new JobFailureDummyHandler()
       );

        executionManager.run(jobs);


        System.exit(NumberUtils.INTEGER_ZERO);

    }



}
