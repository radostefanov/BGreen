package com.bgreen.app.interfaces;

import com.bgreen.app.enums.FriendshipStatus;
import com.bgreen.app.models.User;
import com.bgreen.app.services.FriendService;

import com.bgreen.app.services.ImageService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Friends implements Initializable {

    /**
     * Creates a friendService for non GUI parts.
     */
    private static FriendService friendService = new FriendService();

    /**
     * List with all users.
     */
    private static List<User> userList;

    private ImageService imageService = new ImageService();

    @FXML
    private Label description;

    @FXML
    private TextField text;

    @FXML
    private Button confirmUsername;

    @FXML
    private TableView table;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableColumn<User, Integer> points;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userList = friendService.getFriends();
        text.getStyleClass().add("friends-textfield");
        description.getStyleClass().add("friends-description");

        showFriendsTable();

        confirmUsername.getStyleClass().add("add-button");

        confirmUsername.setOnAction(e -> {
            friendService.addFriend(text.getText());
            text.clear();
            fillTable();
        });
    }

    /**
     * Creates a table for the friends of the user.
     */
    public void showFriendsTable() {

        name = new TableColumn("Username");
        points = new TableColumn("Points");

        name.getStyleClass().add("friends-column");
        points.getStyleClass().add("friends-column");

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getStyleClass().add("friends-table");

        table.setPlaceholder(new Label("Add friends to view their progress!"));

        TableColumn actionCol = new TableColumn();

        actionCol.getStyleClass().add("friends-column");
        actionCol.setCellFactory(param ->  new TableCell<User, String>() {


            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty) {

                    User user = getTableView().getItems().get(getIndex());

                    if (user.getFriendshipStatus() == FriendshipStatus.ACCEPTED) {

                        final Button btn = new Button("Remove");

                        btn.getStyleClass().add("friends-delete");

                        btn.setOnAction(event -> {
                            setGraphic(null);

                            friendService.deleteFriend(user.getUsername());
                            fillTable();
                        });
                        setGraphic(btn);
                        setText(null);
                    } else if (user.getFriendshipStatus() == FriendshipStatus.HAS_TO_RESPOND) {

                        final Button accept = new Button("Accept");
                        final Button decline = new Button("Decline");
                        decline.getStyleClass().add("btn-decline");

                        final GridPane gPane = new GridPane();

                        gPane.add(accept,1, 0);
                        gPane.add(decline, 2, 0);
                        gPane.setHgap(5);

                        accept.setOnAction(event -> {
                            setGraphic(null);

                            friendService.acceptRequest(user.getUsername());
                            fillTable();
                        });

                        decline.setOnAction(event -> {
                            setGraphic(null);

                            friendService.deleteFriend(user.getUsername());
                            fillTable();
                        });

                        setGraphic(gPane);
                        setText(null);

                    } else if (user.getFriendshipStatus() == FriendshipStatus.WAITING_FOR_ANSWER) {

                        setText("Request sent");
                    }
                } else {

                    setGraphic(null);
                    setText(null);
                }



            }
        });


        TableColumn image = new TableColumn("");


        image.getStyleClass().add("friends-column");
        image.setCellFactory(param ->  new TableCell<User, String>() {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {

                    ImageView userProfile = new ImageView();
                    Image userProfileIcon = null;
                    try {
                        User user = getTableView().getItems().get(getIndex());
                        String path = user.getProfilePicture();
                        userProfileIcon = new Image(imageService.downloadImage(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    userProfile.setFitHeight(35);
                    userProfile.setFitWidth(35);
                    userProfile.setImage(userProfileIcon);
                    setGraphic(userProfile);
                }
            }
        });


        image.setPrefWidth(36);
        image.setMinWidth(36);
        image.setMaxWidth(36);

        name.setPrefWidth(120);
        name.setMinWidth(120);
        name.setMaxWidth(120);

        points.setPrefWidth(100);
        points.setMinWidth(100);
        points.setMaxWidth(100);

        image.getStyleClass().add("friends-image");

        table.getColumns().addAll(image);
        table.getColumns().add(name);
        table.getColumns().add(points);
        table.getColumns().addAll(actionCol);
        fillTable();
    }

    /**
     * Fills table with correct information.
     */
    public void fillTable() {
        userList = friendService.getFriends();
        name.setCellValueFactory(
                new PropertyValueFactory<User, String>("username"));

        points.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("points"));

        table.setItems(FXCollections.observableArrayList(userList));
    }

    /**
     * Add FXML to Friends interface.
     * @return Parent
     */
    public Parent getFriends() {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/views/friends.fxml"));

            root.getStylesheets().add("/css/root.css");
            root.getStylesheets().add("/css/settings.css");
            root.getStylesheets().add("/css/friends.css");



            return root;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

}
