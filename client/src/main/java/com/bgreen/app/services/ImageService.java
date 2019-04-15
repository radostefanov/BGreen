package com.bgreen.app.services;

import com.bgreen.app.auth.AuthUser;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageService extends UserService {

    private static final String relativePath = "./src/main/resources/images/";

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * uploads Image.
     * @param path directory of image
     */
    public ResponseEntity<String> uploadImage(String path) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource(path));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Authorization", AuthUser.getToken());

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "images",
                HttpMethod.POST, requestEntity, String.class);
        return response;
    }

    /**
     * downloads image.
     * @return inputStream
     */
    public InputStream downloadImage(String path) throws IOException {
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        byte[] byteArray = null;
        try {
            byteArray = restTemplate.getForObject(baseUrl + path, byte[].class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new FileInputStream(new File(relativePath + "profile.png"));
            }
        }
        FileOutputStream fos = new FileOutputStream(relativePath + "temp.png");
        fos.write(byteArray);
        return new FileInputStream(new File(relativePath + "temp.png"));
    }
}
