package org.limir.controllers.company;

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
                    try {
                        updateCompany();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Удалить компанию":
                    try {
                        deleteCompany();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Просмотреть список компаний":
                    try {
                        readCompanies();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        });
    }

    private void addCompany() throws IOException {
        SceneManager.showScene("add-company");
    }

    public void deleteCompany() throws IOException {
        SceneManager.showScene("delete-company");
    }

    public void updateCompany() throws IOException {
        SceneManager.showScene("update-company");
    }

    public void readCompanies() throws IOException {
        SceneManager.showScene("read-companies");
        ReadCompany readCompaniesController = (ReadCompany) SceneManager.getController("read-companies");
        try {
            readCompaniesController.reloadCompaniesFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
