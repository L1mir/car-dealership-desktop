package org.limir.controllers.car;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.enums.RequestType;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;

public class DeleteCar {
    @FXML
    private TextField modelTextField;

    @FXML
    private Button executeButton;

    @FXML
    private Button backButton;

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("admin-menu");
    }

    @FXML
    private void handleExecuteButton(ActionEvent event) throws IOException {
        Response deleteCarResponse = RequestHandler.sendRequest(RequestType.DELETE_CAR, modelTextField.getText());
    }
}
