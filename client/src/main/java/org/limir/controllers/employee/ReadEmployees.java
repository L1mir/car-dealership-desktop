package org.limir.controllers.employee;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.EmployeeDTO;
import org.limir.models.entities.Employee;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ReadEmployees {

    @FXML
    private TableView<EmployeeDTO> employeeTable;

    @FXML
    private TableColumn<Employee, Long> employeeIdColumn;

    @FXML
    private TableColumn<Employee, String> employeePositionColumn;

    @FXML
    private TableColumn<Employee, BigDecimal> employeeSalaryColumn;

    @FXML
    private TableColumn<Employee, String> companyNameColumn;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("admin-menu");
    }

    @FXML
    public void initialize() throws IOException {
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeePositionColumn.setCellValueFactory(new PropertyValueFactory<>("employeePosition"));
        employeeSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("employeeSalary"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
    }

    public void reloadEmployeesFromServer() throws IOException {
        loadEmployeesFromServer();
    }

    private void loadEmployeesFromServer() throws IOException {
        Response readCarsResponse = RequestHandler.sendRequest(RequestType.READ_EMPLOYEES, null);

        if (readCarsResponse.getResponseStatus() == ResponseStatus.OK) {
            List<EmployeeDTO> employeeDTOS = new Gson().fromJson(readCarsResponse.getResponseData(), new TypeToken<List<EmployeeDTO>>() {
            }.getType());

            ObservableList<EmployeeDTO> employeesObserver = FXCollections.observableArrayList(employeeDTOS);
            employeeTable.setItems(employeesObserver);
        } else {
            System.out.println("Error loading employees: " + readCarsResponse.getResponseStatus());
        }
    }
}
