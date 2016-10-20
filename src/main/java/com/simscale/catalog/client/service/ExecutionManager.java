package com.simscale.catalog.client.service;

import com.simscale.catalog.client.domain.JobRequest;
import java.util.List;

public interface ExecutionManager {

    public void run(List<JobRequest> jobRequests);

    public void prettyPrintResults();

}
