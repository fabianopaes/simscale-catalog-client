package com.simscale.catalog.client.http;

public class WSResponse {

    private String content;
    private int code;

    public WSResponse(){}

    public WSResponse(String content, int code) {
        this.content = content;
        this.code = code;
    }

    public static WSResponse empty(){
        return new WSResponse();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOkay(){
        return getCode() >= 200 && getCode() <= 299;
    }

    @Override
    public String toString() {
        return "WSResponse{" +
                "content='" + content + '\'' +
                ", code=" + code +
                ", isOkay=" + isOkay() +
                '}';
    }
}
