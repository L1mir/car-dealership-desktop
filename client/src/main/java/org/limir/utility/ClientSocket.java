package org.limir.utility;

import java.net.Socket;

public class ClientSocket {
    private static final ClientSocket SINGLE_INSTANCE = new ClientSocket();
    private static Socket socket;

    private ClientSocket() {
        try {
            socket = new Socket("localhost", 5555);
        } catch (Exception e) {
        }
    }

    public static ClientSocket getInstance() {
        return SINGLE_INSTANCE;
    }

    public Socket getSocket() {
        return socket;
    }
}
