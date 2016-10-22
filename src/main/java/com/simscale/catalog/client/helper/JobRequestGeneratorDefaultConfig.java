package com.simscale.catalog.client.helper;

public class JobRequestGeneratorDefaultConfig extends JobRequestGeneratorAbstractConfig {

    private final int postRequests = 3000;

    private final int putRequests = 3000;

    private final int deleteRequests = 3000;

    private final int getRequests = 3000;

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

