package com.bgreen.app.interfaces;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DashBoardTabbed extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BGreen");
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(680);
        primaryStage.centerOnScreen();
        primaryStage.getIcons().add(new Image("/icons/tree.png"));

        TabPane tabpane = new TabPane();
        tabpane.getStyleClass().add("tabpane");
        tabpane.setSide(Side.LEFT);
        tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        double tabWidth = 175;
        double tabHeight = 85;
        tabpane.setMinSize(tabHeight, tabWidth);
        tabpane.setMaxSize(tabHeight, tabWidth);
        tabpane.setTabMinWidth(tabHeight);
        tabpane.setTabMaxWidth(tabHeight);
        tabpane.setTabMinHeight(tabWidth);
        tabpane.setTabMaxHeight(tabWidth);

        DashBoard dashBoard = new DashBoard();
        dashBoard.setPrimaryStage(primaryStage);
        SubScene dashboardSubscene = new SubScene(dashBoard.getDash(), 800, 650);
        Tab dash = new Tab();
        dash.setContent(dashboardSubscene);
        createTab(tabpane, dash, "Dashboard", "/icons/home.png", 31);


        Tab lbTab = new Tab();
        createTab(tabpane, lbTab, "Leaderboards", "/icons/trophy.png", 33);

        Tab achiev = new Tab();
        createTab(tabpane, achiev, "Achievements", "/icons/achievements.png",31);

        Tab statTab = new Tab();
        createTab(tabpane, statTab, "Statistics", "/icons/stats.png",33 );

        Friends friends = new Friends();
        Tab friendsTab = new Tab();
        createTab(tabpane, friendsTab, "Friends", "/icons/leaderboards.png", 35);

        Settings settings = new Settings();
        Tab settingTab = new Tab();
        createTab(tabpane, settingTab, "Settings", "/icons/settings.png", 35);

        Achievements achievements = new Achievements();
        Leaderboards leaderboards = new Leaderboards();
        Statistics statistics = new Statistics();

        tabpane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab)
            -> {
            if (lbTab.equals(newTab)) {
                SubScene leaderboardsSubScene = new SubScene(leaderboards.getLeaderboards(),
                        800, 650);
                lbTab.setContent(leaderboardsSubScene);
            } else if (achiev.equals(newTab)) {
                SubScene achievementsSubscene = new SubScene(achievements.getAchievements(),
                        800, 650);
                achiev.setContent(achievementsSubscene);
            } else if (statTab.equals(newTab)) {
                SubScene statisticsSubscene = new SubScene(statistics.getStatistics(), 800, 650);
                statTab.setContent(statisticsSubscene);
            } else if (friendsTab.equals(newTab)) {
                SubScene friendsSubScene = new SubScene(friends.getFriends(), 800, 650);
                friendsTab.setContent(friendsSubScene);
            } else if (settingTab.equals(newTab)) {
                SubScene settingsSubscene = new SubScene(settings.getSettings(), 800, 650);
                settingTab.setContent(settingsSubscene);
            } else if (dash.equals(newTab)) {
                SubScene dashboardSubsceneReload = new SubScene(dashBoard.getDash(), 800, 650);
                dash.setContent(dashboardSubsceneReload);
            }
        });

        Scene scene = new Scene(tabpane, 1000, 650);
        scene.getStylesheets().addAll("/css/root.css", "/css/dashboardtabbed.css");
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("AuthUser closed dashboard");
            primaryStage.close();
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Helper method to create image from image patch and add it to the tabs.
     * @param tabpane tabpane tab is added to
     * @param tab tab to add to tabpane
     * @param title title of tab
     * @param iconPath path where the icon is located
     * @param height off the image
     */
    private void createTab(TabPane tabpane, Tab tab, String title, String iconPath, double height) {
        ImageView image = new ImageView(new Image(iconPath));
        image.setFitHeight(height);
        image.setFitWidth(39);

        Label label = new Label(title);
        label.getStyleClass().add("tab-title");

        BorderPane tabPane = new BorderPane();
        tabPane.setCenter(image);
        tabPane.setBottom(label);

        tab.setGraphic(tabPane);
        tabpane.getTabs().add(tab);
    }
}
