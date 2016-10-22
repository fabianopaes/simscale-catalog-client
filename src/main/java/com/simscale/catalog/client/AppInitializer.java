package com.simscale.catalog.client;

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
import java.util.*;

public class AppInitializer {

    public static void main(String[] args) {

        LoadBalanceInfo info = new LoadBalanceInfo(
                AppInitializer.readServers(), new WSClientImpl()
        );

        ExecutionManager executionManager = new ExecutionManagerImpl(
                LoadBalanceFactory.getInstance(LoadBalanceAlgorithm.ROUND_ROBIN, info),
                new JobFailureDummyHandler(), AppInitializer.readExecutionTime()
        );

        executionManager.run(JobRequestGenerator.build(new JobRequestGeneratorDefaultConfig()));

        executionManager.prettyPrintResults();

        System.exit(1);
    }

    public static Deque<Server> readServers() {

        System.out.println("");
        System.out.println("Please provide a list of servers like the given example(<host> <port> <name>)");
        System.out.println(" http://localhost 9000 server-1");
        System.out.println(" If the server does not have a port, please enter 0");
        System.out.println(" After entered all of servers, just type exit");
        Boolean keepReading = true;

        Scanner scanner = new Scanner(System.in);
        Deque<Server> servers = new ArrayDeque<>();

        while (keepReading) {

            System.out.print("Enter the server : ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {

                if(servers.isEmpty()){
                    System.out.println("Stopping the program");
                    System.exit(0);
                }

                keepReading = false;
                System.out.println("As we already know all severs about to initialize the app");

            } else{
                String[] params = input.split(" ");
                if(params.length != 3){
                    System.out.println("Missing a parameter, remember <server> <port> <name>. Try it again");
                } else {
                    try{
                        String host = params[0];
                        String name = params[2];
                        Integer port = Integer.valueOf(params[1]);
                        servers.add(new Server(host, port, name));
                    }catch(NumberFormatException ex){
                        System.out.println("wrong value for port. Try it again");
                    }
                }
            }
        }

        return servers;
    }

    public static Integer readExecutionTime(){
        Scanner scanner = new Scanner(System.in);
        System.out.printf("");
        System.out.print("Enter the time (in minute) which the application might be running: ");
        Boolean keepReading = true;
        Integer time = null;
        while (keepReading) {
            try{
                String input = scanner.nextLine();
                time = Integer.valueOf(input);
                keepReading = false;
            }catch(NumberFormatException ex){
                System.out.println("wrong value for time. Try it again");
            }
        }
        return time;
    }

}



