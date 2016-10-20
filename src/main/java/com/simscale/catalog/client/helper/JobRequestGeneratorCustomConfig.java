package com.simscale.catalog.client.helper;

public class JobRequestGeneratorCustomConfig extends JobRequestGeneratorAbstractConfig{

    private final int postRequests;

    private final int putRequests;

    private final int deleteRequests;

    private final int getRequests;

    public JobRequestGeneratorCustomConfig(int postRequests, int putRequests, int deleteRequests, int getRequests) {
        this.postRequests = postRequests;
        this.putRequests = putRequests;
        this.deleteRequests = deleteRequests;
        this.getRequests = getRequests;
    }

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
