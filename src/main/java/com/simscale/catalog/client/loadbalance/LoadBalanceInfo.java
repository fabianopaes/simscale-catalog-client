package com.simscale.catalog.client.loadbalance;

import com.simscale.catalog.client.domain.Server;
import com.simscale.catalog.client.http.WSClient;

import java.util.Deque;

public class LoadBalanceInfo {

    private Deque<Server> servers;

    private WSClient wsClient;

    public LoadBalanceInfo(Deque<Server> servers, WSClient wsClient) {
        this.servers = servers;
        this.wsClient = wsClient;
    }

    public Deque<Server> getServers() {
        return servers;
    }

    public void setServers(Deque<Server> servers) {
        this.servers = servers;
    }

    public WSClient getWsClient() {
        return wsClient;
    }

    public void setWsClient(WSClient wsClient) {
        this.wsClient = wsClient;
    }
}
