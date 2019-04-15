package com.bgreen.app.interfaces;

import com.bgreen.app.auth.AuthUser;
import com.bgreen.app.enums.ActivityCategory;
import com.bgreen.app.models.Activity;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.requestmodels.UserActivityRequest;
import com.bgreen.app.services.ActivityService;
import com.bgreen.app.services.StatisticsService;
import com.bgreen.app.services.UserService;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class DashBoard implements Initializable {
    public static ArrayList<UserActivity> userActivities = new ArrayList<>();
    public static Activity currActivity;

    @Autowired
    public static ActivityService activityService = new ActivityService();

    private static ArrayList<Integer> points = new ArrayList<>();

    @Autowired
    private static UserService userService = new UserService();

    public UserActivityRequest request;

    @FXML
    public GridPane grid;

    @FXML
    public ComboBox<Activity> activitiesList;

    @FXML
    public Slider veganSlider;

    @FXML
    public GridPane mealContainer;

    @FXML
    public GridPane travelContainer;

    @FXML
    public GridPane houseContainer;

    @FXML
    public GridPane houseContainerSolar;

    @FXML
    public GridPane houseContainerInsulated;

    @FXML
    public GridPane produceContainer;

    @FXML
    public TextField enerSaved;

    @FXML
    public TextField localProduce;

    @FXML
    public TextField insulated;

    @FXML
    public Label wrongInput;

    @FXML
    public TableView activitiesTable;

    @FXML
    public TableColumn<UserActivity, String> nameColumn;

    @FXML
    public TableColumn<UserActivity, String> pointsColumn;

    @FXML
    public TextField travelDistance;

    @FXML
    public TextField temperatureBefore;

    @FXML
    public TextField temperatureAfter;

    public Activity selectedActivity;



    @FXML
    private PieChart activityStatistics;

    @FXML
    private Label sliderValue;

    @FXML
    private Label totalSaved;

    @FXML
    private Label dailyGoalLabel;

    @FXML
    private Label weeklyGoalLabel;

    @FXML
    private Label monthlyGoalLabel;

    private Stage primaryStage;

    private Calendar currentCal = Calendar.getInstance();

    private SimpleDateFormat dateFormat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateGoals();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            currentCal = Calendar.getInstance();
            currentCal.setTime(dateFormat.parse(dateFormat.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AuthUser.setPoints(userService.getUserInfo().getPoints());

        // get user activities from server
        updateUserActivities();

        // updates the total points label
        updateTotalPoints(totalSaved);

        // fills the piechart with userdata
        this.fillPieChart();

        // add activities to combobox
        insertStaticActivity(activitiesList);
        activitiesList.setPrefWidth(430);

        setActivitiesListConverter(activitiesList);

        addVeganListener(veganSlider, sliderValue);

        nameColumn = new TableColumn<>("Activity");
        pointsColumn = new TableColumn<>("Points");

        nameColumn.prefWidthProperty().bind(activitiesTable.widthProperty().divide(2));
        pointsColumn.prefWidthProperty().bind(activitiesTable.widthProperty()
                .divide(2).subtract(20));

        activitiesTable.getColumns().addAll(nameColumn, pointsColumn);

        activitiesTable.setEditable(true);
        fillTable();

    }

    @FXML
    private void activitiesTableOnAction(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {

            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            VBox dialogVBox = new VBox(20);
            dialogVBox.setStyle("-fx-background-color: #c8e6c9;");
            dialogVBox.setPadding(new Insets(10));

            UserActivity userActiv = (UserActivity) activitiesTable
                    .getSelectionModel().getSelectedItem();

            dialogVBox.getChildren().add(new Text("Activity: "
                    + userActiv.getActivity().getName()));

            switch (userActiv.getActivity().getCategory()) {
                case Home:
                    dialogVBox.getChildren().add(new Text("Temperature Before: "
                            + userActiv.getDegreesBefore()));
                    dialogVBox.getChildren().add(new Text("Temperature After: "
                            + userActiv.getDegreesAfter()));
                    break;
                case Transport:
                    dialogVBox.getChildren().add(new Text("Kilometers passed: "
                            + userActiv.getDistance()));
                    break;
                case Meal:
                    dialogVBox.getChildren().add(new Text("Vegan coefficient: "
                            + userActiv.getVeganCoefficient()));
                    break;
                case Insulation:
                    dialogVBox.getChildren().add(new Text("Area: "
                            + userActiv.getAreaInsulated()));
                    break;
                case SolarPanels:
                    dialogVBox.getChildren().add(new Text("Energy saved: "
                            + userActiv.getEnergySavedSolar()));
                    break;
                case LocalProduction:
                    dialogVBox.getChildren().add(new Text("Kilograms of produce bought: "
                            + userActiv.getLocalProduce()));
                    break;
                default:
                    break;
            }

            Button deleteButton = new Button("Delete activity");
            deleteButton.setOnAction(e -> {

                activityService.deleteActivity(userActiv.getId());
                dialog.hide();

                updateUserActivities();

                fillPieChart();

                fillTable();
            });

            dialogVBox.getChildren().add(
                    deleteButton
            );

            Scene dialogScene = new Scene(dialogVBox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }

    /**
     * initializes the combobox to display activities available for logging.
     */
    @FXML
    public void activitiesListOnAction() {
        wrongInput.setVisible(false);
        selectedActivity = activitiesList.getSelectionModel().getSelectedItem();

        displaySubquestions();
    }

    /**
     * actions for addactivity button.
     *
     * @return UserActivityRequest
     */
    @FXML
    public UserActivityRequest addActivityOnAction() throws FileNotFoundException {
        request = new UserActivityRequest();
        request.setActivityId(Long.valueOf(selectedActivity.getId()));
        ActivityCategory category = selectedActivity.getCategory();

        if (checkCorrectInput(category)) {

            switch (category) {
                case Meal:
                    request.setVeganCoefficient((int) veganSlider.getValue());
                    break;

                case Transport:
                    request.setDistance(Integer.parseInt(travelDistance.getText()));
                    break;

                case Home:
                    request.setDegreesBefore(Integer.parseInt(this.temperatureBefore.getText()));
                    request.setDegreesAfter(Integer.parseInt(this.temperatureAfter.getText()));
                    break;

                case Insulation:
                    request.setAreaInsulated(Integer.parseInt(this.insulated.getText()));
                    break;

                case SolarPanels:
                    request.setEnergySavedSolar(Integer.parseInt(this.enerSaved.getText()));
                    break;

                case LocalProduction:
                    request.setMassBought(Integer.parseInt(this.localProduce.getText()));
                    break;
                default:
                    break;
            }

            activityService.linkActivityToUser(request);

            this.updateUserActivities();
            this.fillPieChart();
            this.fillTable();
            this.updateTotalPoints(totalSaved);
            this.updateGoals();

            if (selectedActivity.getCategory().equals(ActivityCategory.Meal)
                    && request.getVeganCoefficient() > 80) {
                veganmealOnAction();
            }
        }
        return request;
    }

    @FXML
    private void veganmealOnAction() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        VBox dialogVBox = new VBox(7);
        dialogVBox.setStyle("-fx-background-color: #c8e6c9;");
        dialogVBox.setPadding(new Insets(10));

        Text save = new Text("You just saved me!");

        ImageView imageView = new ImageView();

        Image image;
        Text didYouKnow = new Text("Did you know?");
        didYouKnow.setStyle("-fx-translate-x: 105");
        Text funFact;

        double random = Math.random() * 10;

        if (random < 3 && random > 2) {
            image = new Image("/gifs/woopwoop2.gif");
            imageView.setImage(image);
            funFact = new Text("50.000.000.000 chickens are killed each year :(");
            funFact.setStyle("-fx-translate-x: 2");
        } else if (random < 4 && random > 3) {
            image = new Image("/gifs/piggie.gif");
            imageView.setImage(image);
            funFact = new Text("We have an excellent sense of smell!");
            funFact.setStyle("-fx-translate-x: 30");
        } else if (random < 5 && random > 4) {
            image = new Image("/gifs/cow1.gif");
            funFact = new Text("We weigh around 800 kg on average!");
            funFact.setStyle("-fx-translate-x: 30");
            imageView.setImage(image);
        } else if (random < 6 && random > 5) {
            image = new Image("/gifs/sheep.gif");
            funFact = new Text("Adult female sheep are called 'ewes'.");
            funFact.setStyle("-fx-translate-x: 35");
            imageView.setImage(image);
        } else {
            return;
        }


        save.setStyle("-fx-font-weight: BOLD; -fx-translate-x: 91");
        imageView.setStyle("-fx-translate-x: 55");

        dialogVBox.getChildren().add(save);
        dialogVBox.getChildren().add(didYouKnow);
        dialogVBox.getChildren().add(funFact);
        dialogVBox.getChildren().add(imageView);

        //create the popup and place it
        Scene dialogScene = new Scene(dialogVBox, 324, 230);
        dialog.setScene(dialogScene);
        dialog.setX(grid.getWidth() + 310);
        dialog.setY(grid.getHeight() - 135);
        dialog.show();

        //click anywhere in the popup to close it.
        dialog.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.isPrimaryButtonDown()) {
                dialog.hide();
                event.consume();
            }
        });
    }

    /**
     * sets a converter for the activitieslist combobox.
     *
     * @param activitiesList combobox
     */
    public static void setActivitiesListConverter(ComboBox<Activity> activitiesList) {
        activitiesList.setConverter(setStringConverter());
    }

    /**
     * creates a new StringConverter.
     *
     * @return new StringConverter
     */
    public static StringConverter<Activity> setStringConverter() {
        return new StringConverter<Activity>() {
            @Override
            public String toString(Activity user) {
                if (user == null) {
                    return null;
                } else {
                    currActivity = user;
                    return user.getName();
                }
            }

            @Override
            public Activity fromString(String userId) {
                return null;
            }
        };
    }

    /**
     * gives the Label the correct text corresponding to the position of the slider.
     *
     * @param newValue    the value the slider is on
     * @param sliderValue the label
     */
    public static void setSliderText(Number newValue, Label sliderValue) {
        double val = Math.ceil(newValue.doubleValue() / 25);
        int switchValue = (int) val;
        sliderValue.setText("new: " + val);

        switch (switchValue) {
            case 0:
                sliderValue.setText("Large amount of meat");
                break;
            case 1:
                sliderValue.setText("Large amount of meat");
                break;
            case 2:
                sliderValue.setText("Normal amount of meat");
                break;
            case 3:
                sliderValue.setText("Small amount of meat");
                break;
            case 4:
                sliderValue.setText("Vegan");
                break;
            default:
                sliderValue.setText("Small amount of meat");
        }
    }

    /**
     * adds an eventlistener to the veganslider.
     *
     * @param veganSlider the slider
     * @param sliderValue the value of the slider
     */
    public static void addVeganListener(Slider veganSlider, Label sliderValue) {
        veganSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setSliderText(newValue, sliderValue);
        });
    }

    /**
     * input validation.
     *
     * @param category the category selected in the comboBox
     * @return true if input is correct
     */
    public boolean checkCorrectInput(ActivityCategory category) {
        int tempBefore;
        int tempAfter;

        switch (category) {
            case Home:
                tempBefore = checkIfNumber(this.temperatureBefore.getText());
                tempAfter = checkIfNumber(this.temperatureAfter.getText());
                if (tempBefore == -345 || tempAfter == -345) {
                    return false;
                }

                if (tempBefore > 40 || tempBefore < 0 || tempAfter > 40 || tempAfter < 0) {
                    setWrongInputLabel("Choose values between 0 and 40 degrees");
                    return false;
                }
                break;

            default:
                break;
        }

        return checkCorrectInput2(category);
    }

    /**
     * input validation.
     *
     * @param category the category selected in the comboBox
     * @return true if input is correct
     */
    public boolean checkCorrectInput2(ActivityCategory category) {

        switch (category) {

            case SolarPanels:
                int solarEnergySaved = checkIfNumber(this.enerSaved.getText());

                if (solarEnergySaved == -345) {
                    return false;
                }

                if (solarEnergySaved <= 0) {
                    setWrongInputLabel("Choose a value > 0");
                    return false;
                }
                break;

            case Insulation:
                int areaInsulated = checkIfNumber(insulated.getText());
                if (areaInsulated == -345) {
                    return false;
                }

                if (areaInsulated <= 0) {
                    setWrongInputLabel("Choose a value > 0");
                    return false;
                }
                break;

            case Meal:
                return true;


            default:
                break;
        }

        return checkCorrectInput3(category);
    }

    /**
     * input validation.
     *
     * @param category the category selected in the comboBox
     * @return true if input is correct
     */
    public boolean checkCorrectInput3(ActivityCategory category) {

        switch (category) {


            case Transport:
                int travDistance = checkIfNumber(travelDistance.getText());
                if (travDistance == -345) {
                    return false;
                }

                if (travDistance <= 0) {
                    setWrongInputLabel("Choose a value > 0");
                    return false;
                }
                break;


            case LocalProduction:
                int produce = checkIfNumber(localProduce.getText());
                if (produce == -345) {
                    return false;
                }

                if (produce <= 0) {
                    setWrongInputLabel("Choose a value > 0");
                    return false;
                }
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * sets the text for the wrongInput label and shows it.
     *
     * @param text the text to display
     */
    public void setWrongInputLabel(String text) {
        wrongInput.setVisible(true);
        wrongInput.setText(text);
    }

    /**
     * checks if a string is a round number.
     *
     * @param text string to check
     * @return the number contained in the string, or -345 if it is not a number
     */
    public int checkIfNumber(String text) {
        if (text.contains(",") || text.contains(".")) {
            setWrongInputLabel("Please use (round) numbers");
            return -345;
        }

        try {
            int res = (int) Math.round(Double.parseDouble(text));
            return res;
        } catch (NumberFormatException e) {
            setWrongInputLabel("Please use (round) numbers");
            return -345;
        }
    }


    /**
     * fill the activities table.
     */
    public void fillTable() {
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getActivity().getName()));

        pointsColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                Double.toString(cellData.getValue().getPoints())));

        activitiesTable.setItems(FXCollections.observableArrayList(userActivities));
    }

    /**
     * display's correct gridpane for selected activity in combobox.
     */
    public void displaySubquestions() {

        mealContainer.setVisible(false);
        travelContainer.setVisible(false);
        houseContainer.setVisible(false);
        houseContainerInsulated.setVisible(false);
        houseContainerSolar.setVisible(false);
        produceContainer.setVisible(false);

        System.out.println("name: " + currActivity.getName());
        System.out.println("category: " + currActivity.getCategory());

        switch (currActivity.getCategory()) {
            case Meal:
                mealContainer.setVisible(true);
                break;
            case Transport:
                travelContainer.setVisible(true);
                break;
            case Home:
                houseContainer.setVisible(true);
                break;
            case Insulation:
                houseContainerInsulated.setVisible(true);
                break;
            case SolarPanels:
                houseContainerSolar.setVisible(true);
                break;
            case LocalProduction:
                produceContainer.setVisible(true);
                break;
            default:
                break;
        }
    }

    /**
     * get dashboard content.
     *
     * @return dashboard content
     */
    public Parent getDash() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/dashboard.fxml"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        root = addStyleSheets(root);
        return root;
    }

    /**
     * adds stylesheets to a node.
     *
     * @param root node to add stylesheets to
     * @return node with stylesheets
     */
    public Parent addStyleSheets(Parent root) {
        root.getStylesheets().add("/css/root.css");
        root.getStylesheets().add("/css/dashboard.css");
        root.getStylesheets().add("/css/activities.css");

        return root;
    }


    /**
     * Creates a piechart from the links between user and activity.
     */
    public void fillPieChart() {
        activityStatistics.getData().clear();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();

        HashMap<String, Double> upoints = new HashMap<>();

        for (UserActivity userActivity : userActivities) {

            Activity activ = userActivity.getActivity();

            if (upoints.containsKey(activ.getName()) || upoints.get(activ.getName()) != null) {
                double points = userActivity.getPoints() + upoints.get(activ.getName());
                upoints.replace(activ.getName(), points);
            } else {
                upoints.put(activ.getName(), userActivity.getPoints());
            }
        }

        for (Map.Entry<String, Double> e : upoints.entrySet()) {
            pieChartData.add(new PieChart.Data(e.getKey(), e.getValue()));
        }

        //        pie = new PieChart();
        activityStatistics.setData(pieChartData);
        activityStatistics.setTitle("Points per activity");
        activityStatistics.setLegendVisible(false);
        activityStatistics.setPrefHeight(275);

        for (PieChart.Data slice : pieChartData) {
            slice.getNode().getStyleClass().add(setColor(slice.getName()));
        }
    }

    private void updateGoals() {
        HashMap<String, Double> dailyMap = new HashMap<>();
        HashMap<Integer, Double> weeklyMap = new HashMap<>();
        HashMap<String, Double> monthlyMap = new HashMap<>();

        try {
            new StatisticsService().getDailyPoints(dailyMap,
                    new ActivityService().getUserActivities(), currentCal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double dailyPoints = 0;
        for (double d : dailyMap.values()) {
            dailyPoints += d;
        }

        int dailyTodo = (int) (userService.getUserInfo().getDailyGoal() - dailyPoints);
        if (dailyTodo < 0) {
            dailyGoalLabel.setText("You did it!");
        } else {
            dailyGoalLabel.setText("Remaining points: " + dailyTodo);
        }

        try {
            new StatisticsService().getWeeklyPoints(weeklyMap,
                    new ActivityService().getUserActivities(), currentCal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double weeklyPoints = 0;
        for (double d : weeklyMap.values()) {
            weeklyPoints += d;
        }

        int weeklyTodo = (int) (userService.getUserInfo().getWeeklyGoal() - weeklyPoints);
        if (weeklyTodo < 0) {
            weeklyGoalLabel.setText("You did it!");
        } else {
            weeklyGoalLabel.setText("Remaining points: " + weeklyTodo);
        }

        try {
            new StatisticsService().getMonthlyPoints(monthlyMap,
                    new ActivityService().getUserActivities(), currentCal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double monthlyPoints = 0;
        for (double d : monthlyMap.values()) {
            monthlyPoints += d;
        }

        int monthlyTodo = (int) (userService.getUserInfo().getMonthlyGoal() - monthlyPoints);
        if (monthlyTodo < 0) {
            monthlyGoalLabel.setText("You did it!");
        } else {
            monthlyGoalLabel.setText("Remaining points: " + monthlyTodo);
        }
    }

    /**
     * updates the UserActivities ArrayList.
     */
    public void updateUserActivities() {
        points.clear();
        userActivities.clear();
        UserActivity[] activities = activityService.getUserActivities();

        for (UserActivity activity : activities) {

            userActivities.add(activity);
        }
    }

    /**
     * updates label showing total points.
     *
     * @param labelOne Label to update
     */
    public void updateTotalPoints(Label labelOne) {
        double userPoints = AuthUser.getPoints();
        userPoints = ((double) ((int) (userPoints * 100.0))) / 100.0;
        String saved = "You have saved " + ((int)(userPoints/ 10)) + " kilograms of CO2!";
        labelOne.setText(saved);
    }

    /**
     * adds all activities to the combobox.
     *
     * @param box combobox activities are added to
     */
    public void insertStaticActivity(ComboBox box) {
        box.getItems().clear();
        box.getItems().addAll(activityService.getAll());
    }

    /**
     * adds correct style to PieChart data.
     *
     * @param name name of color
     * @return stylename
     */
    public static String setColor(String name) {
        switch (name) {
            case "Travel with bike":
                return "color0";

            case "Eat vegetarian / vegan meal":
                return "color1";

            case "Insulated house":
                return "color2";

            case "Lower temperature at home":
                return "color3";

            case "Installed solar panels":
                return "color4";

            case "Travel with public transport":
                return "color5";

            case "Bought local produce":
                return "color6";

            default:
                return null;
        }
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
}


