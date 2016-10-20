package com.simscale.catalog.client;

public class JobRequestGeneratorConfig {

    private final int postRequests = 300;

    private final int putRequests = 300;

    private final int deleteRequests = 300;

    private final int getRequests = 300;

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

    public int getDeleteRequests() {
        return deleteRequests;
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

