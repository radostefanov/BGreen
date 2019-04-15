//package com.bgreen.app.interfaces;
//
//import static com.bgreen.app.interfaces.RegisterScreen.checkEmpty;
//import static com.bgreen.app.interfaces.RegisterScreen.isValidEmailAddress;
//import de.saxsys.javafx.test.JfxRunner;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//
//@RunWith(JfxRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class RegisterScreenTest {
//    private static RegisterScreen registerScreen;
//
//    @Before
//    public void init() {
//        registerScreen = new RegisterScreen();
//        registerScreen.emailField = new TextField();
//        registerScreen.username = new TextField();
//        registerScreen.passwordText = new PasswordField();
//        registerScreen.pwCheck = new PasswordField();
//        registerScreen.wrongInput = new Label();
//    }
//
//    @Test
//    public void a_registerEmptyFields() {
//        registerScreen.register();
//
//        assertEquals("-fx-border-color: red; -fx-border-radius: 5px;", registerScreen.emailField.getStyle());
//        assertEquals("not a valid e-mail address", registerScreen.wrongInput.getText());
//        assertEquals("-fx-border-color: red; -fx-border-radius: 5px;", registerScreen.username.getStyle());
//        assertEquals("-fx-border-color: red; -fx-border-radius: 5px;", registerScreen.passwordText.getStyle());
//        assertEquals("-fx-border-color: red; -fx-border-radius: 5px;", registerScreen.pwCheck.getStyle());
//    }
//
//    @Test
//    public void b_registerWrongMail() {
//        registerScreen.emailField.setText("notAnEmailAddress");
//        registerScreen.pwCheck = new PasswordField();
//        registerScreen.pwCheck.setText("not empty");
//        registerScreen.register();
//
//        assertEquals("", registerScreen.pwCheck.getStyle());
//        assertEquals("-fx-border-color: red; -fx-border-radius: 5px;", registerScreen.emailField.getStyle());
//    }
//
//    @Test
//    public void c_registerNonEmptyField() {
//        registerScreen.emailField.setText("test@mail.com");
//        registerScreen.username.setText("username");
//        registerScreen.passwordText.setText("password");
//        registerScreen.pwCheck.setText("");
//
//        registerScreen.register();
//
//        assertEquals("-fx-border-color: null;", registerScreen.emailField.getStyle());
//        assertEquals("-fx-border-color: null;", registerScreen.username.getStyle());
//    }
//
//    @Test
//    public void registerLoginTrueCheckPasswordsFalse() {
//
//    }
//
//    @Test
//    public void d_checkPasswordsTrue() {
//        registerScreen.passwordText.setText("test");
//        registerScreen.pwCheck.setText("test");
//
//        assertTrue(registerScreen.checkPasswords());
//    }
//
//    @Test
//    public void e_checkPasswordsFalse() {
//        registerScreen.passwordText.setText("else");
//
//        assertFalse(registerScreen.checkPasswords());
//    }
//
//    @Test
//    public void i_testEmailValidationFalse() {
//        assertFalse(isValidEmailAddress("testing"));
//        assertFalse(isValidEmailAddress("testing@"));
//        assertFalse(isValidEmailAddress("testing*&^"));
//    }
//
//    @Test
//    public void i_testEmailValidationTrue() {
//        assertTrue(isValidEmailAddress("testing@test.com"));
//        assertTrue(isValidEmailAddress("testing23$@testing.nl"));
//        assertTrue(isValidEmailAddress("testing*&^@test.ru"));
//    }
//
//    @Test
//    public void j_checkEmptyTrueNull() {
//        assertTrue(checkEmpty(new TextField(null)));
//        assertTrue(checkEmpty(new TextField()));
//    }
//
//    @Test
//    public void k_checkEmptyFalse() {
//        assertFalse(checkEmpty(new TextField("not empty")));
//    }
//}
