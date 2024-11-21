package org.limir.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.limir.controllers.sceneUtility.SceneManager;

import java.io.IOException;

public class CompanyOperations {
    private ChoiceBox<String> companiesOperationsChoiceBox;
    private Button executeButton;

    public void initialize(ChoiceBox<String> companyOperationsChoiceBox, Button executeButton) {
        this.companiesOperationsChoiceBox = companyOperationsChoiceBox;
        this.executeButton = executeButton;

        companyOperationsChoiceBox.getItems().addAll(
                "Добавить компанию",
                "Редактировать компанию",
                "Удалить компанию",
                "Просмотреть список компаний"
        );

        executeButton.setOnAction(event -> {
            String selectedOperation = companyOperationsChoiceBox.getValue();
            if (selectedOperation == null) {
                System.out.println("Выберите операцию!");
                return;
            }

            switch (selectedOperation) {
                case "Добавить компанию":
                    try {
                        addCompany();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Редактировать компанию":
                    System.out.println("Редактирование компании...");
                    break;
                case "Удалить компанию":
                    System.out.println("Удаление компании...");
                    break;
                case "Просмотреть список компаний":
                    System.out.println("Просмотр списка компаний...");
                    break;
            }
        });
    }

    private void addCompany() throws IOException {
        SceneManager.showScene("add-company");
    }
}
