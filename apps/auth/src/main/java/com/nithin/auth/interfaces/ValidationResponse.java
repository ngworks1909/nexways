package com.nithin.auth.interfaces;

public class ValidationResponse{
    public boolean success;
    public String message;

    public ValidationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
