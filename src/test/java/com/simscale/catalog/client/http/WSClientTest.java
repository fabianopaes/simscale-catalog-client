package com.simscale.catalog.client.http;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class WSClientTest {

    private WSClientImpl client;
    private String url = "http://localhost:8080/v1/users/";

    @Before
    public void initialize() {
        client = new WSClientImpl();
    }
}

/*
    @Test
    @Ignore
    public void whenInterruptedExceptionHappensReturnsAnEmptyResponse() throws InterruptedException, ExecutionException, TimeoutException {
        BoundRequestBuilder request = Mockito.mock(BoundRequestBuilder.class);
        ListenableFuture<Response> futureResponseMock = Mockito.mock(FutureResponseMock.class);

        when(request.execute()).thenReturn(futureResponseMock);

        when(futureResponseMock.get(any(), any())).thenThrow(new InterruptedException());

        WSResponse response = client.doCall(request);

        assertEquals(null, response.getContent());
        assertEquals(0, response.getCode());
    }

    @Test
    @Ignore
    public void whenExecutionTimeoutExceptionReturnsAnEmptyResponse() throws InterruptedException, ExecutionException, TimeoutException {
        BoundRequestBuilder request = Mockito.mock(BoundRequestBuilder.class);
        ListenableFuture<Response> futureResponseMock = Mockito.mock(FutureResponseMock.class);

        when(request.execute()).thenReturn(futureResponseMock);

        when(futureResponseMock.get(any(), any())).thenThrow(new TimeoutException());

        WSResponse response = client.doCall(request);

        assertEquals(null, response.getContent());
        assertEquals(0, response.getCode());
    }

    @Test
    public void whenDoGetIsSuccessfullReturnAValidWSResponse(){

    }

    @Test
    public void prepareRightGetRequest(){
        BoundRequestBuilder requestBuilder = client.doPrepare(HttpMethod.GET, url);
        requestBuilder.

    }

    @Test
    public void replaceIdInUrl(){
        assertEquals(url + "1", client.encodedUrl(url + "{id}", 1L));
    }

*/


/*    WSResponse doPerform(HttpMethod type, String url, User user);

    WSResponse doGet(String url);

    WSResponse doPost(String url, User user);

    WSResponse doPut(String url, User user);

    WSResponse doDelete(String url);*/



/*    private BoundRequestBuilder doPrepare (HttpMethod type, String url){

        if (ObjectUtils.equals(HttpMethod.GET, type)) {
            return asyncHttpClient.prepareGet(url);
        } else if (ObjectUtils.equals(HttpMethod.POST, type)) {
            return asyncHttpClient.preparePost(url);
        } else if (ObjectUtils.equals(HttpMethod.PUT, type)) {
            return asyncHttpClient.preparePut(url);
        } if (ObjectUtils.equals(HttpMethod.DELETE, type)) {
            return asyncHttpClient.prepareDelete(url);
        }

        throw new RuntimeException("");
    }
}
*/
