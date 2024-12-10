package org.limir.controllers.company;

import org.limir.models.dto.CompanyDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.entities.Company;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;

public class UpdateCompany {
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
    private TextField searchCompanyTextField;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("admin-menu");
    }

    @FXML
    public void updateButtonPressed(ActionEvent actionEvent) throws IOException {
        String searchCompany = searchCompanyTextField.getText();

        if (searchCompany.isEmpty()) {
            System.err.println("Model field is empty!");
            return;
        }

        Response findCompanyByName = RequestHandler.sendRequest(RequestType.FIND_COMPANY_BY_NAME, searchCompany);

        if (findCompanyByName.getResponseStatus() == ResponseStatus.OK) {
            Gson gson = new Gson();
            CompanyDTO companyToUpdateDto = gson.fromJson(findCompanyByName.getResponseData(), CompanyDTO.class);
            Company companyToUpdate = companyToUpdateDto.toCompany();
            companyToUpdate.setName(nameTextField.getText());
            companyToUpdate.setAddress(addressTextField.getText());
            companyToUpdate.setPhone(phoneTextField.getText());
            companyToUpdate.setWebsite(websiteTextField.getText());
            companyToUpdate.setEmail(emailTextField.getText());

            Response addUpdatedCompany = RequestHandler.sendRequest(RequestType.UPDATE_COMPANY, companyToUpdate);
        }
    }
}
