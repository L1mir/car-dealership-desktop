package org.limir.controllers.employee;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.CompanyDTO;
import org.limir.models.dto.PersonDTO;
import org.limir.models.entities.Employee;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.math.BigDecimal;

public class AddEmployee {
    @FXML
    private TextField companyNameTextField;

    @FXML
    private TextField personNameTextField;

    @FXML
    private TextField positionTextField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("admin-menu");
    }

    public void initialize() throws IOException {
    }

    public void handleAddButtonPressed(ActionEvent actionEvent) throws IOException {
        Employee employee = new Employee();
        employee.setPosition(positionTextField.getText());
        BigDecimal salary = new BigDecimal(salaryTextField.getText());
        employee.setSalary(salary);

        String companyName = companyNameTextField.getText();
        Response findCompanyByNameResponse = RequestHandler.sendRequest(RequestType.FIND_COMPANY_BY_NAME, companyName);

        if (findCompanyByNameResponse.getResponseStatus() == ResponseStatus.OK) {
            Gson gson = new Gson();
            CompanyDTO companyDTO = gson.fromJson(findCompanyByNameResponse.getResponseData(), CompanyDTO.class);
            employee.setCompany(companyDTO.toCompany());
        } else {
            System.out.println(findCompanyByNameResponse.getResponseStatus());
        }

        String personName = personNameTextField.getText();
        Response findPersonByNameResponse = RequestHandler.sendRequest(RequestType.FIND_PERSON_BY_SURNAME, personName);

        if (findPersonByNameResponse.getResponseStatus() == ResponseStatus.OK) {
            Gson gson = new Gson();
            PersonDTO personDTO = gson.fromJson(findPersonByNameResponse.getResponseData(), PersonDTO.class);
            employee.setPerson(personDTO.toPerson());
        } else {
            System.out.println(findPersonByNameResponse.getResponseStatus());
        }

        Response saveEmployeeResponse = RequestHandler.sendRequest(RequestType.ADD_EMPLOYEE, employee);
    }
}
