package org.limir.controllers.diagrams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.CompanyDTO;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CompanyGeographic implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private BarChart<String, Number> companyBarChart;

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
            Response readCompaniesResponse = RequestHandler.sendRequest(RequestType.READ_COMPANIES, null);

            if (readCompaniesResponse.getResponseStatus() == ResponseStatus.OK) {
                List<CompanyDTO> companies = new Gson().fromJson(readCompaniesResponse.getResponseData(), new TypeToken<List<CompanyDTO>>() {}.getType());
                Map<String, Integer> companyCount = calculateCompanyDistribution(companies);
                buildChart(companyCount);
            } else {
                System.out.println("Error loading companies: " + readCompaniesResponse.getResponseStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load companies from server.");
        }
    }

    private Map<String, Integer> calculateCompanyDistribution(List<CompanyDTO> companies) {
        Map<String, Integer> companyCount = new HashMap<>();

        for (CompanyDTO company : companies) {
            String name = company.getName();
            companyCount.put(name, companyCount.getOrDefault(name, 0) + 1);
        }

        return companyCount;
    }

    private void buildChart(Map<String, Integer> companyCount) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Company Distribution");

        for (Map.Entry<String, Integer> entry : companyCount.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        companyBarChart.getData().clear();
        companyBarChart.getData().add(series);
    }
}

