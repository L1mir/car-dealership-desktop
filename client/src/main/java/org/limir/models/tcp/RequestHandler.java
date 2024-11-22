package org.limir.models.tcp;

import com.google.gson.Gson;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.utility.ClientSocket;

import java.io.IOException;


public class RequestHandler {

    private static final Gson gson = new Gson();

    public static Response sendRequest(RequestType requestType, Object requestData) throws IOException {
        Request request = new Request();
        request.setRequestType(requestType);
        request.setRequestMessage(gson.toJson(requestData));

        ClientSocket.getInstance().getOut().println(gson.toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String responseJson = ClientSocket.getInstance().getIn().readLine();
        return gson.fromJson(responseJson, Response.class);
    }

    public static <T> T sendReadRequest(RequestType requestType, java.lang.reflect.Type responseType) throws IOException {
        Response response = sendRequest(requestType, null);

        if (response.getResponseStatus() == ResponseStatus.OK) {
            return gson.fromJson(response.getResponseData(), responseType);
        } else {
            throw new IOException("Failed to read data: " + response.getResponseStatus());
        }
    }

}

