package org.limir.controllers;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.limir.models.entities.Person;
import org.limir.models.entities.User;
import org.limir.models.enums.Gender;
import org.limir.models.enums.RequestType;
import org.limir.models.enums.UserRole;
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
    public TextField textFieldAddress;
    public Button buttonBack;
    public Button buttonRegister;

    private SpinnerValueFactory<Integer> valueMarkFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 120, 18); // Установка диапазона и начального значения

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

        //System.out.println(person.toString());

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String personJson = gson.toJson(person);

        Request request = new Request();
        request.setRequestMessage(personJson);
        request.setRequestType(RequestType.REGISTER);

        String jsonRequest = gson.toJson(request);
        //System.out.println("Отправляемый JSON запрос: " + jsonRequest);
        ClientSocket.getInstance().getOut().println(jsonRequest);
        ClientSocket.getInstance().getOut().flush();
    }

}
