package com.simscale.catalog.client.domain;

import static java.util.Objects.isNull;

import com.simscale.catalog.client.circuitbreaker.CircuitBreaker;
import com.simscale.catalog.client.circuitbreaker.CircuitBreakerCustom;

public class Server {

    private String url;
    private Integer port;
    private String name;
    private CircuitBreaker circuitBreaker;

    public Server(){
        // Using CircuitBreaker with the Default config
        this.circuitBreaker = new CircuitBreakerCustom();
    }

    public Server(String url, Integer port, String name, CircuitBreaker circuitBreaker) {
        this.url = url;
        this.port = port;
        this.name = name;
        this.circuitBreaker = circuitBreaker;
    }

    public Server(String url, String name, CircuitBreaker circuitBreaker) {
        this.url = url;
        this.name = name;
        this.circuitBreaker = circuitBreaker;
    }

    public Server(String url, Integer port, String name) {
        this.url = url;
        this.port = port;
        this.name = name;
        // Using CircuitBreaker with the Default config
        this.circuitBreaker = new CircuitBreakerCustom();
    }

    public Server(String name, String url) {
        this.name = name;
        this.url = url;
        // Using CircuitBreaker with the Default config
        this.circuitBreaker = new CircuitBreakerCustom();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }

    public void setCircuitBreaker(CircuitBreaker circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncondedUrl(){

        if(isNull(port)){
            return url;
        }

        return url + ":" + port;
    }
}
