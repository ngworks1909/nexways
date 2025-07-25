package com.nithin.nexbook.bookings;

import java.time.LocalDateTime;
import java.util.UUID;
import com.nithin.nexbook.flights.Flight;
import com.nithin.nexbook.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @Column(name = "booking_id", nullable = false, unique = true)
    private String bookingId = UUID.randomUUID().toString().replace("-", "");

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id", referencedColumnName = "flightId")
    private Flight flight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Min(value = 1, message = "Travellers must be at least 1")
    private int travellers = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus = BookingStatus.Booked;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    public Booking() {}

    public Booking(Flight flight, User user, int travellers) {
        this.flight = flight;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.travellers = travellers;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Flight getFlight() {
        return flight;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setTravellers(int travellers){
        this.travellers = travellers;
    }

    public int getTravellers() {
        return travellers;
    }
}
