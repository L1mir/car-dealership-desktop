package org.limir;

import org.limir.utility.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT_NUMBER = 5555;
    private static ServerSocket serverSocket;

    private static ClientThread clientHandler;
    private static Thread thread;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT_NUMBER);
        while (true) {
            Socket socket = serverSocket.accept();
            clientHandler = new ClientThread(socket);
            thread = new Thread(clientHandler);
            thread.start();
        }
    }
}
