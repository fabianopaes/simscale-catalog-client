package com.simscale.catalog.client.domain;

import com.simscale.catalog.client.http.HttpMethod;

import java.io.Serializable;

public class JobRequest implements Serializable {

    private String endpoint;
    private HttpMethod method;
    private User user;
    private int executionCount;

    public JobRequest(){}

    public JobRequest(String endpoint, HttpMethod method, User user) {
        this.endpoint = endpoint;
        this.method = method;
        this.user = user;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void increaseExecutionCount(){
        this.executionCount++;
    }

    public int getExecutionCount() {
        return executionCount;
    }

    public boolean isOverRetryLimit(){
        return executionCount > 10;
    }

    @Override
    public String toString() {
        return "JobRequest{" +
                "endpoint='" + endpoint + '\'' +
                ", method=" + method +
                ", user=" + user +
                ", executionCount=" + executionCount +
                '}';
    }
}
