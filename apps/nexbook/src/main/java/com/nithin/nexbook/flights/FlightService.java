package com.nithin.nexbook.flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;

    public Flight getFlight(String flightId){
        return flightRepository.findById(flightId).orElse(null);
    }
}
