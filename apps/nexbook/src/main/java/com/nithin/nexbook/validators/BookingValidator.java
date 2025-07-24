package com.nithin.nexbook.validators;

import org.springframework.stereotype.Service;

import com.nithin.nexbook.interfaces.CreateBookingReponse;
import com.nithin.nexbook.interfaces.CreateBookingRequest;

@Service
public class BookingValidator {
    private boolean isValidFlightId(String flightId) {
        if(flightId == null || flightId.isEmpty()) return false;
        return flightId.trim().length() > 3;
    }

    private boolean isValidTravellers(int travellers) {
        return travellers > 0;
    }

    public CreateBookingReponse isValidCreateBookingRequest(CreateBookingRequest request) {
        if(!isValidFlightId(request.flightId)) {
            return new CreateBookingReponse(false, "Invalid flightId");
        }
        if(!isValidTravellers(request.travellers)) {
            return new CreateBookingReponse(false, "Invalid travellers");
        }
        return new CreateBookingReponse(true, "Valid");
    }
}
