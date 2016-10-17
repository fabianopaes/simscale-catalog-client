package com.simscale.catalog.client;

import com.simscale.catalog.client.circuitbreaker.CircuitBreakerCustom;
import com.simscale.catalog.client.http.WSClientImpl;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class AppInitializer {


    public static void main(String[] args) throws InterruptedException {

        // Here comes all servers that we have
        List<Server> servers = Arrays.asList(
                new Server("http://localhost:9000", new CircuitBreakerCustom("GED")),
                new Server("http://localhost:9001", new CircuitBreakerCustom("Patient-Data")),
                new Server("http://localhost:9002", new CircuitBreakerCustom("Report"))
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

        ExecutionManager executionManager = new ExecutionManager(
                new ArrayDeque<>(servers),
                jobs,
                new WSClientImpl()
        );

        int index = 1;
        while(index <= 15){
            executionManager.execute();
            index++;
            Thread.sleep(5 * 1000);
        }

/*
        executionManager.execute();
*/


        System.exit(NumberUtils.INTEGER_ZERO);

    }



}
