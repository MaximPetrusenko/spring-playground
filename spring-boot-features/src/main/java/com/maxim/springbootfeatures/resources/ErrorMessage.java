package com.maxim.springbootfeatures.resources;

public class ErrorMessage {
    private final String error_message;

    public String getError_message() {
        return error_message;
    }
    public ErrorMessage(String message) {
        this.error_message = message;
    }
}
