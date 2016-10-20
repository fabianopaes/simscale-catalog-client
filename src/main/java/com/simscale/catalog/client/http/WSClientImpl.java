package com.simscale.catalog.client.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simscale.catalog.client.domain.User;
import org.apache.commons.lang3.ObjectUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WSClientImpl implements WSClient{

    private final AsyncHttpClient asyncHttpClient;
    private final ObjectMapper mapper;

    public WSClientImpl() {
        this.asyncHttpClient = new DefaultAsyncHttpClient(
                WSHttpClientConfig.CONNECTION_POOL_PER_HOST
        );
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }


    @Override
    public WSResponse doPerform(HttpMethod type, String url, User user) {

        if (ObjectUtils.equals(HttpMethod.GET, type)) {
            return this.doGet(encodedUrl(url, user.getId()));
        } else if (ObjectUtils.equals(HttpMethod.POST, type)) {
            return doPost(url, user);
        } else if (ObjectUtils.equals(HttpMethod.PUT, type)) {
            return doPut(encodedUrl(url, user.getId()), user);
        } if (ObjectUtils.equals(HttpMethod.DELETE, type)) {
            return doDelete(encodedUrl(url, user.getId()));
        }

        return WSResponse.empty();
    }

    @Override
    public WSResponse doGet(String url) {
        BoundRequestBuilder wsRequest = this.doPrepare(HttpMethod.GET, url);
        return this.doCall(wsRequest);
    }

    @Override
    public WSResponse doPost(String url, User user) {
        try {
            BoundRequestBuilder wsRequest =  this.doPrepare(HttpMethod.POST, url)
                    .setBody(mapper.writeValueAsString(user))
                    .setHeader("Content-Type", "application/json");
            return this.doCall(wsRequest);
        } catch (IOException e) {
            return WSResponse.empty();
        }
    }

    @Override
    public WSResponse doPut(String url, User user) {
        try {
            BoundRequestBuilder wsRequest =  this.doPrepare(HttpMethod.PUT,
                    url.replace("{id}", String.valueOf(user.getId())))
                    .setBody(mapper.writeValueAsString(user))
                    .setHeader("Content-Type", "application/json");
            return this.doCall(wsRequest);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            return WSResponse.empty();
        }
    }

    @Override
    public WSResponse doDelete(String url) {
        BoundRequestBuilder wsRequest = this.doPrepare(HttpMethod.DELETE, url);
        return this.doCall(wsRequest);
    }

    public WSResponse doCall (BoundRequestBuilder wsRequest) {
        Future<Response> future = wsRequest.execute();
        WSResponse wsResponse = new WSResponse();
        try{
            Response response = future.get(2, TimeUnit.SECONDS);
            wsResponse.setCode(response.getStatusCode());
            wsResponse.setContent(response.getResponseBody());
        }catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(wsRequest);
        }
        return wsResponse;
    }


    public BoundRequestBuilder doPrepare (HttpMethod type, String url){

        if (ObjectUtils.equals(HttpMethod.GET, type)) {
            return asyncHttpClient.prepareGet(url);
        } else if (ObjectUtils.equals(HttpMethod.POST, type)) {
            return asyncHttpClient.preparePost(url);
        } else if (ObjectUtils.equals(HttpMethod.PUT, type)) {
            return asyncHttpClient.preparePut(url);
        } if (ObjectUtils.equals(HttpMethod.DELETE, type)) {
            return asyncHttpClient.prepareDelete(url);
        }

        throw new RuntimeException("");
   }

    public String encodedUrl(String url, Long id){
        return url.replace("{id}", String.valueOf(id));
    }

}
