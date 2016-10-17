package com.simscale.catalog.client;

import com.simscale.catalog.client.domain.User;
import io.netty.handler.codec.http.HttpMethod;

public class Job {

    private String endpoint;
    private HttpMethod method;
    private User user;

    public Job(String endpoint, HttpMethod method, User user) {
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
}
