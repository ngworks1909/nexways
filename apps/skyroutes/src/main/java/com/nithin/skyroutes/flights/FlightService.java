package com.nithin.skyroutes.flights;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public List<Flight> searchFlights(String origin, String destination, LocalDateTime startOfDay, int noOfTravellers) {
        return flightRepository.searchFlights(origin, destination, startOfDay, noOfTravellers);
    }
}
