package org.limir.utility;

import com.google.gson.Gson;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.Response;
import org.limir.services.CompanyService;
import org.limir.services.PersonService;
import org.limir.services.servicesImpl.CompanyServiceImpl;
import org.limir.services.servicesImpl.PersonServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson;
    private Request request;
    private Response response;

    private PersonService personService = new PersonServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    public ClientThread(Socket clientSocket) throws IOException {
        response = new Response();
        request = new Request();
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                String message = in.readLine();

                request = gson.fromJson(message, Request.class);

//                switch () { TODO
//
//                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
