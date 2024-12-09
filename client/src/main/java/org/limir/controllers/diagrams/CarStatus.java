package org.limir.controllers.diagrams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.CarDTO;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CarStatus implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private PieChart carStatusChart;

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
            Response readCarsResponse = RequestHandler.sendRequest(RequestType.READ_CARS, null);

            if (readCarsResponse.getResponseStatus() == ResponseStatus.OK) {
                List<CarDTO> cars = new Gson().fromJson(readCarsResponse.getResponseData(), new TypeToken<List<CarDTO>>() {}.getType());
                Map<String, Integer> statusCount = calculateCarStatusDistribution(cars);
                buildChart(statusCount);
            } else {
                System.out.println("Error loading cars: " + readCarsResponse.getResponseStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load cars from server.");
        }
    }

    private Map<String, Integer> calculateCarStatusDistribution(List<CarDTO> cars) {
        Map<String, Integer> statusCount = new HashMap<>();

        for (CarDTO car : cars) {
            String status = car.getCarStatus();
            statusCount.put(status, statusCount.getOrDefault(status, 0) + 1);
        }

        return statusCount;
    }

    private void buildChart(Map<String, Integer> statusCount) {
        List<PieChart.Data> pieChartData = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : statusCount.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        carStatusChart.setData(javafx.collections.FXCollections.observableArrayList(pieChartData));
    }
}
