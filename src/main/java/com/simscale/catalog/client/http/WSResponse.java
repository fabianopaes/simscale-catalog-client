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

    public static WSResponse ok(){
        return new WSResponse("", 200);
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

    public boolean isServerResponding(){
            return getCode() > 0 &&  getCode() < 500;
    }

    @Override
    public String toString() {
        return "WSResponse{" +
                "content='" + content + '\'' +
                ", code=" + code +
                ", isServerResponding=" + isServerResponding() +
                '}';
    }
}
