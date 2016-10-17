package com.simscale.catalog.client.http;

import com.simscale.catalog.client.domain.User;
import org.apache.commons.lang3.ObjectUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WSClientImpl implements WSClient{

    @Override
    public WSResponse doPerform(String type, String url, User user) {

        if (ObjectUtils.equals("GET", type)) {
            return this.doGet(url);
        } else if (ObjectUtils.equals("POST", type)) {
            return doPost(url, user);
        } else if (type == "PUT") {
            return doPut(url, user);
        } if (type == "DELETE") {
            return doDelete(url);
        }

        throw new RuntimeException("");
    }

    @Override
    public WSResponse doGet(String url) {
        BoundRequestBuilder wsRequest = this.doPrepare("GET", url);
        return this.doCall(wsRequest);
    }

    @Override
    public WSResponse doPost(String url, User user) {
        BoundRequestBuilder wsRequest = this.doPrepare("POST", url);
        return this.doCall(wsRequest);
    }

    @Override
    public WSResponse doPut(String url, User user) {
        BoundRequestBuilder wsRequest = this.doPrepare("PUT", url);
        return this.doCall(wsRequest);
    }

    @Override
    public WSResponse doDelete(String url) {
        BoundRequestBuilder wsRequest = this.doPrepare("DELETE", url);
        return this.doCall(wsRequest);
    }

    private WSResponse doCall (BoundRequestBuilder wsRequest) {
        Future<Response> future = wsRequest.execute(new WSAsyncHandler(System.currentTimeMillis()));
        WSResponse wsResponse = new WSResponse();
        try{
            Response response = future.get(2, TimeUnit.SECONDS);
            wsResponse.setCode(response.getStatusCode());
            wsResponse.setContent(response.getResponseBody());
        }catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return wsResponse;
    }


    private BoundRequestBuilder doPrepare (String type, String url){

        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

        if (ObjectUtils.equals("GET", type)) {
            return asyncHttpClient.prepareGet(url);
        } else if (ObjectUtils.equals("POST", type)) {
            return asyncHttpClient.preparePost(url);
        } else if (type == "PUT") {
            return asyncHttpClient.preparePut(url);
        } if (type == "DELETE") {
            return asyncHttpClient.prepareDelete(url);
        }

        throw new RuntimeException("");
   }

}
