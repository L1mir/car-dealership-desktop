package org.limir.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.entities.Person;
import org.limir.models.entities.User;
import org.limir.models.enums.Gender;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.ResponseStatus;
import org.limir.models.enums.UserRole;
import org.limir.models.tcp.RequestHandler;
import org.limir.models.tcp.Response;

import java.io.IOException;

public class Register {
    public TextField textFieldLogin;

    public PasswordField textFieldPassword;

    public TextField textFieldName;

    public TextField textFieldSurname;

    public Spinner<Integer> spinnerAge;

    public RadioButton radioButtonMale;

    public RadioButton radioButtonFemale;

    public TextField textFieldEmail;

    public TextField textFieldPhoneNumber;

    public TextField textFieldAddress;

    public Button buttonRegister;

    @FXML
    private Button backButton;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        SceneManager.showScene("login");
    }

    private final SpinnerValueFactory<Integer> valueMarkFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 120, 18); // Установка диапазона и начального значения

    public void initialize() {
        spinnerAge.setValueFactory(valueMarkFactory);
    }

    public void signUpPressed(ActionEvent actionEvent) throws IOException {
        Person person = new Person();
        person.setFirst_name(textFieldName.getText());
        person.setLast_name(textFieldSurname.getText());

        User user = new User();
        user.setUsername(textFieldLogin.getText());
        user.setPassword(textFieldPassword.getText());
        user.setUser_role(UserRole.CUSTOMER);
        user.setEmail(textFieldEmail.getText());
        user.setPhone(textFieldPhoneNumber.getText());
        user.setAddress(textFieldAddress.getText());

        Integer age = spinnerAge.getValue();
        person.setAge(age != null ? age : 18);

        if (radioButtonMale.isSelected()) {
            person.setGender(Gender.MALE);
        } else {
            person.setGender(Gender.FEMALE);
        }
        person.setUserData(user);

        try {
            Response response = RequestHandler.sendRequest(RequestType.REGISTER, person);

            if (response.getResponseStatus() == ResponseStatus.OK) {
                System.out.println("Registration successful!");
                SceneManager.showScene("login");
            } else {
                System.out.println("Registration failed: ");
            }
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

}