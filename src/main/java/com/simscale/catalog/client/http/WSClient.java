package com.simscale.catalog.client.http;

import com.simscale.catalog.client.domain.User;

public interface WSClient {

    WSResponse doPerform(String type, String url, User user);

    WSResponse doGet(String url);

    WSResponse doPost(String url, User user);

    WSResponse doPut(String url, User user);

    WSResponse doDelete(String url);

}
