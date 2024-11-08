package org.limir.models.tcp;

import org.limir.enums.ResponseStatus;

public class Response {
    private ResponseStatus responseStatus;
    private String responseMessage;
    private String responseDate;

    public Response () {

    }

    public Response(ResponseStatus responseStatus, String responseMessage, String responseDate) {
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
        this.responseDate = responseDate;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }
}
