package org.limir.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class AdminMenu {
    @FXML
    private ChoiceBox<String> carOperationsChoiceBox;

    @FXML
    private Button carExecuteButton;

    @FXML
    private ChoiceBox<String> companyOperationsChoiceBox;

    @FXML
    private Button companyExecuteButton;

    private final CarOperations carOperationsController = new CarOperations();
    private final CompanyOperations companyOperationsController = new CompanyOperations();

    @FXML
    public void initialize() {
        carOperationsController.initialize(carOperationsChoiceBox, carExecuteButton);
        companyOperationsController.initialize(companyOperationsChoiceBox, companyExecuteButton);
    }
}