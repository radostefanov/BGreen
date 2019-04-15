package com.bgreen.app;

import com.bgreen.app.configuration.SecurityConfig;
import com.bgreen.app.enums.HttpMethod;
import com.bgreen.app.models.Achievement;
import com.bgreen.app.models.User;
import com.bgreen.app.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public abstract class AbstractTest {


    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityConfig config;

    protected static String AuthToken;

    protected MockMvc mvc;


    @Autowired
    WebApplicationContext webApplicationContext;



    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

    }


    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }


    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    protected String getLoginToken(String username, String password) throws Exception{

        System.out.println("executing");

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        System.out.println("stopExecution");

        int status = mvcResult.getResponse().getStatus();

        System.out.println(status);
        System.out.println(mvcResult.getResponse().getContentAsString());
        if(status == 200) return mvcResult.getResponse().getHeader("Authorization");

        return null;

    }

    protected <T> T httpRequest(String url, HttpMethod httpMethod, Object requestParameters, Class<T> responseParameters, int expectedStatusCode) throws Exception {

        MvcResult mvcResult;

        System.out.println("Request from test: ");
        System.out.println( this.mapToJson(requestParameters));

        System.out.println("Auth token: " + AbstractTest.AuthToken);

        switch (httpMethod) {

            case GET:
                mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                        .header("Authorization", AbstractTest.AuthToken != null ? AbstractTest.AuthToken : "Empty")
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                break;
            case POST:
                mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                        .header("Authorization", AbstractTest.AuthToken != null ? AbstractTest.AuthToken : "Empty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(requestParameters))
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                break;
            case PUT:
                mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                        .header("Authorization", AbstractTest.AuthToken != null ? AbstractTest.AuthToken : "Empty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(requestParameters))
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                break;
            case PATCH:
                mvcResult = mvc.perform(MockMvcRequestBuilders.patch(url)
                        .header("Authorization", AbstractTest.AuthToken != null ? AbstractTest.AuthToken : "Empty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(requestParameters))
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                break;
            case DELETE:
                mvcResult = mvc.perform(MockMvcRequestBuilders.delete(url)
                        .header("Authorization", AbstractTest.AuthToken != null ? AbstractTest.AuthToken : "Empty")
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                break;
            default:
                mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                        .header("Authorization", AbstractTest.AuthToken != null ? AbstractTest.AuthToken : "Empty")
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                break;
        }


        System.out.println("Response from server: ");
        System.out.println(mvcResult.getResponse().getContentAsString());

        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatusCode, status);

        String content = mvcResult.getResponse().getContentAsString();

        System.out.println(content);

        if(content == null || responseParameters == null) return null;

        return this.mapFromJson(content, responseParameters);

    }
}
