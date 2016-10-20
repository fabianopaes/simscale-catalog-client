package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.domain.JobRequest;

public interface JobFailureHandler {

    /**
     * This method should be used to inject some logic to handle
     * jobs that we could not be able to process properly
     * So, we can send this jobRequest to a SQS, Redis, MongoDB or other
     * to handle it when we need
     *
     * @param jobRequest The jobRequest we will process later
     */
    public void process(JobRequest jobRequest);
}
