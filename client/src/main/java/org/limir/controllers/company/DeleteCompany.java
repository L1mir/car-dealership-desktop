package org.limir.controllers.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.enums.RequestType;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;

public class DeleteCompany {
    @FXML
    private TextField companyName;

    @FXML
    private Button executeButton;

    @FXML
    private Button backButton;

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("admin-menu");
    }

    @FXML
    private void handleExecuteButton(ActionEvent event) throws IOException {
        Response deleteCompanyResponse = RequestHandler.sendRequest(RequestType.DELETE_COMPANY_BY_NAME, companyName.getText());
    }
}
