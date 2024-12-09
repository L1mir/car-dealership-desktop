package org.limir.controllers.employee;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.limir.controllers.sceneUtility.SceneManager;

import java.io.IOException;

public class EmployeeOperations {

    private ChoiceBox<String> employeeOperationsChoiceBox;
    private Button executeButton;

    public void initialize(ChoiceBox<String> employeeOperationsChoiceBox, Button executeButton) {
        this.employeeOperationsChoiceBox = employeeOperationsChoiceBox;
        this.executeButton = executeButton;

        employeeOperationsChoiceBox.getItems().addAll(
                "Добавить работника",
                "Редактировать информацию работника",
                "Удалить информацию работника",
                "Просмотреть список работников"
        );

        executeButton.setOnAction(event -> {
            String selectedOperation = employeeOperationsChoiceBox.getValue();
            if (selectedOperation == null) {
                System.out.println("Выберите операцию!");
                return;
            }

            switch (selectedOperation) {
                case "Добавить работника":
                    try {
                        addEmployee();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Редактировать информацию работника":
                    updateEmployee();
                    break;
                case "Удалить информацию работника":
                    try {
                        deleteEmployee();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Просмотреть список работников":
                    try {
                        readEmployees();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        });
    }

    private void updateEmployee() {
        SceneManager.showScene("update-employee");
    }

    private void addEmployee() throws IOException {
        SceneManager.showScene("add-employee");
    }

    public void deleteEmployee() throws IOException {
        SceneManager.showScene("delete-employee");
    }

    public void readEmployees() throws IOException {
        SceneManager.showScene("read-employees");
        ReadEmployees readEmployeesController = (ReadEmployees) SceneManager.getController("read-employees");
        try {
            readEmployeesController.reloadEmployeesFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
