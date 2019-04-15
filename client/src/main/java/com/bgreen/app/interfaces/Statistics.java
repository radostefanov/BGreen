package com.bgreen.app.interfaces;

import animatefx.animation.FadeIn;

import com.bgreen.app.models.User;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.services.ActivityService;
import com.bgreen.app.services.StatisticsService;
import com.bgreen.app.services.UserService;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.skins.SlimSkin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Statistics implements Initializable {

    private static double dailyPoints;

    private static double weeklyPoints;

    private static double monthlyPoints;

    private DateFormat dateFormat;

    private StatisticsService statisticsService = new StatisticsService();

    private UserService userService = new UserService();

    private UserActivity[] userActivities = new ActivityService().getUserActivities();

    private Calendar currentCal = Calendar.getInstance();

    @FXML
    private ComboBox<String> dateRange;

    @FXML
    private BarChart dailyChart;

    @FXML
    private BarChart weeklyChart;

    @FXML
    private BarChart monthlyChart;

    @FXML
    private Gauge gaugeDaily;

    @FXML
    private Gauge gaugeWeekly;

    @FXML
    private Gauge gaugeMonthly;

    @FXML
    private Button dailyGoalButton;

    @FXML
    private Button weeklyGoalButton;

    @FXML
    private Button monthlyGoalButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            currentCal = Calendar.getInstance();
            currentCal.setTime(dateFormat.parse(dateFormat.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        HashMap<String, Double> monthlyMap = new HashMap<>();
        HashMap<Integer, Double> weeklyMap = new HashMap<>();
        HashMap<String, Double> dailyMap = new HashMap<>();

        try {
            statisticsService.getDailyPoints(dailyMap, userActivities, currentCal);
            statisticsService.getWeeklyPoints(weeklyMap, userActivities, currentCal);
            statisticsService.getMonthlyPoints(monthlyMap, userActivities, currentCal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        createBarChartDaily(dailyMap);
        createBarChartWeekly(weeklyMap);
        createBarChartMonthly(monthlyMap);

        dateRange.getItems().clear();
        dateRange.getItems().addAll("Daily", "Weekly", "Monthly");
        dateRange.getSelectionModel().select(0);

        dateRange.setOnAction(e -> {
            dailyChart.setVisible(false);
            weeklyChart.setVisible(false);
            monthlyChart.setVisible(false);

            switch (dateRange.getSelectionModel().getSelectedItem()) {
                case "Daily":
                    dailyChart.setVisible(true);
                    new FadeIn(dailyChart).play();
                    break;
                case "Weekly":
                    weeklyChart.setVisible(true);
                    new FadeIn(weeklyChart).play();
                    break;
                case "Monthly":
                    monthlyChart.setVisible(true);
                    new FadeIn(monthlyChart).play();
                    break;
                default:
                    break;
            }
        });

        setGaugeData(dailyMap, weeklyMap, monthlyMap);

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.getIcons().add(new Image("/icons/tree.png"));
        VBox dialogVbox = new VBox(20);
        dialogVbox.setStyle("-fx-background-color: #c8e6c9;");
        dialogVbox.setPadding(new Insets(10));
        Scene dialogScene = new Scene(dialogVbox, 260, 180);
        dialogScene.getStylesheets().addAll("/css/root.css", "/css/settings.css");
        dialog.setScene(dialogScene);

        double finalDailyPoints = dailyPoints;
        dailyGoalButton.setOnAction(e -> {
            dialogVbox.getChildren().clear();
            Label dailyGoal = new Label("Change your daily goal: ");
            dailyGoal.setAlignment(Pos.CENTER);
            dailyGoal.getStyleClass().add("label-settings");
            TextField newGoal = new TextField();
            newGoal.getStyleClass().add("text-field");
            Button confirm = new Button("Add goal");
            confirm.getStyleClass().add("button");
            confirm.setOnAction(e1 -> {
                double goal = Double.parseDouble(newGoal.getText());
                User tempUser = (new UserService()).getUserInfo();
                User newUser = new User(tempUser.getUsername(),
                        tempUser.getEmail(), tempUser.getPoints(), goal,
                        tempUser.getWeeklyGoal(), tempUser.getMonthlyGoal());
                userService.modifyUserInfo(newUser);
                gaugeDaily.setValue((finalDailyPoints / goal) * 100);
                dialog.close();
            });
            dialogVbox.getChildren().addAll(dailyGoal, newGoal, confirm);
            dialog.show();
        });

        double finalWeeklyPoints = weeklyPoints;
        weeklyGoalButton.setOnAction(e -> {
            dialogVbox.getChildren().clear();
            Label weeklyGoal = new Label("Change your weekly goal: ");
            weeklyGoal.setAlignment(Pos.CENTER);
            weeklyGoal.getStyleClass().add("label-settings");
            TextField newGoal = new TextField();
            newGoal.getStyleClass().add("text-field");
            Button confirm = new Button("Add goal");
            confirm.getStyleClass().add("button");
            confirm.setOnAction(e1 -> {
                double goal = Double.parseDouble(newGoal.getText());
                User tempUser = (new UserService()).getUserInfo();
                User newUser = new User(tempUser.getUsername(),
                        tempUser.getEmail(), tempUser.getPoints(), tempUser.getDailyGoal(),
                        goal, tempUser.getMonthlyGoal());
                userService.modifyUserInfo(newUser);
                gaugeWeekly.setValue((finalWeeklyPoints / goal) * 100);
                dialog.close();
            });
            dialogVbox.getChildren().addAll(weeklyGoal, newGoal, confirm);
            dialog.show();
        });

        double finalMonthlyPoints = monthlyPoints;
        monthlyGoalButton.setOnAction(e -> {
            dialogVbox.getChildren().clear();
            Label monthlyGoal = new Label("Change your monthly goal: ");
            monthlyGoal.setAlignment(Pos.CENTER);
            monthlyGoal.getStyleClass().add("label-settings");
            TextField newGoal = new TextField();
            newGoal.getStyleClass().add("text-field");
            Button confirm = new Button("Add goal");
            confirm.getStyleClass().add("button");
            confirm.setOnAction(e1 -> {
                double goal = Double.parseDouble(newGoal.getText());
                User tempUser = (new UserService()).getUserInfo();
                User newUser = new User(tempUser.getUsername(),
                        tempUser.getEmail(), tempUser.getPoints(), tempUser.getDailyGoal(),
                        tempUser.getWeeklyGoal(), goal);
                userService.modifyUserInfo(newUser);
                gaugeMonthly.setValue((finalMonthlyPoints / goal) * 100);
                dialog.close();
            });
            dialogVbox.getChildren().addAll(monthlyGoal, newGoal, confirm);
            dialog.show();
        });
    }

    private void setGaugeData(HashMap<String, Double> dailyMap, HashMap<Integer, Double> weeklyMap,
                              HashMap<String, Double> monthlyMap) {

        dailyPoints = 0;
        for (double d : dailyMap.values()) {
            dailyPoints += d;
        }

        weeklyPoints = 0;
        for (double d : weeklyMap.values()) {
            weeklyPoints += d;
        }

        monthlyPoints = 0;
        for (double d : monthlyMap.values()) {
            monthlyPoints += d;
        }

        User oldUser = userService.getUserInfo();
        gaugeDaily.setSkin(new SlimSkin(gaugeDaily));
        if (oldUser.getDailyGoal() == 0) {
            gaugeDaily.setValue(0);
        } else {
            gaugeDaily.setValue((dailyPoints / oldUser.getDailyGoal()) * 100);
        }

        gaugeWeekly.setSkin(new SlimSkin(gaugeWeekly));
        if (oldUser.getWeeklyGoal() == 0) {
            gaugeWeekly.setValue(0);
        } else {
            gaugeWeekly.setValue((weeklyPoints / oldUser.getWeeklyGoal()) * 100);
        }

        gaugeMonthly.setSkin(new SlimSkin(gaugeMonthly));
        if (oldUser.getMonthlyGoal() == 0) {
            gaugeMonthly.setValue(0);
        } else {
            gaugeMonthly.setValue((monthlyPoints / oldUser.getMonthlyGoal()) * 100);
        }
    }

    private void createBarChartDaily(HashMap pointData) {
        dailyChart.getData().add(populate("Daily Points", pointData));
        dailyChart.setEffect(new DropShadow(10, Color.rgb(220, 220, 220)));
    }

    private void createBarChartWeekly(HashMap pointData) {
        weeklyChart.getData().add(populate("Weekly Points", pointData));
        weeklyChart.setEffect(new DropShadow(10, Color.rgb(220, 220, 220)));
    }

    private void createBarChartMonthly(HashMap pointData) {
        monthlyChart.getData().add(populate("Monthly Points", pointData));
        monthlyChart.setEffect(new DropShadow(10, Color.rgb(220, 220, 220)));
    }

    private static XYChart.Series<String, Number> populate(String timeRange, HashMap pointData) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        switch (timeRange) {
            case "Daily Points":
                String[] dailyList = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                String[] charList = {"m", "t", "w", " t", "f", "s", " s"};
                int charListCount = 0;
                for (String i : dailyList) {
                    series.getData().add(new XYChart.Data<>(charList[charListCount],
                            (Number) pointData.get(i)));
                    charListCount++;
                }
                return series;
            case "Weekly Points":
                String[] weekList = {"1", "2", "3", "4", "5"};
                for (String i : weekList) {
                    series.getData().add(new XYChart.Data<>(i,
                            (Number) pointData.get(Integer.parseInt(i))));
                }
                return series;
            case "Monthly Points":
                String[] monthList = {"j", "f", "m", "a", " m", " j",
                    "  j", " a", "s", "o", "n", "d"};
                for (String i : monthList) {
                    series.getData().add(new XYChart.Data<>(i, (Number) pointData.get(i)));
                }
                return series;
            default:
                break;
        }
        return null;
    }

    /**
     * gets statTab content to display in UI.
     *
     * @return content
     */
    public Parent getStatistics() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/statistics.fxml"));

            root.getStylesheets().add("/css/root.css");
            root.getStylesheets().add("/css/statistics.css");

            return root;
        } catch (IOException e) {
            return null;
        }
    }
}
