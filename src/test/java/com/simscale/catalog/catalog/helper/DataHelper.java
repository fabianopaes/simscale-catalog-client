package com.simscale.catalog.catalog.helper;

import com.simscale.catalog.client.domain.JobRequest;
import com.simscale.catalog.client.helper.JobRequestGenerator;
import com.simscale.catalog.client.helper.JobRequestGeneratorCustomConfig;

import java.util.List;

public class DataHelper {

    public List<JobRequest> getJobRequestsForGet(int total){
        return JobRequestGenerator.build(new JobRequestGeneratorCustomConfig(0, 0, 0, total));
    }
}
