package org.limir.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.limir.controllers.car.CarOperations;
import org.limir.controllers.company.CompanyOperations;
import org.limir.controllers.employee.EmployeeOperations;
import org.limir.controllers.profile.UserProfile;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.OrderDTO;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminMenu {
    @FXML
    private ChoiceBox<String> carOperationsChoiceBox;

    @FXML
    private Button carExecuteButton;

    @FXML
    private ChoiceBox<String> companyOperationsChoiceBox;

    @FXML
    private Button companyExecuteButton;

    @FXML
    private ChoiceBox<String> employeeOperationsChoiceBox;

    @FXML
    private Button employeeExecuteButton;

    @FXML
    private Button backButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button saveOrderFileButton;

    @FXML
    private Button carStatusButton;

    @FXML
    private Button companyGeographicButton;

    @FXML
    private Button userRolesButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("login");
    }

    private final CarOperations carOperationsController = new CarOperations();
    private final CompanyOperations companyOperationsController = new CompanyOperations();
    private final EmployeeOperations employeeOperationsController = new EmployeeOperations();

    @FXML
    public void initialize() {
        carOperationsController.initialize(carOperationsChoiceBox, carExecuteButton);
        companyOperationsController.initialize(companyOperationsChoiceBox, companyExecuteButton);
        employeeOperationsController.initialize(employeeOperationsChoiceBox, employeeExecuteButton);
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

    @FXML
    private void handleSaveOrderFileButton(ActionEvent event) throws IOException {
        Response readOrderResponse = RequestHandler.sendRequest(RequestType.READ_ORDERS, null);

        if (readOrderResponse.getResponseStatus() == ResponseStatus.OK) {
            List<OrderDTO> orderDTOS = new Gson().fromJson(readOrderResponse.getResponseData(), new TypeToken<List<OrderDTO>>() {}.getType());

            List<Map<String, Object>> formattedOrders = orderDTOS.stream().map(order -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("totalPrice", order.getTotalPrice());
                map.put("paymentMethod", order.getPaymentMethod());
                map.put("companyName", order.getCompanyName());
                map.put("date", order.getDate());
                return map;
            }).toList();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String json = gson.toJson(formattedOrders);

            Path filePath = Paths.get("orders.json");
            Files.writeString(filePath, json, StandardCharsets.UTF_8);

            System.out.println("Orders saved to file: " + filePath.toAbsolutePath());
        } else {
            System.out.println("Error loading orders: " + readOrderResponse.getResponseStatus());
        }
    }

    @FXML
    private void handleCarStatusButton(ActionEvent event) {
        SceneManager.showScene("car-status-diagram");
    }

    @FXML
    private void handleUserRoleButton(ActionEvent event) {
        SceneManager.showScene("user-role-diagram");
    }

    @FXML
    private void handleCompanyGeographicButtonButton(ActionEvent event) {
        SceneManager.showScene("company-email-diagram");
    }
}