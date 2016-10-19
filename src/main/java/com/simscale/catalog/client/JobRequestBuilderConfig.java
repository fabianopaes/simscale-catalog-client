package com.simscale.catalog.client;

public class JobRequestBuilderConfig {

    private final int postRequests = 5;

    private final int putRequests = 5;

    private final int deleleteRequests = 5;

    private final int getRequests = 5;

    private final String postEndpoint =
            "/v1/users";

    private final String getEndpoint =
            "/v1/users/{id}";

    private final String putEndpoint = getEndpoint;

    private final String deleteEndpoint = getEndpoint;

    public int getPostRequests() {
        return postRequests;
    }

    public int getPutRequests() {
        return putRequests;
    }

    public int getDeleleteRequests() {
        return deleleteRequests;
    }

    public int getGetRequests() {
        return getRequests;
    }

    public String getPostEndpoint() {
        return postEndpoint;
    }

    public String getGetEndpoint() {
        return getEndpoint;
    }

    public String getPutEndpoint() {
        return putEndpoint;
    }

    public String getDeleteEndpoint() {
        return deleteEndpoint;
    }
}

