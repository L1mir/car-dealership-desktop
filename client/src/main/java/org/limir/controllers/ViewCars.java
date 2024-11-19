package org.limir.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.limir.models.entities.Car;
import org.limir.models.entities.Company;
import org.limir.models.enums.CarStatus;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.Response;
import org.limir.utility.ClientSocket;

import javafx.scene.control.TableView;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ViewCars {
    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> carModelColumn;

    @FXML
    private TableColumn<Car, Integer> carYearColumn;

    @FXML
    private TableColumn<Car, BigDecimal> carPriceColumn;

    @FXML
    private TableColumn<Car, CarStatus> carStatusColumn;

    @FXML
    private TableColumn<Car, Company> carCompanyColumn;

    @FXML
    public void initialize() {
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        carYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        carPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        carStatusColumn.setCellValueFactory(new PropertyValueFactory<>("car_status"));
        carCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));

        try {
            loadCarsFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadCarsFromServer() throws IOException {
        Request request = new Request();
        request.setRequestType(RequestType.READ_CAR);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String answer = ClientSocket.getInstance().getIn().readLine();
        Response response = new Gson().fromJson(answer, Response.class);


        if (response.getResponseStatus() == ResponseStatus.OK) {
            List<Car> cars = new Gson().fromJson(response.getResponseData(), new TypeToken<List<Car>>(){}.getType());

            ObservableList<Car> carsObserver = FXCollections.observableArrayList(cars);
            carTable.setItems(carsObserver);
        } else {
            System.out.println("Error loading cars: " + response.getResponseStatus());
        }
    }
}
