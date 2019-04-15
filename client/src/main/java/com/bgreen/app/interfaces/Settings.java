package com.bgreen.app.interfaces;

import com.bgreen.app.models.User;
import com.bgreen.app.services.ImageService;
import com.bgreen.app.services.UserService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings implements Initializable {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private UserService userInfo = new UserService();

    private ImageService imageService = new ImageService();

    @FXML
    private Button logout;

    @FXML
    private ImageView userProfile;

    @FXML
    private Button changeImage;

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label pointCount;

    @FXML
    private Label co2;

    @FXML
    private TextField text;

    @FXML
    private PasswordField passUsername;

    @FXML
    private Button confirmUsername;

    @FXML
    private PasswordField pass1;

    @FXML
    private PasswordField pass2;

    @FXML
    private Button confirmPassword;

    @FXML
    private TextField email1;

    @FXML
    private TextField email2;

    @FXML
    private PasswordField passEmail;

    @FXML
    private Button confirmEmail;

    @FXML
    private BorderPane bp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setOnMouseClicked(e -> removeHighLight(text, passUsername));

        passUsername.setOnMouseClicked(e -> removeHighLight(text, passUsername));

        pass1.setOnMouseClicked(e -> removeHighLight(pass1, pass2));

        pass2.setOnMouseClicked(e -> removeHighLight(pass1, pass2));

        email1.setOnMouseClicked(e -> removeHighLight(email1, email2, passEmail));

        email2.setOnMouseClicked(e -> removeHighLight(email1, email2, passEmail));

        passEmail.setOnMouseClicked(e -> removeHighLight(email1, email2, passEmail));

        logout.setOnAction(e -> {
            LoginScreen login = new LoginScreen();
            login.start(new Stage());
            Stage stage = (Stage) bp.getScene().getWindow();
            stage.close();
        });

        Image userProfileIcon = null;
        try {
            String path = (new UserService()).getUserInfo().getProfilePicture();
            userProfileIcon = new Image(imageService.downloadImage(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        userProfile.setImage(userProfileIcon);

        changeImage.setOnAction(e ->
            changeProfilePicture());

        userProfile.setOnMouseClicked(e -> changeProfilePicture());

        User getProfile = userInfo.getUserInfo();

        name.setText(getProfile.getUsername());
        email.setText(getProfile.getEmail());
        pointCount.setText(Integer.toString((int) getProfile.getPoints()));
        co2.setText(((int) getProfile.getPoints() / 10) + "kg");

        confirmUsername.disableProperty().bind(
                text.textProperty().isEmpty()
                        .or(
                                passUsername.textProperty().isEmpty()
                        ));

        confirmUsername.setOnAction(e -> {
            User getInfo = userInfo.getUserInfo();
            User user = new User(text.getText(), passUsername.getText(),
                    getInfo.getEmail(), (int) getInfo.getPoints());
            if (this.checkUsernameFields()) {
                name.setText(text.getText());
                adjustUserInfo(text, user);
                passUsername.clear();
            } else {
                text.getStyleClass().add("highlight-field");
                passUsername.getStyleClass().add("highlight-field");
            }

        });

        confirmPassword.disableProperty().bind(
                pass1.textProperty().isEmpty()
                        .or(
                                pass2.textProperty().isEmpty()
                        ));

        confirmPassword.setOnAction(e -> {
            User getInfo = userInfo.getUserInfo();

            User user = new User(getInfo.getUsername(), pass2.getText(),
                    getInfo.getEmail(), (int) getInfo.getPoints());
            if (this.checkPasswordFields()) {
                adjustUserInfo(pass1, user);
                pass2.clear();
            } else {
                pass1.getStyleClass().add("highlight-field");
                pass2.getStyleClass().add("highlight-field");
            }
        });

        confirmEmail.disableProperty().bind(
                passEmail.textProperty().isEmpty()
                        .or(
                                email1.textProperty().isEmpty()
                                    .or(
                                            email2.textProperty().isEmpty()
                                    )
                        ));

        confirmEmail.setOnAction(e -> {
            User getInfo = userInfo.getUserInfo();
            User user = new User(getInfo.getUsername(), passEmail.getText(),
                    email2.getText(), (int) getInfo.getPoints());

            if (this.checkEmailFields()) {
                email.setText(email2.getText());
                adjustUserInfo(email1, user);
                email2.clear();
                passEmail.clear();
            } else {
                email1.getStyleClass().add("highlight-field");
                email2.getStyleClass().add("highlight-field");
                passEmail.getStyleClass().add("highlight-field");
            }
        });
    }

    private void adjustUserInfo(TextField text, User user) {
        userInfo.modifyUserInfo(user);
        text.clear();
    }

    /**
     * configures the file chooser for profile pic.
     * @param fileChooser filechooser
     */
    public static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Choose profile picture");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void changeProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            imageService.uploadImage(file.getAbsolutePath());
            try {
                String path = (new UserService()).getUserInfo().getProfilePicture();
                userProfile.setImage(new Image(imageService.downloadImage(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * gets the settings tab content to display in the UI.
     * @return content
     */
    public Parent getSettings() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/settings.fxml"));

            root.getStylesheets().add("/css/root.css");
            root.getStylesheets().add("/css/settings.css");

            return root;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    private boolean checkUsernameFields() {
        return (text.getText().length() >= 3 && text.getText().length() <= 100)
                && (passUsername.getText().length() >= 3 && passUsername.getText().length() <= 100);
    }

    private boolean checkPasswordFields() {
        return (pass1.getText().length() >= 3 && pass1.getText().length() <= 100)
                && (pass2.getText().length() >= 3 && pass2.getText().length() <= 100)
                && (pass1.getText().equals(pass2.getText()));
    }

    private boolean checkEmailFields() {
        return (email1.getText().length() >= 3
                && email1.getText().length() <= 100
                && validate(email1.getText()))
                && (email2.getText().length() >= 3 && email2.getText().length() <= 100
                && validate(email2.getText()))
                && (passEmail.getText().length() >= 3 && passEmail.getText().length() <= 100)
                && (email1.getText().equals(email2.getText()));
    }

    private void removeHighLight(TextField text, TextField passwordField) {
        text.getStyleClass().remove("highlight-field");
        passwordField.getStyleClass().remove("highlight-field");
    }

    private void removeHighLight(TextField text, TextField text2, TextField emailField) {
        text.getStyleClass().remove("highlight-field");
        text2.getStyleClass().remove("highlight-field");
        emailField.getStyleClass().remove("highlight-field");
    }
}
