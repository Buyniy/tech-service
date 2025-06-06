package ru.tarasov.techservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.tarasov.techservice.dto.*;
import ru.tarasov.techservice.exceptions.BookingException;
import ru.tarasov.techservice.service.BookingService;

import java.net.URI;
import java.util.List;

@Slf4j
@RequestMapping("/api/v1/bookings")
@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequest) {
        log.info("POST request. Booking: {}", bookingRequest);
        URI uri = URI.create("api/v1/bookings?id=" + bookingService.createBooking(bookingRequest));
        return ResponseEntity.created(uri)
                .build();
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@Positive @PathVariable Long id) {
        log.info("PUT request. Cancel booking with id: {}", id);
        try {
            return new ResponseEntity<>(bookingService.cancelBooking(id), HttpStatus.OK);
        } catch (BookingException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }

    @PutMapping("/time/{id}")
    public ResponseEntity<?> updateBookingTime(@Valid @RequestBody BookingRequestDTO bookingRequest,
                                               @Positive @PathVariable Long id) {
        log.info("PUT request. Edit time booking with id: {}", id);
        try {
            return new ResponseEntity<>(bookingService.updateBookingTime(bookingRequest, id), HttpStatus.OK);
        } catch (BookingException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }

    @GetMapping(path = "/", produces = "application/json")
    public List<BookingResponseDTO> getAllBookings() {
        log.info("GET request. All bookings.");
        return bookingService.getAllBookings();
    }

    @GetMapping(path = "/provided", produces = "application/json")
    public List<FavorResponseDTO> getProvidedFavors() {
        log.info("GET request. All provided favors.");
        return bookingService.getProvidedFavors();
    }

    @GetMapping(path = "/by-time", produces = "application/json")
    public List<BookingResponseDTO> getAllBookingsByTimeBetween(@Valid @RequestBody TimeIntervalDTO timeInterval) {
        log.info("GET request. All bookings by time interval.");
        return bookingService.getAllBookingsByTimeBetween(timeInterval.startTime(), timeInterval.endTime());
    }

    @GetMapping(path = "/revenue", produces = "application/json")
    public List<RevenueReportDTO> getRevenueReport(@Valid @RequestBody TimeIntervalDTO timeInterval) {
        log.info("GET request. Revenue by time interval.");
        return bookingService.getRevenueReport(timeInterval.startTime(), timeInterval.endTime());
    }
}
