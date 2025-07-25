package com.nithin.nexbook.bookings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public int countBookings(String flightId){
      return bookingRepository.countBookings(flightId);
    }

    public List<Booking> fetchBookings(String userId) {
        return bookingRepository.fetchBookings(userId);
    }
    
    
}
