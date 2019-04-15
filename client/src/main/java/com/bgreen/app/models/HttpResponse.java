package com.bgreen.app.models;

/**
 * models HttpResponse object as returned from server.
 */
public class HttpResponse {
    private String status;
    private String message;

    /**
     * Constructor for http response.
     * @param status in response header
     * @param message response message from server
     */
    public HttpResponse(final String status, final String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * gets status.
     * @return String status of HTTP response from server.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * gets message.
     * @return String message of the http response.
     */
    public String getMessage() {
        return this.message;
    }
}