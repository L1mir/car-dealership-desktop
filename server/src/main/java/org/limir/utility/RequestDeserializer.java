package org.limir.utility;

import com.google.gson.Gson;
import org.limir.models.dto.OrderDTO;
import org.limir.models.dto.UserDTO;
import org.limir.models.entities.*;
import org.limir.models.tcp.Request;

public class RequestDeserializer {

    private static Gson gson = new Gson();

    public static Request deserializeRequest(String message) {
        return gson.fromJson(message, Request.class);
    }

    public static Person deserializePerson(Request request) {
        return gson.fromJson(request.getRequestMessage(), Person.class);
    }

    public static User deserializeUser(Request request) {
        return gson.fromJson(request.getRequestMessage(), User.class);
    }

    public static Car deserializeCar(Request request) {
        return gson.fromJson(request.getRequestMessage(), Car.class);
    }

    public static Company deserializeCompany(Request request) {
        return gson.fromJson(request.getRequestMessage(), Company.class);
    }

    public static String deserializeModel(Request request) {
        return gson.fromJson(request.getRequestMessage(), String.class);
    }

    public static OrderDTO deserializeOrderDto(Request request) {
        return gson.fromJson(request.getRequestMessage(), OrderDTO.class);
    }

    public static UserDTO deserializeUserDto(Request request) {
        return gson.fromJson(request.getRequestMessage(), UserDTO.class);
    }

    public static String deserializeName(Request request) {
        return gson.fromJson(request.getRequestMessage(), String.class);
    }

    public static Employee deserializeEmployee(Request request) {
        return gson.fromJson(request.getRequestMessage(), Employee.class);
    }

    public static Long deserializeEmployeeId(Request request) {
        return gson.fromJson(request.getRequestMessage(), Long.class);
    }
}

