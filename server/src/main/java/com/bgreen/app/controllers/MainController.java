package com.bgreen.app.controllers;

import com.bgreen.app.models.BasicHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * MainController.
 * Demo controller used to verify connection between client and server
 */
@RestController
public class MainController {

    /**
     * Listen for get requests in hello.
     * Sends basic hello back response
     * @return BasicHttpResponse
     */
    @GetMapping("/hello")
    public BasicHttpResponse respondToHello() {
        return new BasicHttpResponse("success", "Hi Back :)");
    }


    /**
     * Listens for post requests.
     * Sends basic hello back response
     * @return BasicHttpResponse
     */
    @PostMapping("/hello")
    public BasicHttpResponse respondToHelloFromPost() {
        return new BasicHttpResponse("success", "Hi Back, this time from a post request :)");
    }

}
