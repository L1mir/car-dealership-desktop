package org.limir.controllers.coupon;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.dto.CarDTO;
import org.limir.models.enums.Coupon;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;
import java.math.BigDecimal;

public class PriceAfterCoupon {

    @FXML
    private VBox priceBeforeCouponVBox;
    @FXML
    private VBox priceAfterCouponVBox;
    @FXML
    private Label priceBeforeValueLabel;
    @FXML
    private Label priceAfterValueLabel;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField couponTextField;

    @FXML
    private Button backButton;

    @FXML
    private void handleBackButton() {
        SceneManager.showScene("customer-menu");
    }

    @FXML
    private void handleSubmitButton() throws IOException {
        Response readCarResponse = RequestHandler.sendRequest(RequestType.READ_CAR_BY_MODEL, modelTextField.getText());

        if (readCarResponse.getResponseStatus() == ResponseStatus.OK) {
            Gson gson = new Gson();
            CarDTO car = gson.fromJson(readCarResponse.getResponseData(), CarDTO.class);

            priceBeforeValueLabel.setText(car.getPrice().toString());

            String enteredCoupon = couponTextField.getText().trim().toUpperCase();
            Coupon coupon = null;
            try {
                coupon = Coupon.valueOf(enteredCoupon);
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный купон.");
            }

            if (coupon != null) {
                applyCoupon(car, coupon);
            }

            priceAfterValueLabel.setText(car.getPrice().toString());

            priceBeforeCouponVBox.setVisible(true);
            priceAfterCouponVBox.setVisible(true);

        } else {
            System.out.println("Ошибка при получении данных о машине.");
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
}

