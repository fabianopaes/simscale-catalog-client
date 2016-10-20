package com.simscale.catalog.client.helper;

public abstract class JobRequestGeneratorAbstractConfig {

    private final String postEndpoint =
            "/v1/users";

    private final String getEndpoint =
            "/v1/users/{id}";

    private final String putEndpoint = getEndpoint;

    private final String deleteEndpoint = getEndpoint;


    public abstract int getPostRequests();

    public abstract int getPutRequests();

    public abstract int getDeleteRequests();

    public abstract int getGetRequests();

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
