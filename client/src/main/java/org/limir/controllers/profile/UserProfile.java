package org.limir.controllers.profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.limir.controllers.sceneUtility.SceneManager;
import org.limir.models.CurrentUser;
import org.limir.models.dto.UserDTO;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;

public class UserProfile {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button chooseImageButton;

    @FXML
    private Button backButton;

    private UserDTO currentUserDTO;

    @FXML
    private void handleBackButton(ActionEvent event) {
        SceneManager.showScene("customer-menu");
    }

    public void reloadUser() throws IOException {
        loadUser();
    }

    private void loadUser() throws IOException {
        currentUserDTO = CurrentUser.getUser();

        usernameLabel.setText(currentUserDTO.getUsername());
        nameLabel.setText(currentUserDTO.getFirstName() + " " + currentUserDTO.getLastName());
        emailLabel.setText(currentUserDTO.getEmail());
        phoneLabel.setText(currentUserDTO.getPhone());
        addressLabel.setText(currentUserDTO.getAddress());
        genderLabel.setText(currentUserDTO.getGender().toString());
        ageLabel.setText(String.valueOf(currentUserDTO.getAge()));
    }

    @FXML
    private void handleChooseImageButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                FileInputStream imageStream = new FileInputStream(selectedFile);
                Image profileImageFile = new Image(imageStream);
                profileImage.setImage(profileImageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
