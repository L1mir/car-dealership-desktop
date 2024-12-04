package org.limir.controllers.diagrams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.CarDTO;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AvgCompanyPrice implements Initializable {

    @FXML
    private BarChart<String, Number> priceComparisonChart;

    @FXML
    private Button backButton;

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        SceneManager.showScene("customer-menu");
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

                Map<String, List<BigDecimal>> companyPrices = new HashMap<>();
                for (CarDTO car : cars) {
                    companyPrices
                            .computeIfAbsent(car.getCompanyName(), k -> new ArrayList<>())
                            .add(car.getPrice());
                }

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                for (Map.Entry<String, List<BigDecimal>> entry : companyPrices.entrySet()) {
                    double averagePrice = entry.getValue().stream()
                            .mapToDouble(BigDecimal::doubleValue)
                            .average()
                            .orElse(0);
                    series.getData().add(new XYChart.Data<>(entry.getKey(), averagePrice));
                }

                priceComparisonChart.getData().clear();
                priceComparisonChart.getData().add(series);
            } else {
                System.out.println("Error loading cars: " + readCarsResponse.getResponseStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load cars from server.");
        }
    }
}
