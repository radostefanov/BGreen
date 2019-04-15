package com.bgreen.app;

import com.bgreen.app.models.User;
import com.bgreen.app.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImageTest extends AbstractTest {

    private User demoUser1;
    private User demoUser2;

    private String relativePath = "./src/test/resources/profile.png";

    @Autowired
    private UserRepository userRepository;

    @Override
    @Before
    public void setUp() {
        demoUser1 = new User();
        demoUser1.setUsername("resuname");
        demoUser1.setPassword(passwordEncoder.encode("drowssap"));

        demoUser2 = new User();
        demoUser2.setUsername("user2");
        demoUser2.setPassword(passwordEncoder.encode("password2"));

        super.setUp();
    }

    @Test
    @Transactional
    public void _clearDatabase(){

        userRepository.truncate();
    }


    @Test
    public void uploadFileTest() throws Exception {
        userRepository.save(demoUser1);
        File file = new File(relativePath);
        String absolutePath = file.getAbsolutePath();

        String token = AbstractTest.AuthToken = this.getLoginToken("resuname", "drowssap");

        InputStream inputStream = new FileInputStream(absolutePath);

        MockMultipartFile firstFile = new MockMultipartFile("file", "profile.png", "image/png", inputStream);

        mvc.perform(MockMvcRequestBuilders.multipart("/images")
                .file(firstFile)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().is(200))
                .andExpect(content().string("File is uploaded successfully"));
    }

    @Test
    public void uploadFileTest2() throws Exception {
        userRepository.save(demoUser2);
        File file = new File("./src/main/resources/static/3.png");
        String absolutePath;
        if(file.exists()) absolutePath = file.getAbsolutePath();
        else absolutePath = (new File(relativePath)).getAbsolutePath();

        String token = AbstractTest.AuthToken = this.getLoginToken("user2", "password2");

        InputStream inputStream = new FileInputStream(absolutePath);

        MockMultipartFile firstFile = new MockMultipartFile("file", "3.png", "image/png", inputStream);

        mvc.perform(MockMvcRequestBuilders.multipart("/images")
                .file(firstFile)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().is(200))
                .andExpect(content().string("File is uploaded successfully"));

    }

    @After
    public void stop() {
        userRepository.deleteAll();
    }
}
