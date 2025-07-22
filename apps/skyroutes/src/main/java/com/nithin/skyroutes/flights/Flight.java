package com.nithin.skyroutes.flights;

import java.time.LocalDateTime;
import java.util.UUID;



import jakarta.persistence.*;
import com.nithin.skyroutes.city.City;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    private String flightId = UUID.randomUUID().toString().replace("-", "");

    @Column(nullable = false)
    private String flightName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origin", referencedColumnName = "cityId")
    private City origin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination", referencedColumnName = "cityId")
    private City destination;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private LocalDateTime flightTime;

    @Column(nullable = false)
    private int seats;

    public Flight() {}

    public Flight(String flightName, City origin, City destination, float price, LocalDateTime flightTime, int seats) {
        this.flightName = flightName;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.flightTime = flightTime;
        this.seats = seats;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getFlightName() {
        return flightName;
    }

    public City getOrigin() {
        return origin;
    }

    public City getDestination() {
        return destination;
    }

    public float getPrice() {
        return price;
    }

    public LocalDateTime getFlightTime() {
        return flightTime;
    }

    public int getSeats() {
        return seats;
    }
}
