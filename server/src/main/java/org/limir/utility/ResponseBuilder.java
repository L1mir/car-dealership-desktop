package org.limir.utility;

import com.google.gson.Gson;
import org.limir.enums.ResponseStatus;
import org.limir.models.tcp.Response;

public class ResponseBuilder {

    public Response createSuccessResponse(String message) {
        return new Response(ResponseStatus.OK, message, "");
    }

    public Response createSuccessResponse(String message, Object data) {
        Gson gson = new Gson();
        return new Response(ResponseStatus.OK, message, gson.toJson(data));
    }

    public Response createErrorResponse(String errorMessage) {
        return new Response(ResponseStatus.ERROR, errorMessage, "");
    }
}
