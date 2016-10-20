package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.domain.JobRequest;

public class JobFailureDummyHandler implements JobFailureHandler {

    @Override
    public void process(JobRequest jobRequest) {
        // do nothing yet

    }
}
