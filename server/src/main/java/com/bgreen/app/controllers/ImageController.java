package com.bgreen.app.controllers;

import com.bgreen.app.models.User;
import com.bgreen.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class ImageController {

    private static final String relativePath = "./src/main/resources/static/";

    /**
     * Creates userRepository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     *  Something.
     * @param file the file the user wishes to use.
     * @param authentication self explanatory.
     * @return confirmation of the action.
     */
    @RequestMapping(value = "/images", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,
                                             Authentication authentication)
        throws IOException {
        User loggedInUser = (User) authentication.getCredentials();

        File writePath = new File(relativePath);
        String absolutePath = writePath.getAbsolutePath();
        File convertFile;
        if (file.getOriginalFilename().equals("profile.png")) {
            convertFile = new File(absolutePath + "/profile.png");
        } else {
            convertFile = new File(absolutePath + "/" + loggedInUser.getId() + ".png");
        }

        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();

        userRepository.findById(loggedInUser.getId()).map(user -> {
            user.setProfile_picture(loggedInUser.getId() + ".png");
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        });
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }
}
