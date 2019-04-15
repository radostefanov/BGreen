package com.bgreen.app;

import com.bgreen.app.models.HttpResponse;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class HttpResponseTest {

    HttpResponse res = new HttpResponse("200", "Hello");

    @Test
    public void testConstructor() {
        assertNotNull(res);
    }

    @Test
    public void testGetStatus() {
        assertEquals(res.getStatus(), "200");
    }

    @Test
    public void testGetMessage() {
        assertEquals(res.getMessage(), "Hello");
    }
}
