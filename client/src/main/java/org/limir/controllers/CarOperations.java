package org.limir.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CarOperations {
    @FXML
    private ChoiceBox<String> carOperationsChoiceBox;

    @FXML
    Button executeButton;

    @FXML
    public void initialize() {
        carOperationsChoiceBox.getItems().addAll(
                "Добавить автомобиль",
                "Редактировать автомобиль",
                "Удалить автомобиль",
                "Просмотреть список автомобилей"
        );

        executeButton.setOnAction(event -> {
            try {
                handleOperation();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleOperation() throws IOException {
        String selectedOperation = carOperationsChoiceBox.getValue();

        if (selectedOperation == null) {
            System.out.println("Выберите операцию!");
            return;
        }

        switch (selectedOperation) {
            case "Добавить автомобиль":
                addCar();
                break;
            case "Редактировать автомобиль":
                // TODO editCar();
                break;
            case "Удалить автомобиль":
                // TODO deleteCar();
                break;
            case "Просмотреть список автомобилей":
                // TODO viewCars();
                break;
            default:
                System.out.println("Неизвестная операция: " + selectedOperation);
        }
    }

    private void addCar() throws IOException {
        Stage stage = (Stage) executeButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/org/limir/add-car.fxml"));
        Scene addCarScene = new Scene(root);
        stage.setScene(addCarScene);
    }
}
