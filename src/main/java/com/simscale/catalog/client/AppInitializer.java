package com.simscale.catalog.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simscale.catalog.client.domain.JobRequest;
import com.simscale.catalog.client.domain.Server;
import com.simscale.catalog.client.helper.JobRequestGenerator;
import com.simscale.catalog.client.helper.JobRequestGeneratorDefaultConfig;
import com.simscale.catalog.client.http.WSClientImpl;
import com.simscale.catalog.client.loadbalance.JobFailureDummyHandler;
import com.simscale.catalog.client.loadbalance.LoadBalanceAlgorithm;
import com.simscale.catalog.client.loadbalance.LoadBalanceFactory;
import com.simscale.catalog.client.loadbalance.LoadBalanceInfo;
import com.simscale.catalog.client.service.ExecutionManager;
import com.simscale.catalog.client.service.ExecutionManagerImpl;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AppInitializer {

    public static void main(String[] args) {

        if(args.length == 0){
            System.out.println("You have to provider at least the servers config path and as a plus the requests " +
                    "config that we will make");
            System.exit(1);
        }

        if (!args[0].endsWith(".json")) {
                System.out.println("servers.config should be a json file. Received " + args[0]);
                System.exit(1);
        }

        File serversConfig = new File(args[0]);
        if(!serversConfig.exists()){
                System.out.println("The servers.config path does not exists in your owm machine");
                System.exit(1);
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Server> servers = new ArrayList<>();
        try{
            servers = Arrays.asList(mapper.readValue(serversConfig, Server[].class));
        }catch(IOException ex){
            System.out.println("Your server config file is wrong. Please take a look at servers.json at root " +
                    "of the project as example");
            System.exit(1);
        }

        List<JobRequest> jobRequests = new ArrayList<>();

        if (args.length < 2) {
            System.out.println("You have not provided the requests config. We will run some random requests");
            jobRequests = JobRequestGenerator.build(new JobRequestGeneratorDefaultConfig());
        } else {
                File requestsConfig = new File(args[1]);
                if(!requestsConfig.exists()){
                        System.out.println("The requests.config path does not exists in your owm machine");
                        System.exit(1);
                }

                if (!args[1].endsWith(".json")) {
                    System.out.println("requests.config should be a json file. Received " + args[1]);
                    System.exit(1);
                }
                try{
                    jobRequests = Arrays.asList(mapper.readValue(serversConfig, JobRequest[].class));
                }catch(IOException ex){
                    System.out.println("Your requests config file is wrong. Please take a look at requests.json at root " +
                            "of the project as example");
                    System.exit(1);
                }
        }

        LoadBalanceInfo info = new LoadBalanceInfo(
                new ArrayDeque<>(servers),
                new WSClientImpl()
        );

        ExecutionManager executionManager = new ExecutionManagerImpl(
                LoadBalanceFactory.getInstance(LoadBalanceAlgorithm.ROUND_ROBIN, info),
                new JobFailureDummyHandler(), 1
        );

        executionManager.run(jobRequests);

        executionManager.prettyPrintResults();
    }

}



