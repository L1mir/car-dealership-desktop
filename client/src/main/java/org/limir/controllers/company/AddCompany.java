package org.limir.controllers.company;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.entities.Company;
import org.limir.models.enums.RequestType;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;
import org.limir.utility.ClientSocket;

import java.io.IOException;

public class AddCompany {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField websiteTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button addCompanyButton;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("admin-menu");
    }

    public void addButtonPressed(ActionEvent actionEvent) throws IOException {
        Company company = new Company();
        company.setName(nameTextField.getText());
        company.setAddress(addressTextField.getText());
        company.setPhone(phoneTextField.getText());
        company.setWebsite(websiteTextField.getText());
        company.setEmail(emailTextField.getText());

        Response addCompanyResponse = RequestHandler.sendRequest(RequestType.ADD_COMPANY, company);
    }
}
