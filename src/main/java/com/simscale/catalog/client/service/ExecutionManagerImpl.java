package com.simscale.catalog.client.service;

import com.simscale.catalog.client.domain.JobRequest;
import com.simscale.catalog.client.http.WSResponse;
import com.simscale.catalog.client.loadbalance.JobFailureHandler;
import com.simscale.catalog.client.loadbalance.LoadBalance;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExecutionManagerImpl implements ExecutionManager{

    private LoadBalance loadBalance;

    private JobFailureHandler failureHandler;

    private Integer durationMinutes;

    public ExecutionManagerImpl(LoadBalance loadBalance, JobFailureHandler failureHandler, Integer durationMinutes) {
        this.loadBalance = loadBalance;
        this.failureHandler = failureHandler;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public void run(List<JobRequest> jobRequests){

        long begin = System.currentTimeMillis();
        long shouldEndAt = begin + getDurationInMillis();

        while(System.currentTimeMillis() < shouldEndAt){
            execute(new ArrayDeque<>(jobRequests));
        }
    }

    @Override
    public void prettyPrintResults() {
        loadBalance.prettyPrintExecutionMetrics();
    }

    public long getDurationInMillis(){
        return TimeUnit.MINUTES.toMillis(durationMinutes);
    }

    public void execute(Deque<JobRequest> jobRequests){

        while(! jobRequests.isEmpty()){

            JobRequest singleJobRequest = jobRequests.iterator().next();
            try{

                ExecutionStatus status =  singleExecution(singleJobRequest);
                jobRequests.remove(singleJobRequest);

                if(ObjectUtils.equals(status, ExecutionStatus.RETRY)) {
                    jobRequests.addLast(singleJobRequest);
                }

                if(ObjectUtils.equals(status, ExecutionStatus.SCHEDULE_RETRY)) {
                    failureHandler.process(singleJobRequest);
                }

            }catch (Exception ex){
                System.out.println("Something unknown went wrong, we will save to process it later");
                jobRequests.remove(singleJobRequest);
                failureHandler.process(singleJobRequest);
                ex.printStackTrace();
            }
        }
    }

    public ExecutionStatus singleExecution(JobRequest jobRequest){

        if( ! jobRequest.isOverRetryLimit()){

            WSResponse response = loadBalance.execute(jobRequest);

            if (response.isServerResponding()) {
                //System.out.println(response);
                return ExecutionStatus.OK;
            } else {
                System.out.println("Something went wrong, we will process it later over again");
                jobRequest.increaseExecutionCount();
                return ExecutionStatus.RETRY;
            }

        } else {
            System.out.println("We have tried to process this jobRequest more than the limit  ... " + jobRequest.toString());
            System.out.println("Saving to process it later... " + jobRequest.toString());
            return ExecutionStatus.SCHEDULE_RETRY;
        }
    }


}