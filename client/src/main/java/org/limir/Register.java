package org.limir;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.limir.models.entities.Person;
import org.limir.models.entities.User;
import org.limir.models.enums.Gender;
import org.limir.models.enums.RequestType;
import org.limir.models.tcp.Request;
import org.limir.utility.ClientSocket;
import com.google.gson.GsonBuilder;

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
    public TextField textFieldAddress; // Если нужно поле для адреса
    public Button buttonBack;
    public Button buttonRegister;

    private SpinnerValueFactory<Integer> valueMarkFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 120, 18); // Установка диапазона и начального значения

    public void initialize() {
        spinnerAge.setValueFactory(valueMarkFactory);
    }

    public void signUpPressed(ActionEvent actionEvent) throws IOException {
        User user = new User();
        user.setUsername(textFieldLogin.getText());
        user.setPassword(textFieldPassword.getText());
        user.setEmail(textFieldEmail.getText());
        user.setPhone(textFieldPhoneNumber.getText());
        user.setAddress(textFieldAddress.getText());

        Person person = new Person();
        person.setFirst_name(textFieldName.getText());
        person.setLast_name(textFieldSurname.getText());

        Integer age = spinnerAge.getValue();
        if (age == null) {
            age = 18;
        }
        person.setAge(age);

        if (radioButtonMale.isSelected()) {
            person.setGender(Gender.MALE);
        } else {
            person.setGender(Gender.FEMALE);
        }

        person.setUserData(user);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String jsonRequest = gson.toJson(person);

        Request request = new Request();
        request.setRequestMessage(jsonRequest);
        request.setRequestType(RequestType.REGISTER);

        ClientSocket.getInstance().getOut().println(request);
        ClientSocket.getInstance().getOut().flush();
    }
}
