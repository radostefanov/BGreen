package com.bgreen.app.interfaces;

import com.bgreen.app.models.User;
import com.bgreen.app.services.AuthenticationService;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * allows user to register as a new user.
 * Username, password and e-mail sent to
 * remote server. Stylesheet: bgreen.css.
 */
public class RegisterScreen extends Application {

    public static Stage stage;

    @FXML
    public TextField emailField;

    @FXML
    public TextField username;

    @FXML
    public PasswordField passwordText;

    @FXML
    public PasswordField pwCheck;

    @FXML
    public Label wrongInput;

    @Override
    public void start(final Stage primaryStage) {
        stage = primaryStage;
        setContent(primaryStage);
        primaryStage.show();
    }

    @FXML
    private void backAction() {
        Stage loginStage = new Stage();
        LoginScreen login = new LoginScreen();
        login.start(loginStage);
        loginStage.show();
        stage.close();
    }

    @FXML
    private void pwCheckKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            register();
        }
    }

    @FXML
    private void registerAction() {
        register();
    }

    private void setContent(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/registerscreen.fxml"));
            LoginScreen.setRootProperties(primaryStage, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * registers new User and checks if all info is correct.
     */
    public void register() {
        boolean login = true;
        if (checkEmpty(emailField) || !isValidEmailAddress(emailField.getText())) {
            login = false;
            emailField.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            wrongInput.setText("not a valid e-mail address");
        } else {
            emailField.setStyle("-fx-border-color: null;");
        }
        if (checkEmpty(username)) {
            login = false;
            username.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
        } else {
            username.setStyle("-fx-border-color: null;");
        }

        if (checkEmpty(passwordText)) {
            login = false;
            passwordText.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
        }
        if (checkEmpty(pwCheck)) {
            login = false;
            pwCheck.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
        }

        if (checkPasswords() && login) {
            passwordText.setStyle("-fx-border-color: null;");
            pwCheck.setStyle("-fx-border-color: null;");
            User user = new User(username.getText(), passwordText.getText(), emailField.getText());
            boolean exists = (new AuthenticationService()).authenticate(user, "api/auth/register");
            if (exists) {
                LoginScreen.login(stage, username, passwordText, wrongInput);
            } else {
                wrongInput.setText("Username already exists");
            }
        }
    }

    /**
     * checks if the passwords are the same.
     * @return boolean for checking passwords.
     */
    public boolean checkPasswords() {
        if (passwordText.getText().equals(pwCheck.getText())) {
            return true;
        }
        wrongInput.setText("Passwords are not the same");
        return false;
    }

    /**
     *  checks if the textfield is empty.
     * @param text the textfield to check
     * @return true when textfield is empty, false when not empty
     */
    public static boolean checkEmpty(TextField text) {
        if (text.getText() == null || text.getText().isEmpty()) {
            return true;
        }
        return false;
    }


    /**
     * Checks if entered email is a valid email address.
     *
     * @param email Entered email address
     * @return True if correct email False if incorrect email.
     */
    public static boolean isValidEmailAddress(String email) {

        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            result = false;
        }

        return result;
    }
}

