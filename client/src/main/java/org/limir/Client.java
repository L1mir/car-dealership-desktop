package org.limir;

import javafx.application.Application;

import javafx.stage.Stage;

import org.limir.controllers.sceneUtility.SceneInitializer;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.utility.ClientSocket;

import java.io.IOException;
import java.net.Socket;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        ClientSocket clientSocket = ClientSocket.getInstance();
        Socket socket = clientSocket.getSocket();
        SceneManager.initialize(primaryStage);
        SceneInitializer.initializeScenes();
        SceneManager.showScene("login");
        primaryStage.setTitle("Car-dealership");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}