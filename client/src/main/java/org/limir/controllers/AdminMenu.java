package org.limir.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.limir.controllers.car.CarOperations;
import org.limir.controllers.company.CompanyOperations;
import org.limir.controllers.profile.UserProfile;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.CurrentUser;
import org.limir.models.dto.OrderDTO;
import org.limir.models.dto.UserDTO;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private Button backButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button saveOrderFileButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("login");
    }

    private final CarOperations carOperationsController = new CarOperations();
    private final CompanyOperations companyOperationsController = new CompanyOperations();

    @FXML
    public void initialize() {
        carOperationsController.initialize(carOperationsChoiceBox, carExecuteButton);
        companyOperationsController.initialize(companyOperationsChoiceBox, companyExecuteButton);
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
            // Преобразование JSON ответа в список объектов OrderDTO
            List<OrderDTO> orderDTOS = new Gson().fromJson(readOrderResponse.getResponseData(), new TypeToken<List<OrderDTO>>() {}.getType());

            // Создание списка для сохранения только нужных полей
            List<Map<String, Object>> formattedOrders = orderDTOS.stream().map(order -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("totalPrice", order.getTotalPrice());
                map.put("paymentMethod", order.getPaymentMethod());
                map.put("companyName", order.getCompanyName());
                map.put("date", order.getDate());
                return map;
            }).toList();

            // Создание экземпляра Gson с форматированием
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Преобразование списка в форматированный JSON
            String json = gson.toJson(formattedOrders);

            // Сохранение JSON в файл
            Path filePath = Paths.get("orders.json");
            Files.writeString(filePath, json, StandardCharsets.UTF_8);

            System.out.println("Orders saved to file: " + filePath.toAbsolutePath());
        } else {
            System.out.println("Error loading orders: " + readOrderResponse.getResponseStatus());
        }
    }
}