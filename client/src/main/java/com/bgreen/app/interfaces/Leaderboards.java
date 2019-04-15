package com.bgreen.app.interfaces;

import com.bgreen.app.models.User;
import com.bgreen.app.services.LeaderboardService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Leaderboards implements Initializable {

    private static String token1 = "";

    private static List<User> globalLeaders;

    private static List<User> friendLeaders;

    @FXML
    private Button refresh;

    @FXML
    private TableView globalTable;

    @FXML
    private TableView friendsTable;

    @FXML
    private BorderPane bp;

    @FXML
    private TableColumn<User, String> globalUsername;

    @FXML
    private TableColumn<User, Integer> globalPoints;

    @FXML
    private TableColumn<User, String> friendsUsername;

    @FXML
    private TableColumn<User, Integer> friendsPoints;

    /**
     * Initializes the page.
     * @param url url.
     * @param rb resourcebundle.
     */
    public void initialize(URL url, ResourceBundle rb) {

        LeaderboardService leaderboardService = new LeaderboardService();

        globalLeaders = Arrays.asList(
                leaderboardService.getTop5Users());
        friendLeaders = Arrays.asList(
                leaderboardService.getTop5Friends());

        showLeaderboards();

        globalTable.setItems(FXCollections.observableArrayList(globalLeaders));
        friendsTable.setItems(FXCollections.observableArrayList(friendLeaders));

        refresh.setOnAction(e -> {
            globalLeaders = Arrays.asList(
                    leaderboardService.getTop5Users());
            friendLeaders = Arrays.asList(
                    leaderboardService.getTop5Friends());
            globalTable.setItems(FXCollections.observableArrayList(globalLeaders));
            friendsTable.setItems(FXCollections.observableArrayList(friendLeaders));
        });
    }

    /**
     * Creates a table with the username and points.
     */
    public void showLeaderboards() {

        globalUsername = new TableColumn("Username");
        globalPoints = new TableColumn("Points");

        friendsUsername = new TableColumn("Username");
        friendsPoints = new TableColumn("Points");

        globalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        friendsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        globalTable.getStyleClass().add("leaderboards-table");
        friendsTable.getStyleClass().add("leaderboards-table");

        globalUsername.getStyleClass().add("leaderboards-column");
        globalPoints.getStyleClass().add("leaderboards-column");
        friendsUsername.getStyleClass().add("leaderboards-column");
        friendsPoints.getStyleClass().add("leaderboards-column");

        globalTable.getColumns().add(globalUsername);
        globalTable.getColumns().add(globalPoints);

        friendsTable.getColumns().add(friendsUsername);
        friendsTable.getColumns().add(friendsPoints);

        globalTable.setPlaceholder(new Label("There are no users with points currently!"));
        friendsTable.setPlaceholder(new Label("Add friends to see their points!"));

        fillTable();
    }

    /**
     * fills the table with userdata.
     */
    public void fillTable() {
        globalUsername.setCellValueFactory(
                new PropertyValueFactory<User, String>("Username"));

        globalPoints.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("points"));

        friendsUsername.setCellValueFactory(
                new PropertyValueFactory<User, String>("Username"));

        friendsPoints.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("points"));

        globalTable.setItems(FXCollections.observableArrayList(globalLeaders));
        friendsTable.setItems(FXCollections.observableArrayList(friendLeaders));
    }

    /**
     * get leaderboards content.
     * @return leaderboards content
     */
    public Parent getLeaderboards() {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/views/leaderboards.fxml"));

            root.getStylesheets().add("/css/root.css");
            root.getStylesheets().add("/css/leaderboards.css");

            return root;
        } catch (IOException e) {

            System.out.println(e.getMessage());
            System.out.println("Last point of execution");
            return null;
        }

    }
}
