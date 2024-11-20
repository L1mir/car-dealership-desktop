package org.limir.controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.limir.models.entities.Company;
import org.limir.models.enums.RequestType;
import org.limir.models.tcp.Request;
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

    public void addButtonPressed(ActionEvent actionEvent) throws IOException {
        Company company = new Company();
        company.setName(nameTextField.getText());
        company.setAddress(addressTextField.getText());
        company.setPhone(phoneTextField.getText());
        company.setWebsite(websiteTextField.getText());
        company.setEmail(emailTextField.getText());

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(company));
        request.setRequestType(RequestType.ADD_COMPANY);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String answer = ClientSocket.getInstance().getIn().readLine();
        Response response = new Gson().fromJson(answer, Response.class);
    }
}
