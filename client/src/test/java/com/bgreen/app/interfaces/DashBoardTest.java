//package com.bgreen.app.interfaces;
//
//import com.bgreen.app.models.Activity;
//import com.bgreen.app.models.UserActivity;
//import com.bgreen.app.requestmodels.UserActivityRequest;
//import com.bgreen.app.services.ActivityService;
//
//import static com.bgreen.app.enums.ActivityCategory.*;
//import de.saxsys.javafx.test.JfxRunner;
//import javafx.scene.Parent;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.Slider;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.web.client.HttpServerErrorException;
//
//import java.util.ArrayList;
//
//@RunWith(JfxRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class DashBoardTest {
//    private DashBoard dash = new DashBoard();
//    private Activity activ = new Activity();
//
//    @Before
//    public void initialize() {
//        dash.currActivity = new Activity();
//        dash.userActivities = new ArrayList<>();
//        dash.activityService = new ActivityService();
//        dash.activitiesList = new ComboBox<>();
//        dash.mealContainer = new GridPane();
//        dash.travelContainer = new GridPane();
//        dash.houseContainer = new GridPane();
//        dash.houseContainerInsulated = new GridPane();
//        dash.houseContainerSolar = new GridPane();
//        dash.produceContainer = new GridPane();
//        dash.localProduce = new TextField();
//        dash.enerSaved = new TextField();
//        dash.insulated = new TextField();
//        dash.travelDistance = new TextField();
//        dash.temperatureBefore = new TextField();
//        dash.temperatureAfter = new TextField();
//        dash.activitiesTable = new TableView();
//        dash.nameColumn = new TableColumn<>();
//        dash.pointsColumn = new TableColumn<>();
//        dash.selectedActivity = new Activity();
//        dash.selectedActivity.setId(1);
//        dash.wrongInput = new Label();
//        dash.veganSlider = new Slider();
//    }
//
//
//    @Test
//    public void a_displaySubquestions() {
//        activ.setName("name");
//        dash.currActivity = activ;
//        dash.currActivity.setCategory(Meal);
//        dash.displaySubquestions();
//        assertEquals(true, dash.mealContainer.visibleProperty().getValue());
//
//        dash.currActivity.setCategory(Transport);
//        dash.displaySubquestions();
//        assertEquals(true, dash.travelContainer.visibleProperty().getValue());
//
//        dash.currActivity.setCategory(Home);
//        dash.displaySubquestions();
//        assertEquals(true, dash.houseContainer.visibleProperty().getValue());
//
//        dash.currActivity.setCategory(Insulation);
//        dash.displaySubquestions();
//        assertEquals(true, dash.houseContainerInsulated.visibleProperty().getValue());
//
//        dash.currActivity.setCategory(SolarPanels);
//        dash.displaySubquestions();
//        assertEquals(true, dash.houseContainerSolar.visibleProperty().getValue());
//
//        dash.currActivity.setCategory(Produce);
//        dash.displaySubquestions();
//        assertEquals(true, dash.produceContainer.visibleProperty().getValue());
//    }
//
//    @Test
//    public void b_activitiesListOnAction() {
//        dash.currActivity.setCategory(Meal);
//
//        Activity activ = new Activity();
//        activ.setName("name");
//        dash.activitiesList.getSelectionModel().select(activ);
//
//        dash.activitiesListOnAction();
//
//        assertFalse(dash.wrongInput.visibleProperty().getValue());
//        assertEquals("name", dash.selectedActivity.getName());
//    }
//
//    @Test
//    public void setActivitiesListConverter() {
//        dash.setActivitiesListConverter(dash.activitiesList);
//
//        assertEquals(activ.getName(), dash.activitiesList.getConverter().toString(activ));
//    }
//
//    @Test
//    public void testStringConverterNullUser() {
//        dash.setActivitiesListConverter(dash.activitiesList);
//        activ = null;
//
//        assertNull(dash.activitiesList.getConverter().toString(activ));
//    }
//
//    @Test
//    public void testStringConverterFromString() {
//        dash.activitiesList = new ComboBox<>();
//        dash.setActivitiesListConverter(dash.activitiesList);
//
//        assertNull(dash.activitiesList.getConverter().fromString("userId"));
//    }
//
//    @Test
//    public void setSliderTextCase0() {
//        Label slider = new Label();
//        dash.setSliderText(0, slider);
//
//        assertEquals("Large amount of meat", slider.getText());
//    }
//
//    @Test
//    public void setSliderTextCase1() {
//        Label slider = new Label();
//        dash.setSliderText(25, slider);
//
//        assertEquals("Large amount of meat", slider.getText());
//    }
//
//    @Test
//    public void setSliderTextCase2() {
//        Label slider = new Label();
//        dash.setSliderText(50, slider);
//
//        assertEquals("Normal amount of meat", slider.getText());
//    }
//
//    @Test
//    public void setSliderTextCase3() {
//        Label slider = new Label();
//        dash.setSliderText(75, slider);
//
//        assertEquals("Small amount of meat", slider.getText());
//    }
//
//    @Test
//    public void setSliderTextCase4() {
//        Label slider = new Label();
//        dash.setSliderText(100, slider);
//
//        assertEquals("Vegan", slider.getText());
//    }
//
//    @Test
//    public void setSliderTextCaseDefault() {
//        Label slider = new Label();
//        dash.setSliderText(1200, slider);
//
//        assertEquals("Small amount of meat", slider.getText());
//    }
//
//    @Test
//    public void addVeganListener() {
//        Slider slider = new Slider();
//        Label label = new Label();
//        dash.addVeganListener(slider, label);
//
//        slider.setValue(20);
//        assertEquals("Large amount of meat", label.getText());
//    }
//
//    @Test
//    public void checkCorrectInputMeal() {
//        assertTrue(dash.checkCorrectInput(Meal));
//    }
//
//    @Test
//    public void checkCorrectInputHomeNaNAfter() {
//        dash.wrongInput = new Label();
//        dash.temperatureAfter = new TextField("not a number");
//        dash.temperatureBefore = new TextField("2");
//
//        assertFalse(dash.checkCorrectInput(Home));
//        assertEquals("Please use (round) numbers", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputHomeNaNBefore() {
//        dash.wrongInput = new Label();
//        dash.temperatureAfter = new TextField("2");
//        dash.temperatureBefore = new TextField("not a number");
//
//        assertFalse(dash.checkCorrectInput(Home));
//        assertEquals("Please use (round) numbers", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputHomeBeforeWrong() {
//        dash.wrongInput = new Label();
//        dash.temperatureAfter = new TextField("10");
//        dash.temperatureBefore = new TextField("41");
//
//        assertFalse(dash.checkCorrectInput(Home));
//        assertEquals("Choose values between 0 and 40 degrees", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputHomeBeforeWrongTwo() {
//        dash.wrongInput = new Label();
//        dash.temperatureAfter = new TextField("10");
//        dash.temperatureBefore = new TextField("-1");
//
//        assertFalse(dash.checkCorrectInput(Home));
//        assertEquals("Choose values between 0 and 40 degrees", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputHomeAfterWrong() {
//        dash.wrongInput = new Label();
//        dash.temperatureAfter = new TextField("41");
//        dash.temperatureBefore = new TextField("10");
//
//        assertFalse(dash.checkCorrectInput(Home));
//        assertEquals("Choose values between 0 and 40 degrees", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputHomeAfterWrongTwo() {
//        dash.wrongInput = new Label();
//        dash.temperatureAfter = new TextField("10");
//        dash.temperatureBefore = new TextField("-1");
//
//        assertFalse(dash.checkCorrectInput(Home));
//        assertEquals("Choose values between 0 and 40 degrees", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputHomeAfterNegative() {
//        dash.wrongInput = new Label();
//        dash.temperatureAfter = new TextField("-1");
//        dash.temperatureBefore = new TextField("10");
//
//        assertFalse(dash.checkCorrectInput(Home));
//        assertEquals("Choose values between 0 and 40 degrees", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputHomeCorrect() {
//        dash.wrongInput = new Label();
//        dash.temperatureAfter = new TextField("10");
//        dash.temperatureBefore = new TextField("11");
//
//        assertTrue(dash.checkCorrectInput(Home));
//    }
//
//    @Test
//    public void checkCorrectInputSolarPanelsNaN() {
//        dash.wrongInput = new Label();
//        dash.enerSaved = new TextField("not a number");
//
//        assertFalse(dash.checkCorrectInput(SolarPanels));
//        assertEquals("Please use (round) numbers", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputSolarPanelsWrong() {
//        dash.wrongInput = new Label();
//        dash.enerSaved = new TextField("0");
//
//        assertFalse(dash.checkCorrectInput(SolarPanels));
//        assertEquals("Choose a value > 0", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputSolarPanelsWrongNegative() {
//        dash.wrongInput = new Label();
//        dash.enerSaved = new TextField("-1");
//
//        assertFalse(dash.checkCorrectInput(SolarPanels));
//        assertEquals("Choose a value > 0", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputSolarPanelsCorrect() {
//        dash.wrongInput = new Label();
//        dash.enerSaved = new TextField("50");
//
//        assertTrue(dash.checkCorrectInput(SolarPanels));
//    }
//
//    @Test
//    public void checkCorrectInputInsulationNaN() {
//        dash.wrongInput = new Label();
//        dash.insulated = new TextField("not a number");
//
//        assertFalse(dash.checkCorrectInput(Insulation));
//        assertEquals("Please use (round) numbers", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputInsulationWrong() {
//        dash.wrongInput = new Label();
//        dash.insulated = new TextField("0");
//
//        assertFalse(dash.checkCorrectInput(Insulation));
//        assertEquals("Choose a value > 0", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputInsulationWrongNegative() {
//        dash.wrongInput = new Label();
//        dash.insulated = new TextField("-1");
//
//        assertFalse(dash.checkCorrectInput(Insulation));
//        assertEquals("Choose a value > 0", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputInsulationCorrect() {
//        dash.wrongInput = new Label();
//        dash.insulated = new TextField("50");
//
//        assertTrue(dash.checkCorrectInput(Insulation));
//    }
//
//    @Test
//    public void checkCorrectInputTransportNaN() {
//        dash.wrongInput = new Label();
//        dash.travelDistance = new TextField("not a number");
//
//        assertFalse(dash.checkCorrectInput(Transport));
//        assertEquals("Please use (round) numbers", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputTransportWrong() {
//        dash.wrongInput = new Label();
//        dash.travelDistance = new TextField("0");
//
//        assertFalse(dash.checkCorrectInput(Transport));
//        assertEquals("Choose a value > 0", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputTransportWrongNegative() {
//        dash.wrongInput = new Label();
//        dash.travelDistance = new TextField("-1");
//
//        assertFalse(dash.checkCorrectInput(Transport));
//        assertEquals("Choose a value > 0", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputTransportCorrect() {
//        dash.wrongInput = new Label();
//        dash.travelDistance = new TextField("50");
//
//        assertTrue(dash.checkCorrectInput(Transport));
//    }
//
//    @Test
//    public void checkCorrectInputProduceNaN() {
//        dash.wrongInput = new Label();
//        dash.localProduce = new TextField("not a number");
//
//        assertFalse(dash.checkCorrectInput(Produce));
//        assertEquals("Please use (round) numbers", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputProduceWrong() {
//        dash.wrongInput = new Label();
//        dash.localProduce = new TextField("0");
//
//        assertFalse(dash.checkCorrectInput(Produce));
//        assertEquals("Choose a value > 0", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputProduceWrongNegative() {
//        dash.wrongInput = new Label();
//        dash.localProduce = new TextField("-1");
//
//        assertFalse(dash.checkCorrectInput(Produce));
//        assertEquals("Choose a value > 0", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkCorrectInputProduceCorrect() {
//        dash.wrongInput = new Label();
//        dash.localProduce = new TextField("50");
//
//        assertTrue(dash.checkCorrectInput(Produce));
//    }
//
//    @Test
//    public void setWrongInputLabel() {
//        dash.wrongInput = new Label();
//        dash.setWrongInputLabel("");
//        assertTrue(dash.wrongInput.visibleProperty().getValue());
//        assertEquals("", dash.wrongInput.getText());
//    }
//
//    @Test
//    public void checkIfNumber() {
//        String test = "10";
//        assertEquals(10, dash.checkIfNumber(test));
//    }
//
//    @Test
//    public void checkIfNumberNonRoundNumber() {
//        dash.wrongInput = new Label();
//
//        String test = "8,1";
//        assertEquals(-345, dash.checkIfNumber(test));
//
//        test = "8.1";
//        assertEquals(-345, dash.checkIfNumber(test));
//    }
//
//    @Test
//    public void checkIfNumberNonNumber() {
//        dash.wrongInput = new Label();
//
//        String test = "not a number";
//        assertEquals(-345, dash.checkIfNumber(test));
//    }
//
//    @Test
//    public void getDash() {
//        assertNull(dash.getDash());
//    }
//
//    @Test
//    public void addStyleSheets() {
//        Parent test = new GridPane();
//        test = dash.addStyleSheets(test);
//
//        assertTrue(test.getStylesheets().contains("/css/root.css"));
//        assertTrue(test.getStylesheets().contains("/css/dashboard.css"));
//        assertTrue(test.getStylesheets().contains("/css/activities.css"));
//    }
//
//    @Test
//    public void addActivityOnAction() throws HttpServerErrorException {
//        activ.setCategory(Transport);
//        activ.setId(1);
//        dash.selectedActivity = activ;
//        dash.travelDistance = new TextField("-10");
//
//        UserActivityRequest request = new UserActivityRequest();
//        request.setActivityId(Long.valueOf(activ.getId()));
//
//        assertEquals(request, dash.addActivityOnAction());
//        assertEquals(request.getActivityId(), dash.addActivityOnAction().getActivityId());
//    }
//
//    @Test
//    public void fillTable() {
//        dash.nameColumn = new TableColumn<>();
//        dash.pointsColumn = new TableColumn<>();
//        dash.activitiesTable = new TableView();
//        dash.userActivities = new ArrayList<>();
//        UserActivity activ = new UserActivity();
//        activ.setPoints(10);
//        dash.userActivities.add(activ);
//        dash.fillTable();
//
//        assertNotNull(dash.nameColumn.getCellValueFactory());
//        assertNotNull(dash.pointsColumn.getCellValueFactory());
//        assertNotNull(dash.activitiesTable.getItems());
//    }
//
//    @Test
//    public void testSetColor() {
//        assertEquals(DashBoard.setColor("Travel with bike"), "color0");
//        assertEquals(DashBoard.setColor("Eat vegetarian / vegan meal"), "color1");
//        assertEquals(DashBoard.setColor("Insulated house"), "color2");
//        assertEquals(DashBoard.setColor("Lower temperature at home"), "color3");
//        assertEquals(DashBoard.setColor("Installed solar panels"), "color4");
//        assertEquals(DashBoard.setColor("Travel with public transport"), "color5");
//        assertEquals(DashBoard.setColor("default"), null);
//    }
//}