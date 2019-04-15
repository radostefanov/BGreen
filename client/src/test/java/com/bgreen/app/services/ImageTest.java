package com.bgreen.app.services;

import com.bgreen.app.App;
import com.bgreen.app.auth.AuthUser;
import javafx.scene.image.Image;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


@RunWith(MockitoJUnitRunner.class)
public class ImageTest {

    protected final String baseUrl = App.baseUrl;

    private String imageFile = "./src/main/resources/images/test.png";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ImageService imageService;

    @Test
    public void uploadTest() {
        ResponseEntity<String> toRespond = new ResponseEntity<>(
                "File is uploaded successfully", HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "images"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<String>> any(),
                Matchers.<Class<String>> any()
                )
        ).thenReturn(toRespond);

        ResponseEntity<String> response = imageService.uploadImage("/images/test.png");

        assertEquals("File is uploaded successfully", response.getBody());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void uploadTokenTest() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "randomGarbage");
        ResponseEntity<String> toRespond = new ResponseEntity<>(
                "File is uploaded successfully", httpHeaders, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "images"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<String>> any(),
                Matchers.<Class<String>> any()
                )
        ).thenReturn(toRespond);
        AuthUser.setToken("Bearer RandomGarbage");
        ResponseEntity<String> response = imageService.uploadImage("/images/test.png");

        assertEquals("File is uploaded successfully", response.getBody());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void downloadTest() throws IOException {
        byte[] bFile = Files.readAllBytes(new File(imageFile).toPath());
        Mockito.when(restTemplate.getForObject(
                Matchers.eq(baseUrl + "test.png"),
                Matchers.<Class<byte[]>> any()
                )
        ).thenReturn(bFile);

        InputStream in = imageService.downloadImage("test.png");

        assertEquals(FileInputStream.class, in.getClass());

        assertEquals(137, in.read());
    }

    @Test
    public void downloadUrlNotExistsTest() throws IOException {
        HttpClientErrorException errorNotFound = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        Mockito.when(restTemplate.getForObject(
                Matchers.eq(baseUrl + "testFail.png"),
                Matchers.<Class<byte[]>> any()
                )
        ).thenThrow(errorNotFound);

        InputStream in = imageService.downloadImage("testFail.png");

        assertEquals(FileInputStream.class, in.getClass());

        assertEquals(137, in.read());
    }

    @Test(expected = NullPointerException.class)
    public void downloadUrlNotAcceptableTest() throws IOException {
        HttpClientErrorException errorNotFound = new HttpClientErrorException(HttpStatus.NOT_ACCEPTABLE);
        Mockito.when(restTemplate.getForObject(
                Matchers.eq(baseUrl + "testFail.png"),
                Matchers.<Class<byte[]>> any()
                )
        ).thenThrow(errorNotFound);

        InputStream in = imageService.downloadImage("testFail.png");

        assertEquals(FileInputStream.class, in.getClass());

        assertEquals(137, in.read());
    }
}
