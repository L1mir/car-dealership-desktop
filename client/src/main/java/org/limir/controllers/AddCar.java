package org.limir.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.CompanyDTO;
import org.limir.models.entities.Car;
import org.limir.models.entities.Company;
import org.limir.models.enums.CarStatus;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.Response;
import org.limir.utility.ClientSocket;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class AddCar {
    @FXML
    private TextField modelTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    ChoiceBox<String> carStatusChoiceBox;

    @FXML
    private Button addCarButton;

    @FXML
    private ChoiceBox<String> companyNameChoiceBox;

    @FXML
    private Button backButton;

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
        Request request = new Request();
        request.setRequestType(RequestType.READ_COMPANY);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String responseJson = ClientSocket.getInstance().getIn().readLine();
        Response response = new Gson().fromJson(responseJson, Response.class);

        if (response.getResponseStatus() == ResponseStatus.OK) {
            List<Company> companies = new Gson().fromJson(
                    response.getResponseData(),
                    new TypeToken<List<Company>>() {}.getType()
            );

            ObservableList<String> companyNames = FXCollections.observableArrayList();
            for (Company company : companies) {
                companyNames.add(company.getName());
            }
            companyNameChoiceBox.setItems(companyNames);
        } else {
            System.err.println("Failed to load companies: " + response.getResponseStatus());
        }
    }


    public void addButtonPressed(ActionEvent actionEvent) throws IOException {
        Car car = new Car();
        car.setModel(modelTextField.getText());
        car.setYear(Integer.parseInt(yearTextField.getText()));
        car.setPrice(new BigDecimal(priceTextField.getText()));

        if ("AVAILABLE".equals(carStatusChoiceBox.getValue())) {
            car.setCar_status(CarStatus.AVAILABLE);
        } else if ("SOLD".equals(carStatusChoiceBox.getValue())) {
            car.setCar_status(CarStatus.SOLD);
        }

        String selectedCompanyName = companyNameChoiceBox.getValue();
        if (selectedCompanyName != null) {
            Request companyRequest = new Request();
            companyRequest.setRequestType(RequestType.READ_COMPANY);

            ClientSocket.getInstance().getOut().println(new Gson().toJson(companyRequest));
            ClientSocket.getInstance().getOut().flush();

            String responseJson = ClientSocket.getInstance().getIn().readLine();
            Response response = new Gson().fromJson(responseJson, Response.class);

            if (response.getResponseStatus() == ResponseStatus.OK) {
                List<CompanyDTO> companiesDTO = new Gson().fromJson(
                        response.getResponseData(),
                        new TypeToken<List<CompanyDTO>>() {}.getType()
                );

                CompanyDTO selectedCompanyDTO = companiesDTO.stream()
                        .filter(companyDTO -> companyDTO.getName().equals(selectedCompanyName))
                        .findFirst()
                        .orElse(null);

                if (selectedCompanyDTO != null) {
                    Company selectedCompany = new Company();
                    selectedCompany.setCompany_id(selectedCompanyDTO.getCompanyId());
                    selectedCompany.setName(selectedCompanyDTO.getName());
                    selectedCompany.setAddress(selectedCompanyDTO.getAddress());
                    selectedCompany.setPhone(selectedCompanyDTO.getPhone());
                    selectedCompany.setEmail(selectedCompanyDTO.getEmail());
                    selectedCompany.setWebsite(selectedCompanyDTO.getWebsite());

                    car.setCompany(selectedCompany);
                } else {
                    System.err.println("Компания не найдена!");
                    return;
                }
            } else {
                System.err.println("Ошибка получения компаний!");
                return;
            }
        } else {
            System.err.println("Компания не выбрана!");
            return;
        }

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(car));
        request.setRequestType(RequestType.ADD_CAR);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String responseJson = ClientSocket.getInstance().getIn().readLine();
        Response response = new Gson().fromJson(responseJson, Response.class);

        if (response.getResponseStatus() == ResponseStatus.OK) {
            System.out.println("Car added successfully!");
        } else {
            System.err.println("Failed to add car: " + response.getResponseStatus());
        }
    }
}
