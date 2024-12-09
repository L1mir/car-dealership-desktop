package org.limir.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.limir.controllers.order.OrderHistory;
import org.limir.controllers.profile.UserProfile;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    private Button priceAfterCouponeButton;

    @FXML
    private Button pdfOrderButton;

    @FXML
    private Button profileButton;

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
        favoriteColumn.setCellFactory(tc -> new CheckBoxTableCell<>(index -> {
            CarDTO car = carTable.getItems().get(index);
            return car.favoriteProperty();
        }));

        carTable.setEditable(true);
        favoriteColumn.setEditable(true);

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

        if (selectedCar == null) {
            showAlert(Alert.AlertType.WARNING, "Выберите машину", "Выберите машину для заказа.");
            return;
        }

        if ("SOLD".equalsIgnoreCase(selectedCar.getCarStatus())) {
            showAlert(Alert.AlertType.WARNING, "Машина продана", "Эта машина уже продана. Выберите другую машину.");
            return;
        }

        String selectedPaymentMethod = (String) paymentMethodChoiceBox.getValue();
        if (selectedPaymentMethod == null) {
            showAlert(Alert.AlertType.WARNING, "Метод оплаты", "Выберите метод оплаты.");
            return;
        }

        UserDTO currentUser = CurrentUser.getUser();

        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Ошибка авторизации", "Пользователь не авторизован!");
            return;
        }

        String enteredCoupon = couponTextField.getText().trim().toUpperCase();
        Coupon coupon = null;
        try {
            coupon = Coupon.valueOf(enteredCoupon);
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Неверный купон", "Купон недействителен. Проверьте правильность введённого кода.");
        }

        if (coupon != null) {
            applyCoupon(selectedCar, coupon);
            showAlert(Alert.AlertType.INFORMATION, "Купон принят", "Купон успешно применён: " + coupon);
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
                showAlert(Alert.AlertType.INFORMATION, "Успешный заказ", "Заказ успешно создан!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Ошибка заказа", "Ошибка создания заказа: " + purchaseOrderResponse.getResponseStatus());
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    private void handlePriceAfterCouponButton(ActionEvent event) {
        SceneManager.showScene("price-after-coupon");
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

    @FXML
    private void handlePdfOrderButton(ActionEvent event) {
        try {
            Response readOrderResponse = RequestHandler.sendRequest(RequestType.READ_ORDERS, null);

            if (readOrderResponse.getResponseStatus() == ResponseStatus.OK) {
                List<OrderDTO> orderDTOS = new Gson().fromJson(readOrderResponse.getResponseData(), new TypeToken<List<OrderDTO>>() {}.getType());

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save PDF File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

                if (file != null) {
                    PDDocument document = new PDDocument();

                    PDPage page = new PDPage();
                    document.addPage(page);

                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        contentStream.setLeading(14.5f);
                        contentStream.newLineAtOffset(50, 750);

                        contentStream.showText("Orders Report");
                        contentStream.newLine();
                        contentStream.newLine();

                        for (OrderDTO order : orderDTOS) {
                            contentStream.showText("Total Price: " + order.getTotalPrice());
                            contentStream.newLine();
                            contentStream.showText("Payment Method: " + order.getPaymentMethod());
                            contentStream.newLine();
                            contentStream.showText("Company Name: " + order.getCompanyName());
                            contentStream.newLine();
                            contentStream.showText("Date: " + order.getDate());
                            contentStream.newLine();
                            contentStream.newLine();
                        }

                        contentStream.endText();
                    }

                    document.save(file);
                    document.close();

                    System.out.println("Orders exported to PDF: " + file.getAbsolutePath());
                } else {
                    System.out.println("File save operation was canceled.");
                }
            } else {
                System.out.println("Error loading orders: " + readOrderResponse.getResponseStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating PDF: " + e.getMessage());
        }
    }


    @FXML
    private void handleProfileButton(ActionEvent event) {
        SceneManager.showScene("user-profile");

        UserProfile userProfile = (UserProfile) SceneManager.getController("user-profile");

        try {
            userProfile.reloadUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
