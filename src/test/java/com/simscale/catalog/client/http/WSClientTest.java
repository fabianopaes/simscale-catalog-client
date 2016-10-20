package com.simscale.catalog.client.http;

import com.simscale.catalog.client.domain.User;
import com.simscale.catalog.client.helper.JobRequestGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("unchecked")
public class WSClientTest {

    private WSClientImpl client;
    private String url = "http://localhost:8080/v1/users/";
    private User user;

    @Before
    public void initialize() {
        client = new WSClientImpl();
        user = JobRequestGenerator.userBuildWithId(1);
    }

    @Test(expected=RuntimeException.class)
    public void whenAnUnsupportedHttpMethodTriesToGetRequestBuilderThrowException (){
        client.doPrepare(null, url);
    }

    @Test
    public void returnARequestBuilderInstanceForGet(){
        assertNotNull(client.doPrepare(HttpMethod.GET, url));
    }

    @Test
    public void returnARequestBuilderInstanceForPost(){
        assertNotNull(client.doPrepare(HttpMethod.POST, url));
    }

    @Test
    public void returnARequestBuilderInstanceForPut(){
        assertNotNull(client.doPrepare(HttpMethod.PUT, url));
    }

    @Test
    public void returnARequestBuilderInstanceForDelete(){
        assertNotNull(client.doPrepare(HttpMethod.DELETE, url));
    }

    @Test
    public void whenPerformGetSuccessfullyReturnAValidWSResponse(){

    }

    @Test
    public void replaceIdInUrl(){
        assertEquals(url + "1", client.encodedUrl(url + "{id}", 1L));
    }

}
