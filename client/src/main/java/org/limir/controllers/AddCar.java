package org.limir.controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.limir.models.dto.UserDTO;
import org.limir.models.entities.Car;
import org.limir.models.enums.CarStatus;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.enums.UserRole;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.Response;
import org.limir.utility.ClientSocket;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

public class AddCar {
    @FXML
    private TextField modelTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    ChoiceBox<String>  carStatusChoiceBox;

    @FXML
    private Button addCarButton;

    public void initialize() {
        carStatusChoiceBox.getItems().addAll("AVAILABLE", "SOLD");
    }

    public void addButtonPressed(ActionEvent actionEvent) throws IOException {
        Car car = new Car();
        car.setModel(modelTextField.getText());
        car.setYear(Integer.parseInt(yearTextField.getText()));
        BigDecimal price = new BigDecimal(priceTextField.getText());
        car.setPrice(price);
        if (Objects.equals(carStatusChoiceBox.getValue(), "AVAILABLE")) {
            car.setCar_status(CarStatus.AVAILABLE);
        } else {
            car.setCar_status(CarStatus.SOLD);
        }

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(car));
        request.setRequestType(RequestType.ADD_CAR);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String answer = ClientSocket.getInstance().getIn().readLine();
        Response response = new Gson().fromJson(answer, Response.class);

    }
}
