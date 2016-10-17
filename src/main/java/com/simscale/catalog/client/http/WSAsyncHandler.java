package com.simscale.catalog.client.http;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.Response;

public class WSAsyncHandler extends AsyncCompletionHandler {

    private final long begin;

    public WSAsyncHandler(long begin) {
        this.begin = begin;
    }

    @Override
    public Object onCompleted(Response response) throws Exception {
        System.out.println("    Time successful execution:  " + String.valueOf(System.currentTimeMillis() - begin));
        return response;
    }

    @Override
    public void onThrowable(Throwable t) {
        System.out.println("    Time to get an error: " + String.valueOf(System.currentTimeMillis() - begin));
    }

}
