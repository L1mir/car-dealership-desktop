package org.limir.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.CurrentUser;
import org.limir.models.dto.CarDTO;
import org.limir.models.dto.UserDTO;
import org.limir.models.entities.Car;
import org.limir.models.enums.PaymentMethod;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.Request;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;
import org.limir.utility.ClientSocket;
import org.limir.models.dto.OrderDTO;

import javafx.scene.control.TableView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CustomerMenu {
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
    private TableColumn<Car, String> carCompanyColumn;

    @FXML
    private Button purchaseButton;

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox paymentMethodChoiceBox;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("login");
    }

    @FXML
    public void initialize() {
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        carYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        carPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        carStatusColumn.setCellValueFactory(new PropertyValueFactory<>("carStatus"));
        carCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        paymentMethodChoiceBox.getItems().addAll("CASH", "CARD", "CREDIT");

        try {
            loadCarsFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadCarsFromServer() throws IOException {
        Request request = new Request();
        request.setRequestType(RequestType.READ_CARS);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String answer = ClientSocket.getInstance().getIn().readLine();
        Response response = new Gson().fromJson(answer, Response.class);

        if (response.getResponseStatus() == ResponseStatus.OK) {
            List<CarDTO> cars = new Gson().fromJson(response.getResponseData(), new TypeToken<List<CarDTO>>() {
            }.getType());

            ObservableList<CarDTO> carsObserver = FXCollections.observableArrayList(cars);
            carTable.setItems(carsObserver);
        } else {
            System.out.println("Error loading cars: " + response.getResponseStatus());
        }
    }

    @FXML
    private void handleOrderButton(ActionEvent event) {
        CarDTO selectedCar = carTable.getSelectionModel().getSelectedItem();

        String selectedPaymentMethod = (String) paymentMethodChoiceBox.getValue();
        if (selectedPaymentMethod == null) {
            System.out.println("Выберите метод оплаты.");
            return;
        }

        if (selectedCar == null) {
            System.out.println("Выберите машину для заказа.");
            return;
        }

        UserDTO currentUser = CurrentUser.getUser();

        if (currentUser == null) {
            System.out.println("Пользователь не авторизован!");
            return;
        }

        try {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(selectedPaymentMethod.toUpperCase());

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setCartId(selectedCar.getCarId());
            orderDTO.setCompanyId(selectedCar.getCompanyId());
            orderDTO.setUserName(currentUser.getUsername());
            orderDTO.setCompanyName(selectedCar.getCompanyName());
            orderDTO.setTotalPrice(selectedCar.getPrice());
            orderDTO.setDate(new Date());
            orderDTO.setPaymentMethod(paymentMethod);

            Response createOrderResponse = RequestHandler.sendRequest(RequestType.CREATE_ORDER, orderDTO);
            Response createPaymentResponse = RequestHandler.sendRequest(RequestType.CREATE_PAYMENT, orderDTO);

            if (createOrderResponse.getResponseStatus() == ResponseStatus.OK) {
                System.out.println("Заказ успешно создан!");
            } else {
                System.out.println("Ошибка создания заказа: " + createOrderResponse.getResponseStatus());
            }
            if (createPaymentResponse.getResponseStatus() == ResponseStatus.OK) {
                System.out.println("Оплата успешно проведена!");
            } else {
                System.out.println("Ошибка создания оплаты: " + createPaymentResponse.getResponseStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePurchaseButton(ActionEvent event) {
        CarDTO selectedCar = carTable.getSelectionModel().getSelectedItem();

        String selectedPaymentMethod = (String) paymentMethodChoiceBox.getValue();
        if (selectedPaymentMethod == null) {
            System.out.println("Выберите метод оплаты.");
            return;
        }

        if (selectedCar == null) {
            System.out.println("Выберите машину для заказа.");
            return;
        }

        UserDTO currentUser = CurrentUser.getUser();

        if (currentUser == null) {
            System.out.println("Пользователь не авторизован!");
            return;
        }

        try {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(selectedPaymentMethod.toUpperCase());

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setCartId(selectedCar.getCarId());
            orderDTO.setCompanyId(selectedCar.getCompanyId());
            orderDTO.setUserName(currentUser.getUsername());
            orderDTO.setCompanyName(selectedCar.getCompanyName());
            orderDTO.setTotalPrice(selectedCar.getPrice());
            orderDTO.setDate(new Date());
            orderDTO.setPaymentMethod(paymentMethod);

            Response purchaseOrderResponse = RequestHandler.sendRequest(RequestType.PURCHASE_ORDER, orderDTO);

            if (purchaseOrderResponse.getResponseStatus() == ResponseStatus.OK) {
                System.out.println("Заказ успешно создан!");
            } else {
                System.out.println("Ошибка создания заказа: " + purchaseOrderResponse.getResponseStatus());
            }
            if (purchaseOrderResponse.getResponseStatus() == ResponseStatus.OK) {
                System.out.println("Оплата успешно проведена!");
            } else {
                System.out.println("Ошибка создания оплаты: " + purchaseOrderResponse.getResponseStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
