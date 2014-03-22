package com.endava.domain;

/**
 * Ionuț Păduraru
 */
public class ErrorResponse {

    public ErrorResponse(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

}