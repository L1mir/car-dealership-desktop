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

public class CompanyEmailsDiagram implements Initializable {

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
                Map<String, Integer> emailStats = calculateEmailStatistics(companies);
                buildChart(emailStats);
            } else {
                System.out.println("Error loading companies: " + readCompaniesResponse.getResponseStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load companies from server.");
        }
    }

    private Map<String, Integer> calculateEmailStatistics(List<CompanyDTO> companies) {
        Map<String, Integer> emailStats = new HashMap<>();
        emailStats.put("With Email", 0);
        emailStats.put("Without Email", 0);

        for (CompanyDTO company : companies) {
            if (company.getEmail() != null && !company.getEmail().isEmpty()) {
                emailStats.put("With Email", emailStats.get("With Email") + 1);
            } else {
                emailStats.put("Without Email", emailStats.get("Without Email") + 1);
            }
        }

        return emailStats;
    }

    private void buildChart(Map<String, Integer> emailStats) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Email Availability");

        for (Map.Entry<String, Integer> entry : emailStats.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        companyBarChart.getData().clear();
        companyBarChart.getData().add(series);
    }

}

