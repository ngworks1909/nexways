package com.nithin.skyroutes.interfaces;

public class FlightSearchResponse {
    public boolean success;
    public String message;

    public FlightSearchResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
