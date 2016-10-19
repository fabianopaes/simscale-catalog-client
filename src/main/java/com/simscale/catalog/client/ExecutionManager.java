package com.simscale.catalog.client;

import com.simscale.catalog.client.http.WSResponse;
import com.simscale.catalog.client.loadbalance.JobFailureHandler;
import com.simscale.catalog.client.loadbalance.LoadBalance;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class ExecutionManager {

    private LoadBalance loadBalance;

    private JobFailureHandler failureHandler;

    public ExecutionManager(LoadBalance loadBalance, JobFailureHandler failureHandler) {
        this.loadBalance = loadBalance;
        this.failureHandler = failureHandler;
    }

    public void run(List<Job> jobs){
        execute(new ArrayDeque<>(jobs));
        loadBalance.prettyPrintExecutionMetrics();
    }

    private void execute(Deque<Job> jobs){

        while(! jobs.isEmpty()){

            Job singleJob = jobs.iterator().next();
            try{

                ExecutionStatus status =  singleExecution(singleJob);
                jobs.remove(singleJob);

                if(ObjectUtils.equals(status, ExecutionStatus.RETRY)) {
                    jobs.addLast(singleJob);
                }

                if(ObjectUtils.equals(status, ExecutionStatus.SCHEDULE_RETRY)) {
                    failureHandler.process(singleJob);
                }

            }catch (Exception ex){
                System.out.println("Something unknown went wrong, we will save to process it later");
                jobs.remove(singleJob);
                failureHandler.process(singleJob);
                ex.printStackTrace();
            }
        }
    }

    private ExecutionStatus singleExecution(Job job){

        if( ! job.isOverRetryLimit()){

            WSResponse response = loadBalance.execute(job);

            if (response.isOkay()) {
                System.out.println(response);
                return ExecutionStatus.OK;
            } else {
                System.out.println("Something went wrong, we will process it later over again");
                job.increaseExecutionCount();
                return ExecutionStatus.RETRY;
            }

        } else {
            System.out.println("We have tried to process this job more than the limit  ... " + job.toString());
            System.out.println("Saving to process it later... " + job.toString());
            return ExecutionStatus.SCHEDULE_RETRY;
        }
    }


}
