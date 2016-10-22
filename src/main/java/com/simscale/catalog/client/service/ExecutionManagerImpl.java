package com.simscale.catalog.client.service;

import com.simscale.catalog.client.domain.JobRequest;
import com.simscale.catalog.client.http.WSResponse;
import com.simscale.catalog.client.loadbalance.JobFailureHandler;
import com.simscale.catalog.client.loadbalance.LoadBalance;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExecutionManagerImpl implements ExecutionManager{

    private final Logger logger = LoggerFactory.getLogger(ExecutionManagerImpl.class);

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

            long begin = System.currentTimeMillis();

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
                logger.error("Something unknown went wrong, we will save to process it later. ", ex);
                jobRequests.remove(singleJobRequest);
                failureHandler.process(singleJobRequest);
            }

            long end = System.currentTimeMillis();

            logger.info("Started at: " + begin + " Ended at: " + end +
                    ". Time Execution " + TimeUnit.MILLISECONDS.toSeconds(end - begin) + "seconds, " +
                    TimeUnit.MILLISECONDS.toMillis(end - begin) + " milli");
        }
    }

    public ExecutionStatus singleExecution(JobRequest jobRequest){

        if( ! jobRequest.isOverRetryLimit()){

            WSResponse response = loadBalance.execute(jobRequest);

            if (response.isServerResponding()) {
                logger.info("Execution went fine");
                return ExecutionStatus.OK;
            } else {
                logger.info("Error processing request. The server might be down, we are scheduling retry" + jobRequest.toString());
                jobRequest.increaseExecutionCount();
                return ExecutionStatus.RETRY;
            }

        } else {
            logger.info("We have tried to process this jobRequest more than the limit  ... " + jobRequest.toString());
            logger.info("Saving to process it later... " + jobRequest.toString());
            return ExecutionStatus.SCHEDULE_RETRY;
        }
    }


}
