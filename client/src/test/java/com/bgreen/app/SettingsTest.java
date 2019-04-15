package com.bgreen.app;

import javafx.stage.FileChooser;
import org.junit.Test;
import java.io.File;

import static com.bgreen.app.interfaces.Settings.configureFileChooser;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class SettingsTest {

    @Test
    public void configureFileChooserTest() {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        assertNotNull(fileChooser);

        assertNull(fileChooser.getInitialFileName());

        assertEquals(fileChooser.getTitle(), "Choose profile picture");

        assertEquals(fileChooser.getInitialDirectory(), new File(System.getProperty("user.home")));

        assertNotNull(fileChooser.getExtensionFilters());

        assertEquals(3, fileChooser.getExtensionFilters().size());
    }
}
