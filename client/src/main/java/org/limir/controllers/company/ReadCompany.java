package org.limir.controllers.company;

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
import org.limir.models.CurrentUser;
import org.limir.models.dto.CompanyDTO;
import org.limir.models.entities.Company;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.enums.UserRole;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.util.List;

public class ReadCompany {
    @FXML
    private TableView<CompanyDTO> companyTable;

    @FXML
    private TableColumn<Company, String> companyNameColumn;

    @FXML
    private TableColumn<Company, String> companyAddressColumn;

    @FXML
    private TableColumn<Company, String> companyPhoneColumn;

    @FXML
    private TableColumn<Company, String> companyEmailColumn;

    @FXML
    private TableColumn<Company, String> companySiteColumn;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        if (CurrentUser.getUser().getUserRole() == UserRole.ADMIN) {
            SceneManager.showScene("admin-menu");
        } else {
            SceneManager.showScene("customer-menu");
        }
    }

    @FXML
    public void initialize() {
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        companyAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        companyPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        companyEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        companySiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));

        try {
            loadCompaniesFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadCompaniesFromServer() throws IOException {
        loadCompaniesFromServer();
    }

    private void loadCompaniesFromServer() throws IOException {
        Response readCompanieResponse = RequestHandler.sendRequest(RequestType.READ_COMPANIES, null);

        if (readCompanieResponse.getResponseStatus() == ResponseStatus.OK) {
            List<CompanyDTO> companyDTOS = new Gson().fromJson(readCompanieResponse.getResponseData(), new TypeToken<List<CompanyDTO>>() {
            }.getType());

            ObservableList<CompanyDTO> companyObserver = FXCollections.observableArrayList(companyDTOS);
            companyTable.setItems(companyObserver);
        } else {
            System.out.println("Error loading cars: " + readCompanieResponse.getResponseStatus());
        }
    }

}
