package com.bgreen.app;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

import com.bgreen.app.models.BasicHttpResponse;
import com.bgreen.app.models.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



/**
 * Unit test for simple App.
 */


@WithMockUser(username = "demoUser")
public class AppTest extends AbstractTest
{
    @Override
    @Before
    public void setUp() {

        super.setUp();
    }


    @Test
    public void a_getTest() throws Exception {


        String uri = "/hello";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        BasicHttpResponse httpResponse = this.mapFromJson(content, BasicHttpResponse.class);

        assertTrue(httpResponse.getStatus().equals("success"));

        assertTrue(httpResponse.getMessage().equals("Hi Back :)"));


    }


    @Test
    public void b_postTest() throws Exception {


        String uri = "/hello";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        BasicHttpResponse httpResponse = this.mapFromJson(content, BasicHttpResponse.class);

        assertTrue(httpResponse.getStatus().equals("success"));

        assertTrue(httpResponse.getMessage().equals("Hi Back, this time from a post request :)"));

    }

    @Test
    public void c_setIdTest(){

        User user = new User();
        user.setId(Long.valueOf(50));

        assertEquals(Long.valueOf(50), user.getId());
    }

    @Test
    public void d_testAppBoot() throws Exception {

        App.main(new String[] {} );
    }
}
