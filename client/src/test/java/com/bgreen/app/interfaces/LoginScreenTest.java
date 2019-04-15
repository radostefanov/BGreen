//package com.bgreen.app.interfaces;
//
//import de.saxsys.javafx.test.JfxRunner;
//import javafx.event.EventType;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertFalse;
//import static junit.framework.TestCase.assertTrue;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//
//@RunWith(JfxRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class LoginScreenTest {
//    private LoginScreen login = new LoginScreen();
//
//    @Before
//    public void init() {
//        login.passwordText = new PasswordField();
//        login.reg = new Label();
//        login.userText = new TextField();
//    }
//
//    @Test
//    public void pwCheckKeyPressed() {
//        EventType<KeyEvent> type = new EventType<>();
//        KeyEvent event = new KeyEvent(null, null, type, "", "", KeyCode.ENTER ,false, false, false, false );
//
//        assertTrue(login.pwCheckKeyPressed(event));
//
//        event = new KeyEvent(null, null, type, "", "", KeyCode.A ,false, false, false, false );
//
//        assertFalse(login.pwCheckKeyPressed(event));
//    }
//
//    @Test
//    public void login() {
//    assertTrue(login.login());
//    }
//
//    @Test
//    public void a_loginUserText() {
//        login.userText.setText("notempty");
//        login.login();
//        assertEquals("-fx-border-color: null", login.userText.getStyle());
//    }
//
//    @Test
//    public void b_loginPasswordText() {
//        login.passwordText.setText("notempty");
//        login.login();
//        assertEquals("-fx-border-color: red; -fx-border-radius: 3px;", login.passwordText.getStyle());
//    }
//
//    @Test
//    public void loginNotEmpty() {
//        login.userText.setText("not empty");
//        login.passwordText.setText("not empty");
//        assertTrue(login.login());
//    }
//}