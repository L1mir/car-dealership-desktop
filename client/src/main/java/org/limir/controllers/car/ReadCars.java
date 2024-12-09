package org.limir.controllers.car;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.CurrentUser;
import org.limir.models.dto.CarDTO;
import org.limir.models.entities.Car;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.enums.UserRole;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ReadCars {
    @FXML
    private TableView<CarDTO> carTable;

    @FXML
    private TableColumn<Car, String> carModelColumn;

    @FXML
    private TableColumn<Car, Integer> carYearColumn;

    @FXML
    private TableColumn<Car, BigDecimal> carPriceColumn;

    @FXML
    private TableColumn<Car, String> carStatusColumn;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        if (CurrentUser.getUser().getUserRole() == UserRole.ADMIN) {
            SceneManager.showScene("admin-menu");
        }
        else {
            SceneManager.showScene("customer-menu");
        }
    }

    @FXML
    public void initialize() throws IOException {
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        carYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        carPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        carStatusColumn.setCellValueFactory(new PropertyValueFactory<>("carStatus"));
    }

    public void reloadCarsFromServer() throws IOException {
        loadCarsFromServer();
    }

    private void loadCarsFromServer() throws IOException {
        Response readCarsResponse = RequestHandler.sendRequest(RequestType.READ_CARS, null);

        if (readCarsResponse.getResponseStatus() == ResponseStatus.OK) {
            List<CarDTO> cars = new Gson().fromJson(readCarsResponse.getResponseData(), new TypeToken<List<CarDTO>>() {
            }.getType());

            ObservableList<CarDTO> carsObserver = FXCollections.observableArrayList(cars);
            carTable.setItems(carsObserver);
        } else {
            System.out.println("Error loading cars: " + readCarsResponse.getResponseStatus());
        }
    }
}
