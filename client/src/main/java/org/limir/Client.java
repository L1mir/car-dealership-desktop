package org.limir;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.limir.utility.ClientSocket;

import java.io.IOException;
import java.net.Socket;

public class Client extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        ClientSocket clientSocket = ClientSocket.getInstance();
        Socket socket = clientSocket.getSocket();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Car-dealership");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}