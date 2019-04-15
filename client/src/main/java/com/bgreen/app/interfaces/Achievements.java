package com.bgreen.app.interfaces;

import com.bgreen.app.models.Achievement;
import com.bgreen.app.models.User;
import com.bgreen.app.requestmodels.UserAchievementRequest;
import com.bgreen.app.services.AchievementService;
import com.bgreen.app.services.UserService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Achievements implements Initializable {

    private static List<Achievement> allAchievementsList;
    private static List<Achievement> achievementsList;
    private static User currentUser;

    @FXML
    private TableView achievementsTable;

    @FXML
    private BorderPane bp;

    @FXML
    private TableColumn<Achievement, String> achievementName;

    @FXML
    private ImageView trophy1;

    @FXML
    private ImageView trophy2;

    @FXML
    private ImageView trophy3;

    /**
     * initializes achivements.
     * @param location url
     * @param resources resourcebundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AchievementService achievementService = new AchievementService();
        UserService userService = new UserService();

        currentUser = userService.getUserInfo();

        allAchievementsList = Arrays.asList(achievementService.getAchievements("api/achievements"));

        achievementService.deleteAchievements();

        for (int i = 0; i < allAchievementsList.size(); i++) {
            if (allAchievementsList.get(i).getRequirements() <= currentUser.getPoints()) {
                UserAchievementRequest request = new UserAchievementRequest();
                request.setAchievementId(allAchievementsList.get(i).getId());
                request.setAchievementName(allAchievementsList.get(i).getAchievementName());
                achievementService.linkAchievementsToUser(request);
            }
        }

        achievementsList = Arrays.asList(achievementService.getAchievements(
                "api/users/achievements"));

        showAchievements();

        Image trophyImage1;
        Image trophyImage2;
        Image trophyImage3;

        trophyImage1 = new Image("/icons/3bw.png");
        trophyImage2 = new Image("/icons/2bw.png");
        trophyImage3 = new Image("/icons/1bw.png");

        if (achievementsList.size() >= 2) {
            trophyImage1 = new Image("/icons/3.png");
        }

        if (achievementsList.size() >= 4) {
            trophyImage2 = new Image("/icons/2.png");
        }

        if (achievementsList.size() >= 6) {
            trophyImage3 = new Image("/icons/1.png");
        }


        trophy1.setImage(trophyImage1);
        trophy2.setImage(trophyImage2);
        trophy3.setImage(trophyImage3);


        achievementsTable.setItems(FXCollections.observableArrayList(achievementsList));

    }


    /**
     * shows content.
     */
    public void showAchievements() {

        achievementName = new TableColumn("Achievements unlocked:");

        achievementsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        achievementsTable.getColumns().add(achievementName);

        fillTable();
    }

    /**
     * fills table with achievements.
     */
    public void fillTable() {

        achievementName.setCellValueFactory(new PropertyValueFactory<Achievement, String>(
                "achievementName"));

        achievementsTable.setItems(FXCollections.observableArrayList(achievementsList));

    }

    /**
     * get achoevements content.
     * @return shows achievements content
     */
    public Parent getAchievements() {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/views/achievements.fxml"));

            root.getStylesheets().add("/css/root.css");
            //root.getStylesheets().add("/css/achievements.css");

            return root;
        } catch (IOException e) {

            System.out.println(e.getMessage());
            System.out.println("Last point of execution");

            return null;
        }
    }

}
