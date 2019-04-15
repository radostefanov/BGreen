package com.bgreen.app;

import com.bgreen.app.enums.ActivityCategory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ActivityCategoryTest {

    @Test
    public void EnumTest() {
        assertNotNull(ActivityCategory.Home);
        assertNotNull(ActivityCategory.Insulation);
        assertNotNull(ActivityCategory.SolarPanels);
        assertNotNull(ActivityCategory.Meal);
        assertNotNull(ActivityCategory.Transport);
    }
}
