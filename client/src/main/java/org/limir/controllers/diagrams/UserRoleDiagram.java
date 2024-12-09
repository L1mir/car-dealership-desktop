package org.limir.controllers.diagrams;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.UserDTO;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UserRoleDiagram implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private LineChart<String, Number> userRoleChart;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        SceneManager.showScene("admin-menu");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataAndBuildChart();
    }

    private void loadDataAndBuildChart() {
        try {
            Response readUsersResponse = RequestHandler.sendRequest(RequestType.READ_USERS, null);

            if (readUsersResponse.getResponseStatus() == ResponseStatus.OK) {
                List<UserDTO> users = new Gson().fromJson(readUsersResponse.getResponseData(), new TypeToken<List<UserDTO>>() {}.getType());
                Map<String, Integer> roleCount = calculateUserRoleDistribution(users);
                buildChart(roleCount);
            } else {
                System.out.println("Error loading users: " + readUsersResponse.getResponseStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load users from server.");
        }
    }

    private Map<String, Integer> calculateUserRoleDistribution(List<UserDTO> users) {
        Map<String, Integer> roleCount = new HashMap<>();

        for (UserDTO user : users) {
            String role = user.getUserRole().toString();
            roleCount.put(role, roleCount.getOrDefault(role, 0) + 1);
        }

        return roleCount;
    }

    private void buildChart(Map<String, Integer> roleCount) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("User Role Distribution");

        for (Map.Entry<String, Integer> entry : roleCount.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        userRoleChart.getData().clear();
        userRoleChart.getData().add(series);
    }
}
