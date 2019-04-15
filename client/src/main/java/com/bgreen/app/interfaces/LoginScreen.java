package com.bgreen.app.interfaces;

import com.bgreen.app.models.User;
import com.bgreen.app.services.AuthenticationService;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;

public class LoginScreen extends Application {

    private static Stage stage;

    private static AuthenticationService authenticationService = new AuthenticationService();

    @FXML
    public TextField userText;

    @FXML
    public PasswordField passwordText;

    @FXML
    public Label reg;

    /**
     * starts the loginscreen.
     * @param primaryStage stage to be set
     */
    @Override
    public void start(final Stage primaryStage) {
        stage = primaryStage;
        setContent(primaryStage);
        primaryStage.show();
    }

    /**
     * check if key pressed is enter.
     * @param event event
     * @return true if enter was pressed
     */
    @FXML
    public boolean pwCheckKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login(stage, userText, passwordText, reg);
            return true;
        }
        return false;
    }

    private void setContent(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            setRootProperties(primaryStage, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets properties for the root.
     * @param primaryStage stage
     * @param root root of the scene
     * @return the stage
     */
    public static Stage setRootProperties(Stage primaryStage, Parent root) {
        primaryStage.setTitle("BGreen");
        primaryStage.getIcons().add(new Image("/icons/tree.png"));
        primaryStage.centerOnScreen();

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().addAll("/css/root.css", "/css/loginscreen.css");
        primaryStage.setScene(scene);
        return primaryStage;
    }

    @FXML
    private RegisterScreen registerAction() {
        Stage registerStage = new Stage();
        registerStage.setTitle("BGreen");
        RegisterScreen regScreen = new RegisterScreen();
        regScreen.start(registerStage);
        registerStage.show();
        stage.close();
        return regScreen;
    }

    @FXML
    public boolean login() {
        login(stage, userText, passwordText, reg);
        return true;
    }

    /**
     * Takes care of user login process.
     *
     * @param primaryStage stage of the scene
     * @param userText     username
     * @param passwordText password
     * @param reg          label to display info
     */
    public static boolean login(Stage primaryStage, TextField userText,
                             PasswordField passwordText, Label reg) {
        boolean empty = false;
        boolean exists = false;
        if (RegisterScreen.checkEmpty(userText)) {
            userText.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            empty = true;
            System.out.println("empty one");
        } else {
            userText.setStyle("-fx-border-color: null");
        }

        if (RegisterScreen.checkEmpty(passwordText)) {
            passwordText.setStyle("-fx-border-color: red; -fx-border-radius: 3px;");
            empty = true;
            System.out.println("empty two");
        } else {
            passwordText.setStyle("-fx-border-color: null");
            User user = new User(userText.getText(), passwordText.getText());
            if (!empty) {
                System.out.println("empty = false");
                try {
                    exists = authenticationService.authenticate(user, "/api/auth/login");
                } catch (ResourceAccessException exception) {
                    exists = false;
                }
            }

            if (exists) {
                showDash(primaryStage);
            } else {
                passwordText.setStyle("-fx-border-color: red; -fx-border-radius: 3px;");
                userText.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
                reg.setText("User not found");
            }
        }
        return exists;
    }

    /**
     * Refractor result-method shows dashboard.
     *
     * @param primaryStage main primaryStage to attach layouts to
     */
    public static void showDash(Stage primaryStage) {
        Stage dashStage = new Stage();
        DashBoardTabbed dash = new DashBoardTabbed();
        dash.start(dashStage);
        dashStage.show();
        primaryStage.close();
    }

    /**
     * Replaces main, to boot from Spring.
     */
    public static void execute(String[] args) {
        launch(args);
    }
}

