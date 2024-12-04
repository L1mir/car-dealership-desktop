package org.limir.controllers.auth;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.limir.controllers.CustomerMenu;
import org.limir.controllers.order.OrderHistory;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.CurrentUser;
import org.limir.models.entities.User;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.enums.UserRole;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;
import org.limir.models.dto.UserDTO;

import java.io.IOException;

public class Login {
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField textFieldLogin;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonRegister;

    public void logInPressed(ActionEvent actionEvent) throws IOException {
        User user = new User();
        user.setUsername(textFieldLogin.getText());
        user.setPassword(passwordField.getText());

        Response loginResponse = RequestHandler.sendRequest(RequestType.LOGIN, user);

        if (loginResponse.getResponseStatus() == ResponseStatus.OK) {
            UserDTO loggedUserDTO = new Gson().fromJson(loginResponse.getResponseData(), UserDTO.class);

            CurrentUser.setUser(loggedUserDTO);

            if (loggedUserDTO.getUserRole() == UserRole.CUSTOMER) {
                SceneManager.showScene("customer-menu");
                CustomerMenu customerMenuController = (CustomerMenu) SceneManager.getController("customer-menu");
                try {
                    customerMenuController.reloadCarsFromServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (loggedUserDTO.getUserRole() == UserRole.ADMIN) {
                SceneManager.showScene("admin-menu");
            } else {
                System.out.println("Unknown user role: " + loggedUserDTO.getUserRole());
                return;
            }
        } else {
            System.out.println("Login failed: " + loginResponse.getResponseStatus());
        }


    }

    public void registerPressed(ActionEvent event) throws IOException {
        SceneManager.showScene("register");
    }
}
