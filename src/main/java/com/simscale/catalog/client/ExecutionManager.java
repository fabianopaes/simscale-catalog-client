package com.simscale.catalog.client;

import com.simscale.catalog.client.http.WSClient;
import com.simscale.catalog.client.http.WSResponse;
import com.simscale.catalog.client.loadbalance.LoadBalance;
import com.simscale.catalog.client.loadbalance.RoundRobin;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class ExecutionManager {

    private final WSClient client;

    private Deque<Server> servers;

    private List<Job> jobs;

    private List<Job> reliableJobs;

    private List<Job> retryJobs;

    private LoadBalance loadBalance;

    public ExecutionManager(Deque<Server> servers, List<Job> jobs, WSClient wsClient) {
        this.servers = servers;
        this.jobs = jobs;
        this.client = wsClient;
        this.reliableJobs = new ArrayList<>();
        this.retryJobs = new ArrayList<>();
        this.loadBalance = new RoundRobin();
    }

    public void execute(){

        getJobs().parallelStream().forEachOrdered(job -> {

            System.out.println(" ");
            Optional<Server> serverOptional =
                    loadBalance.getAvailableServer(servers);

            if (serverOptional.isPresent()) {
                Server server = serverOptional.get();
                singleExecution(server, job);
                getServers().addLast(server);
            } else {
                System.out.println("There's no server available");
                reliableJobs.add(job);
            }

        });

        System.out.println("Reliable jobs " + reliableJobs.size());

        prettyPrintExecutionMetrics();
    }

    public Deque<Server> getServers() {
        return servers;
    }

    public void setServers(Deque<Server> servers) {
        this.servers = servers;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void prettyPrintExecutionMetrics(){
        System.out.println("******************  Metrics  **********************");
        System.out.println(" Execution count: " + getJobs().size());
        System.out.println(" Reliable  count: " + reliableJobs.size());
        System.out.println(" Retry  count: " + reliableJobs.size());
        getServers().parallelStream().forEachOrdered(server -> {
            System.out.println("");
            System.out.println("Server: " + server.getName());
            System.out.println("Server: " + server.getCircuitBreaker().getCircuitBreakerMetrics().getSuccessCount());
        });

        System.out.println("****************************************");

    }

    private void singleExecution(Server server, Job job){
        System.out.println("  " +
                        server.getName() + "   " +
                        job.getMethod().name() + " -> "+
                        server.getEncondedUrl() + job.getEndpoint()
        );

        WSResponse response = client.doPerform(
                job.getMethod().name(),
                server.getEncondedUrl() + job.getEndpoint(),
                job.getUser()
        );

        if (response.isOkay()) {
            System.out.println(response.getContent());
            server.getCircuitBreaker().onSuccess();
        } else {
            retryJobs.add(job);
            server.getCircuitBreaker().onFailure();
        }

    }

}
