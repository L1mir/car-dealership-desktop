package org.limir.controllers.car;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.limir.controllers.sceneUtility.SceneManager;

import java.io.IOException;

public class CarOperations {

    private ChoiceBox<String> carOperationsChoiceBox;
    private Button executeButton;

    public void initialize(ChoiceBox<String> carOperationsChoiceBox, Button executeButton) {
        this.carOperationsChoiceBox = carOperationsChoiceBox;
        this.executeButton = executeButton;

        carOperationsChoiceBox.getItems().addAll(
                "Добавить автомобиль",
                "Редактировать автомобиль",
                "Удалить автомобиль",
                "Просмотреть список автомобилей"
        );

        executeButton.setOnAction(event -> {
            String selectedOperation = carOperationsChoiceBox.getValue();
            if (selectedOperation == null) {
                System.out.println("Выберите операцию!");
                return;
            }

            switch (selectedOperation) {
                case "Добавить автомобиль":
                    try {
                        addCar();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Редактировать автомобиль":
                    updateCar();
                    break;
                case "Удалить автомобиль":
                    try {
                        deleteCar();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Просмотреть список автомобилей":
                    System.out.println("Просмотр списка автомобилей...");
                    break;
            }
        });
    }

    private void updateCar() {
        SceneManager.showScene("update-car");
    }

    private void addCar() throws IOException {
        SceneManager.showScene("add-car");
    }

    public void deleteCar() throws IOException {
        SceneManager.showScene("delete-car");
    }
}