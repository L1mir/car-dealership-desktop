package org.limir;

import org.limir.utility.ClientSocket;

import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ClientSocket clientSocket = ClientSocket.getInstance();
        Socket socket = clientSocket.getSocket();
    }
}
