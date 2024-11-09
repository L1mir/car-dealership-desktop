package org.limir.models.tcp;

import com.google.gson.annotations.Expose;
import org.limir.models.enums.RequestType;

public class Request {
    @Expose
    private RequestType requestType;

    @Expose
    private String requestMessage;

    public Request(RequestType requestType, String requestMessage) {
        this.requestType = requestType;
        this.requestMessage = requestMessage;
    }

    public Request() {

    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
}
