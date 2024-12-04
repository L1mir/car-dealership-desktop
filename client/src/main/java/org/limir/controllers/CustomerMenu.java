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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.limir.controllers.order.OrderHistory;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.CurrentUser;
import org.limir.models.dto.CarDTO;
import org.limir.models.dto.UserDTO;
import org.limir.models.entities.Car;
import org.limir.models.enums.PaymentMethod;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;
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
    private Button orderHistoryButton;

    @FXML
    private TableColumn<CarDTO, Boolean> favoriteColumn;

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

        favoriteColumn.setCellValueFactory(cellData -> cellData.getValue().favoriteProperty());
        favoriteColumn.setCellFactory(tc -> new CheckBoxTableCell<>());

        paymentMethodChoiceBox.getItems().addAll("CASH", "CARD", "CREDIT");

        try {
            loadCarsFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    private void handleOrderHistoryButton(ActionEvent event) {
        SceneManager.showScene("order-history");

        OrderHistory orderHistoryController = (OrderHistory) SceneManager.getController("order-history");
        try {
            orderHistoryController.reloadOrderHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
