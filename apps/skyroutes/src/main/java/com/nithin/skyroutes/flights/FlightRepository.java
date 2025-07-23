package com.nithin.skyroutes.flights;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, String> {
    @Query("SELECT f FROM Flight f WHERE " +
           "LOWER(f.origin.cityName) = LOWER(:origin) AND " +
           "LOWER(f.destination.cityName) = LOWER(:destination) AND " +
           "FUNCTION('DATE', f.flightTime) = FUNCTION('DATE', :day) AND " +
           "f.seats >= :noOfTravellers")
    List<Flight> searchFlights(
        @Param("origin") String origin,
        @Param("destination") String destination,
        @Param("day") LocalDateTime day,
        @Param("noOfTravellers") int noOfTravellers
    );
}