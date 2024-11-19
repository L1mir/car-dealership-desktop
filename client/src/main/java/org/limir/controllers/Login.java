package org.limir.controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.limir.models.entities.User;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.enums.UserRole;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.Response;
import org.limir.utility.ClientSocket;
import org.limir.models.dto.UserDTO;

import java.io.IOException;

public class Login {
    @FXML
    private PasswordField passwordField;
    public TextField textFieldLogin;
    public Button buttonLogin;
    public Button buttonRegister;

    public void logInPressed(ActionEvent actionEvent) throws IOException {
        User user = new User();
        user.setUsername(textFieldLogin.getText());
        user.setPassword(passwordField.getText());

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(user));
        request.setRequestType(RequestType.LOGIN);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String answer = ClientSocket.getInstance().getIn().readLine();
        Response response = new Gson().fromJson(answer, Response.class);

        if (response.getResponseStatus() == ResponseStatus.OK) {
            UserDTO loggedUserDTO = new Gson().fromJson(response.getResponseData(), UserDTO.class);

            Stage stage = (Stage) buttonLogin.getScene().getWindow();
            Parent root;

            if (loggedUserDTO.getUserRole() == UserRole.CUSTOMER) {
                root = FXMLLoader.load(getClass().getResource("/org/limir/customer-menu.fxml"));
            } else if (loggedUserDTO.getUserRole() == UserRole.ADMIN) {
                root = FXMLLoader.load(getClass().getResource("/org/limir/admin-menu.fxml"));
            } else {
                System.out.println("Unknown user role: " + loggedUserDTO.getUserRole());
                return;
            }

            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        } else {
            System.out.println("Login failed: " + response.getResponseStatus());
        }

    }


    public void registerPressed (ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonRegister.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/limir/register.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
