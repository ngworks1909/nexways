package com.nithin.nexbook.interfaces;

public class CreateBookingReponse {
    public boolean success;
    public String message;
    public CreateBookingReponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
