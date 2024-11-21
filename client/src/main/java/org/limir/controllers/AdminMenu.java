package org.limir.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.limir.controllers.sceneUtility.SceneManager;

import java.io.IOException;

public class AdminMenu {
    @FXML
    private ChoiceBox<String> carOperationsChoiceBox;

    @FXML
    private Button carExecuteButton;

    @FXML
    private ChoiceBox<String> companyOperationsChoiceBox;

    @FXML
    private Button companyExecuteButton;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("login");
    }

    private final CarOperations carOperationsController = new CarOperations();
    private final CompanyOperations companyOperationsController = new CompanyOperations();

    @FXML
    public void initialize() {
        carOperationsController.initialize(carOperationsChoiceBox, carExecuteButton);
        companyOperationsController.initialize(companyOperationsChoiceBox, companyExecuteButton);
    }
}