package org.limir.utility;

import com.google.gson.Gson;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.Response;

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
    private RequestProcessor requestProcessor;

    public ClientThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
        requestProcessor = new RequestProcessor();
    }

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                Request request = RequestDeserializer.deserializeRequest(message);
                Response response = requestProcessor.processRequest(request);
                out.println(gson.toJson(response));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

