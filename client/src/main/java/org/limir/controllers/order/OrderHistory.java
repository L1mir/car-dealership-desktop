package org.limir.controllers.order;

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
import org.limir.models.dto.OrderDTO;
import org.limir.models.dto.UserDTO;
import org.limir.models.entities.Car;
import org.limir.models.entities.Company;
import org.limir.models.entities.Payment;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderHistory {
    @FXML
    private TableView<OrderDTO> orderHistoryTable;

    @FXML
    private TableColumn<Car, BigDecimal> carPriceColumn;

    @FXML
    private TableColumn<Payment, String> paymentMethodColumn;

    @FXML
    private TableColumn<Company, String> companyNameColumn;

    @FXML
    private TableColumn<Date, String> datePaymentColumn;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        carPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        datePaymentColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        SceneManager.showScene("customer-menu");
    }

    public void reloadOrderHistory() throws IOException {
        loadOrderHistory();
    }

    private void loadOrderHistory() throws IOException {
        UserDTO userDTO = CurrentUser.getUser();

        Response readOrderResponse = RequestHandler.sendRequest(RequestType.READ_USER_ORDERS, userDTO);

        if (readOrderResponse.getResponseStatus() == ResponseStatus.OK) {
            List<OrderDTO> orderDTOS = new Gson().fromJson(readOrderResponse.getResponseData(), new TypeToken<List<OrderDTO>>() {
            }.getType());

            ObservableList<OrderDTO> orderObserver = FXCollections.observableArrayList(orderDTOS);
            orderHistoryTable.setItems(orderObserver);
        } else {
            System.out.println("Error loading cars: " + readOrderResponse.getResponseStatus());
        }
    }
}
