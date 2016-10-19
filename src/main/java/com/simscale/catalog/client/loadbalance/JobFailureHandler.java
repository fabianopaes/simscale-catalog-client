package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.Job;

public interface JobFailureHandler {

    /**
     * This method should be used to inject some logic to handle
     * jobs that we could not be able to process properly
     * So, we can send this job to a SQS, Redis, MongoDB or other
     *
     * @param job The job we will process later
     */
    public void process(Job job);
}
