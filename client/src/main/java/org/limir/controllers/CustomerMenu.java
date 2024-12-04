package org.limir.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.limir.controllers.order.OrderHistory;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.CurrentUser;
import org.limir.models.dto.CarDTO;
import org.limir.models.dto.UserDTO;
import org.limir.models.entities.Car;
import org.limir.models.enums.Coupon;
import org.limir.models.enums.PaymentMethod;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;
import org.limir.models.dto.OrderDTO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    private TextField couponTextField;

    @FXML
    private Button diagramButton;

    @FXML
    private Button readCompaniesButton;

    @FXML
    private Button exportButton;


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

        String enteredCoupon = couponTextField.getText().trim().toUpperCase();
        Coupon coupon = null;
        try {
            coupon = Coupon.valueOf(enteredCoupon);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный купон.");
        }

        if (coupon != null) {
            System.out.println("Купон принят: " + coupon);
            applyCoupon(selectedCar, coupon);
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

    private void applyCoupon(CarDTO selectedCar, Coupon coupon) {
        switch (coupon) {
            case LOYALTY15:
                selectedCar.setPrice(selectedCar.getPrice().multiply(BigDecimal.valueOf(0.85)));
                break;
            case ROYALTY20:
                selectedCar.setPrice(selectedCar.getPrice().multiply(BigDecimal.valueOf(0.80)));
                break;
            case SERVICE30:
                selectedCar.setPrice(selectedCar.getPrice().multiply(BigDecimal.valueOf(0.70)));
                break;
            default:
                System.out.println("Купон не существует.");
                break;
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


    @FXML
    private void handleDiagramButton(ActionEvent event) {
        SceneManager.showScene("avg-company-price");
    }

    @FXML
    private void handleReadCompaniesButton(ActionEvent event) {
        SceneManager.showScene("read-companies");
    }

    @FXML
    private void handleExportButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Model,Year,Price,Status,Company");
                writer.newLine();

                ObservableList<CarDTO> cars = carTable.getItems();

                for (CarDTO car : cars) {
                    writer.write(String.format("%s,%d,%.2f,%s,%s",
                            car.getModel(),
                            car.getYear(),
                            car.getPrice(),
                            car.getCarStatus(),
                            car.getCompanyName()));
                    writer.newLine();
                }

                System.out.println("Данные успешно экспортированы.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка при экспорте данных.");
            }
        }
    }
}
