package org.limir.controllers.favorite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.limir.models.dto.CarDTO;

public class FavoriteCar {

    @FXML
    private TableView<CarDTO> favoriteCarTable;

    @FXML
    private TableColumn<CarDTO, String> favoriteCarModelColumn;

    @FXML
    private TableColumn<CarDTO, Integer> favoriteCarYearColumn;

    @FXML
    private TableColumn<CarDTO, String> favoriteCarStatusColumn;

    @FXML
    private TableColumn<CarDTO, String> favoriteCarCompanyColumn;

    @FXML
    private TableColumn<CarDTO, String> favoriteCarPriceColumn;

    private final ObservableList<CarDTO> favoriteCars = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        favoriteCarModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        favoriteCarYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        favoriteCarStatusColumn.setCellValueFactory(new PropertyValueFactory<>("carStatus"));
        favoriteCarCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        favoriteCarPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        favoriteCarTable.setItems(favoriteCars);
    }

    public void reloadFavoriteCars(ObservableList<CarDTO> allCars) {
        favoriteCars.clear();
        favoriteCars.addAll(allCars.filtered(CarDTO::isFavorite));
    }
}