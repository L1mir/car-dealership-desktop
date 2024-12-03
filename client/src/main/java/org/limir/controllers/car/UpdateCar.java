package org.limir.controllers.car;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.CarDTO;
import org.limir.models.entities.Car;
import org.limir.models.entities.Company;
import org.limir.models.enums.CarStatus;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class UpdateCar {

    @FXML
    private TextField searchModelTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private ChoiceBox<String> carStatusChoiceBox;

    @FXML
    private ChoiceBox<String> companyNameChoiceBox;

    @FXML
    private Button updateCarButton;

    @FXML
    private Button backButton;

    private CarDTO carToUpdate;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("admin-menu");
    }

    public void initialize() {
        carStatusChoiceBox.getItems().addAll("AVAILABLE", "SOLD");

        try {
            loadCompanies();
        } catch (IOException e) {
            System.err.println("Error loading companies: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadCompanies() throws IOException {
        Response readCompaniesResponse = RequestHandler.sendRequest(RequestType.READ_COMPANIES, null);

        if (readCompaniesResponse.getResponseStatus() == ResponseStatus.OK) {
            List<Company> companies = new Gson().fromJson(
                    readCompaniesResponse.getResponseData(),
                    new TypeToken<List<Company>>() {
                    }.getType()
            );

            ObservableList<String> companyNames = FXCollections.observableArrayList();

            for (Company company : companies) {
                companyNames.add(company.getName());
            }
            companyNameChoiceBox.setItems(companyNames);
        } else {
            System.err.println("Failed to load companies: " + readCompaniesResponse.getResponseStatus());
        }
    }

    @FXML
    public void updateButtonPressed(ActionEvent actionEvent) throws IOException {
        String searchModel = searchModelTextField.getText();

        if (searchModel.isEmpty()) {
            System.err.println("Model field is empty!");
            return;
        }

        Response readCarsResponse = RequestHandler.sendRequest(RequestType.READ_CARS, null);

        if (readCarsResponse.getResponseStatus() == ResponseStatus.OK) {
            List<CarDTO> carDTOList = new Gson().fromJson(
                    readCarsResponse.getResponseData(),
                    new TypeToken<List<CarDTO>>() {
                    }.getType()
            );

            carToUpdate = carDTOList.stream()
                    .filter(carDTO -> carDTO.getModel().equalsIgnoreCase(searchModel))
                    .findFirst()
                    .orElse(null);

            if (carToUpdate != null) {
                carToUpdate.setModel(modelTextField.getText());
                carToUpdate.setYear(Integer.parseInt(yearTextField.getText()));
                carToUpdate.setPrice(new BigDecimal(priceTextField.getText()));

                String selectedStatus = carStatusChoiceBox.getValue();
                carToUpdate.setCarStatus(selectedStatus);

                Car carToUpdateEntity = new Car();
                carToUpdateEntity.setCar_id(carToUpdate.getCarId());
                carToUpdateEntity.setModel(carToUpdate.getModel());
                carToUpdateEntity.setYear(carToUpdate.getYear());
                carToUpdateEntity.setPrice(carToUpdate.getPrice());
                carToUpdateEntity.setCar_status(CarStatus.valueOf(carToUpdate.getCarStatus()));

                Response updateCarResponse = RequestHandler.sendRequest(RequestType.UPDATE_CAR, carToUpdateEntity);

                if (updateCarResponse.getResponseStatus() == ResponseStatus.OK) {
                    System.out.println("Car updated successfully!");
                } else {
                    System.err.println("Failed to update car: " + updateCarResponse.getResponseStatus());
                }
            } else {
                System.err.println("Car not found!");
            }
        } else {
            System.err.println("Error searching car: " + readCarsResponse.getResponseStatus());
        }
    }
}
