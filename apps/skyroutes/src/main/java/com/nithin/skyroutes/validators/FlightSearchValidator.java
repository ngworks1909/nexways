package com.nithin.skyroutes.validators;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.nithin.skyroutes.interfaces.FlightSearchResponse;

@Service
public class FlightSearchValidator {

    public boolean isValidCity(String city) {
        if (city == null) return false;
        return city.trim().length() > 2;
    }

    public boolean isValidDate(String date) {
        try {
            LocalDateTime.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public FlightSearchResponse  validate(String origin, String destination, String day, int travellers) {
        if (origin == null || !isValidCity(origin)) {
            return new FlightSearchResponse(false, "Invalid source");
        }
        if(destination == null || !isValidCity(destination)){
            return new FlightSearchResponse(false, "Invalid destination");
        }
        if (!isValidDate(day)) {
            return new FlightSearchResponse(false, "Invalid date");
        }
        if (travellers < 1) {
            return new FlightSearchResponse(false, "Invalid travellers");
        }
        return new FlightSearchResponse(true, "Valid");
    }
}
