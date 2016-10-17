package com.simscale.catalog.client.circuitbreaker;


public class CircuitBreakerCustom implements CircuitBreaker {

    private String name;

    private int failureThreshold = 1;

    private int retryInterval = 30000;

    private int failureCount;

    private Status state;

    private long lastOpenTimestamp = Long.MAX_VALUE;

    private long attemptResetAfter = 1 * 60 * 1000;

    private int successCount;

    public enum Status{

        CLOSED,

        OPEN,

        HALF_OPEN

    }

    /*private Logger LOG = LoggerFactory.getLogger(CircuitBreakerCustom.class);*/

    public CircuitBreakerCustom() {
        this.state = Status.CLOSED;
    }

    public CircuitBreakerCustom(String name) {
        this.state = Status.CLOSED;
        this.name = name;
    }

    public CircuitBreakerCustom(String name, int failureThreshold, int timeout) {
        this();
        this.name = name;
        this.failureThreshold = failureThreshold;
        this.retryInterval = timeout;

    }

    public CircuitBreakerCustom(int failureThreshold) {
        this();
        this.failureThreshold = failureThreshold;
    }

    public Status getState() {

        if (Status.OPEN.equals(state) &&
                System.currentTimeMillis() >= attemptResetAfter) {
            state = Status.HALF_OPEN;
        }
        return state;
    }

    public int onFailure() {

        if(isClosed()){
            tripBreaker();
            return increaseFailureCountAndGet();
        }

        if (isHalfOpen()) {
            tripBreaker();
        }

        return getFailureCount();
    }

    void setState(Status state) {
        Status priorState = this.state;
        if (this.state.equals(state)) {
            return;
        }
        this.state = state;
    }

    public void reset() {
        setState(Status.CLOSED);
        setFailureCount(0);
    }

    public void tripBreaker() {
        tripBreaker(retryInterval);
    }

    public void tripBreaker(int timeout) {
        lastOpenTimestamp = System.currentTimeMillis();
        attemptResetAfter = lastOpenTimestamp + timeout;
        setState(Status.OPEN);

    }

    public int getFailureCount() {
        return failureCount;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void onSuccess() {
        if (!isClosed()) {
            reset();
            return;
        }

        if (isClosed() && failureCount > 0) {
            setFailureCount(0);
        }

        increaseSuccessCounter();
    }

    public int onFailure(String type) {
        return onFailure();
    }


    public boolean isClosed() {
        return Status.CLOSED.equals(state);
    }

    public boolean isHalfOpen() {
        return Status.HALF_OPEN.equals(getState());
    }

    public boolean isOpen() {
        return Status.OPEN.equals(state);
    }

    public boolean isCallable() {
        return isClosed() || isHalfOpen();
    }

    public int getFailureThreshold() {
        return failureThreshold;
    }

    public long getLastOpenTimestamp() {
        return lastOpenTimestamp;
    }

    @Override
    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public void increaseSuccessCounter(){
        this.successCount ++;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    private int increaseFailureCountAndGet(){
        increaseFailureCount();
        return getFailureCount();
    }

    private void increaseFailureCount(){
        this.failureCount++;
    }
}
