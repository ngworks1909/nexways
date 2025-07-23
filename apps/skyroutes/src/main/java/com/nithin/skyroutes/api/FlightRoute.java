package com.nithin.skyroutes.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nithin.skyroutes.flights.Flight;
import com.nithin.skyroutes.flights.FlightService;
import com.nithin.skyroutes.interfaces.FlightSearchResponse;
import com.nithin.skyroutes.validators.FlightSearchValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/flights")
public class FlightRoute {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightSearchValidator flightSearchValidator;

    @GetMapping("/data")
    private ResponseEntity<?> searchFlight(@RequestParam String source, @RequestParam String destination, @RequestParam String day, @RequestParam int travellers){
        
        FlightSearchResponse response = flightSearchValidator.validate(source, destination, day, travellers);
        if(!response.success){
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", response.message
                ));
        }
        List<Flight> flights = flightService.searchFlights(source, destination, LocalDateTime.parse(day), travellers);

        return ResponseEntity.status(200).body(Map.of(
                "flights", flights
        ));
    }
    
}
