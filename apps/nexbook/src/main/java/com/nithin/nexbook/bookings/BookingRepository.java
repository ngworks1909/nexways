package com.nithin.nexbook.bookings;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query("SELECT COALESCE(SUM(b.travellers), 0) FROM Booking b WHERE b.flight.flightId = :flightId")
    int countBookings( @Param ("flightId") String flightId);

    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId")
    List<Booking> fetchBookings(@Param ("userId") String userId);
}
