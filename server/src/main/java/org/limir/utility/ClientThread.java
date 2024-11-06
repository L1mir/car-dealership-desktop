package org.limir.utility;

import org.limir.services.CompanyService;
import org.limir.services.PersonService;
import org.limir.services.servicesImpl.CompanyServiceImpl;
import org.limir.services.servicesImpl.PersonServiceImpl;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private PersonService personService = new PersonServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

    }
}
