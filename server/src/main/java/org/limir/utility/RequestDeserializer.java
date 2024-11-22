package org.limir.utility;

import com.google.gson.Gson;
import org.limir.models.entities.Car;
import org.limir.models.entities.Company;
import org.limir.models.entities.Person;
import org.limir.models.entities.User;
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
}

