package com.simscale.catalog.client.helper;

public class JobRequestGeneratorDefaultConfig extends JobRequestGeneratorAbstractConfig {

    private final int postRequests = 300;

    private final int putRequests = 300;

    private final int deleteRequests = 300;

    private final int getRequests = 300;

    @Override
    public int getPostRequests() {
        return postRequests;
    }

    @Override
    public int getPutRequests() {
        return putRequests;
    }

    @Override
    public int getDeleteRequests() {
        return deleteRequests;
    }

    @Override
    public int getGetRequests() {
        return getRequests;
    }

}

