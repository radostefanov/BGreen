package com.bgreen.app.models;

/**
 * Basic HTTP response class.
 * Used for testing client and server connection.
 *
 */
public class BasicHttpResponse {

    private String status;

    private String message;

    public BasicHttpResponse(){

    }

    public BasicHttpResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Gets the status.
     * @return Returns the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status sets the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the message.
     * @return returns the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * @param message sets the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
