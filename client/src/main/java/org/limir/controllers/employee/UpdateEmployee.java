package org.limir.controllers.employee;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.CompanyDTO;
import org.limir.models.dto.EmployeeDTO;
import org.limir.models.entities.Company;
import org.limir.models.entities.Employee;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.math.BigDecimal;

public class UpdateEmployee {

    @FXML
    private TextField positionTextField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private TextField searchEmployeeTextField;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("admin-menu");
    }


    @FXML
    public void updateButtonPressed(ActionEvent actionEvent) throws IOException {
        Long searchEmployee = Long.valueOf(searchEmployeeTextField.getText());

        Response findEmployeeByName = RequestHandler.sendRequest(RequestType.FIND_EMPLOYEE_BY_ID, searchEmployee);

        if (findEmployeeByName.getResponseStatus() == ResponseStatus.OK) {
            Gson gson = new Gson();
            EmployeeDTO employeeToUpdateDto = gson.fromJson(findEmployeeByName.getResponseData(), EmployeeDTO.class);
            Employee employeeToUpdate = employeeToUpdateDto.toEmployee();
            employeeToUpdate.setPosition(positionTextField.getText());
            BigDecimal employeeSalary = new BigDecimal(salaryTextField.getText());
            employeeToUpdate.setSalary(employeeSalary);

            Response addUpdatedEmployee = RequestHandler.sendRequest(RequestType.UPDATE_EMPLOYEE, employeeToUpdate);
        }
    }
}
