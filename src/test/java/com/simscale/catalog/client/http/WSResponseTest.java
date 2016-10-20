package com.simscale.catalog.client.http;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WSResponseTest {

    private String content;

    private int code;

    @Before
    public void initialize(){
        content = "empty-body";
        code = 200;
    }

    @Test
    public void makeSureGetContentReturnRightValue(){
        WSResponse response = new WSResponse(content, 0);
        assertEquals(content, response.getContent());
    }

    @Test
    public void makeSureGetCodeReturnRightValue(){
        WSResponse response = new WSResponse("", code);
        assertEquals(code, response.getCode());
    }

    @Test
    public void makeSureOkReturnRightValue(){
        WSResponse response = WSResponse.ok();
        assertEquals(code, response.getCode());
    }

    @Test
    public void makeSureEmptyReturnInvalidObject(){
        WSResponse response = WSResponse.empty();
        assertEquals(0, response.getCode());
        assertEquals(null, response.getContent());
    }

    @Test
    public void makeSureHttp200FamilyIsEqualsServerResponding(){
        verifyServerResponding(Arrays.asList(200, 201, 202, 203, 204, 205, 206));
    }

    @Test
    public void makeSureHttp100FamilyIsEqualsServerResponding(){
        verifyServerResponding(Arrays.asList(100, 101));
    }

    @Test
    public void makeSureHttp300FamilyIsEqualsServerResponding(){
        verifyServerResponding(Arrays.asList(300, 301, 302, 303, 304, 305, 306, 307));
    }

    @Test
    public void makeSureHttp400FamilyIsEqualsServerResponding(){
        verifyServerResponding(Arrays.asList(400, 401, 402, 403,
                404, 405, 406, 407, 408, 408, 409, 410, 411, 412, 413,
                414, 415,416,417));
    }

    @Test
    public void makeSureHttp500FamilyIsEqualsServerResponding(){
        verifyServerNotResponding(Arrays.asList(501,502,503,504,505));
    }

    private void verifyServerResponding(List<Integer> codes){
        verifyIfServerIsResponding(codes, true);
    }

    private void verifyServerNotResponding(List<Integer> codes){
        verifyIfServerIsResponding(codes, false);
    }

    private void verifyIfServerIsResponding(List<Integer> codes, boolean isResponding){
        for(Integer code : codes){
            WSResponse response = new WSResponse("", code);
            assertEquals(isResponding, response.isServerResponding());
        }
    }


}
