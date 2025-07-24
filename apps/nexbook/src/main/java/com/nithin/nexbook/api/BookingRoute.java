package com.nithin.nexbook.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nithin.nexbook.bookings.BookingService;
import com.nithin.nexbook.config.TokenParser;
import com.nithin.nexbook.flights.Flight;
import com.nithin.nexbook.flights.FlightService;
import com.nithin.nexbook.interfaces.CreateBookingReponse;
import com.nithin.nexbook.interfaces.CreateBookingRequest;
import com.nithin.nexbook.user.User;
import com.nithin.nexbook.user.UserService;
import com.nithin.nexbook.validators.BookingValidator;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/bookings")
public class BookingRoute {
    
    @Autowired
    private BookingService bookingService;

    @Autowired
    private TokenParser tokenParser;

    @Autowired
    private BookingValidator bookingValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private FlightService flightService;


    @PostMapping("/create")
    public ResponseEntity<?> createBooking(HttpServletRequest request, @RequestBody CreateBookingRequest body) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            // return "{\"success\": false, \"message\": \"Authorization header missing\"}";
            return ResponseEntity.status(400).body(Map.of(
                "success", false,
                "message", "Authorization header missing"
            ));
        }

        // Extract token (remove Bearer prefix if present)
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

        // Parse token to get claims
        Claims claims = tokenParser.getAllClaims(token);
        String userId = claims.get("userId", String.class);
        CreateBookingReponse  response = bookingValidator.isValidCreateBookingRequest(body);
        if(!response.success){
            return ResponseEntity.status(400).body(Map.of(
                "success", false,
                "message", response.message
            ));
        }

        User user = userService.getUserById(userId);
        if(user == null){
            return ResponseEntity.status(400).body(Map.of(
                "success", false,
                "message", "Something went wrong"
            ));
        }

        Flight flight = flightService.getFlight(body.flightId);
        if(flight == null){
            return ResponseEntity.status(400).body(Map.of(
                "success", false,
                "message", "Invalid flight"
            ));
        }

        //TODO: check if enough seats are there

        //TODO: create booking

        //TODO: return response;

        
        return null;
    }
    
}
