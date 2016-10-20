package com.simscale.catalog.client.service;

import com.simscale.catalog.client.domain.JobRequest;
import com.simscale.catalog.client.helper.JobRequestGenerator;
import com.simscale.catalog.client.helper.JobRequestGeneratorCustomConfig;
import com.simscale.catalog.client.http.WSResponse;
import com.simscale.catalog.client.loadbalance.JobFailureHandler;
import com.simscale.catalog.client.loadbalance.LoadBalance;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ExecutionManagerTest {

    private ExecutionManagerImpl executionManager;
    private LoadBalance loadBalance;
    private JobFailureHandler failureHandler;


    @Before
    public void initialize(){
        loadBalance = Mockito.mock(LoadBalance.class);
        failureHandler  = Mockito.mock(JobFailureHandler.class);
        executionManager = new ExecutionManagerImpl(loadBalance, failureHandler, 1);
    }


    @Test
    //@Ignore
    public void makeSureApplicationNotStopRunningBeforeAGivenTime(){
        when(loadBalance.execute(any())).thenReturn(WSResponse.ok());
        long begin = System.currentTimeMillis();
        executionManager.run(JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(15, 15, 15, 15)));
        long end = System.currentTimeMillis();
        begin = begin + executionManager.getDurationInMillis();
        assertTrue(end >= begin);
    }

    @Test
    public void whenAJobRetriesExceededReturnsScheduleRetryStatus(){

        JobRequest request = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 1)).get(0);
        request.setExecutionCount(11);
        assertEquals(ExecutionStatus.SCHEDULE_RETRY, executionManager.singleExecution(request));
    }

    @Test
    public void whenAJobExecuteOkayReturnsOkStatus(){

        JobRequest request = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 1)).get(0);
        when(loadBalance.execute(request)).thenReturn(WSResponse.ok());
        assertEquals(ExecutionStatus.OK, executionManager.singleExecution(request));
    }

    @Test
    public void whenAJobExecuteFailureReturnsOkStatus(){
        JobRequest request = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 1)).get(0);
        when(loadBalance.execute(request)).thenReturn(WSResponse.empty());
        assertEquals(ExecutionStatus.RETRY, executionManager.singleExecution(request));
        assertEquals(1, request.getExecutionCount());
    }

    @Test
    public void whenASingleExecutionReturnsScheduleRetryCallJobFailureHandler(){
        JobRequest request = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 1)).get(0);
        request.setExecutionCount(11);
        executionManager.execute(new ArrayDeque<>(Arrays.asList(request)));
        verify(failureHandler, times(1)).process(request);
    }

    @Test
    public void whenASingleExecutionReturnsOkayNeverCallJobFailureHandler(){
        JobRequest request = JobRequestGenerator
                .build(new JobRequestGeneratorCustomConfig(0, 0, 0, 1)).get(0);
        when(loadBalance.execute(request)).thenReturn(WSResponse.ok());
        executionManager.execute(new ArrayDeque<>(Arrays.asList(request)));
        verify(failureHandler, times(0)).process(request);
    }

}

