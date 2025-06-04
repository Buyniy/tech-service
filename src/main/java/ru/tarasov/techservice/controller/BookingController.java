package ru.tarasov.techservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import ru.tarasov.techservice.dto.BookingRequestDTO;
import ru.tarasov.techservice.dto.BookingResponseDTO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequestMapping("/api/v1/bookings")
@RestController
public class BookingController {

    @PostMapping("/")
    public void createBooking(@Valid @RequestBody BookingRequestDTO bookingRequest) {
    }

    @PutMapping("/cancel/{id}")
    public void cancelBooking(@Positive @PathVariable Long id) {
    }

    @PutMapping("/{id}/{time}")
    public void updateBookingTime(@Positive @PathVariable Long id,
                                  @PathVariable LocalDateTime bookingTime) {
    }

    @GetMapping("/all")
    public List<BookingResponseDTO> getAllBookings() {
        return Collections.emptyList();
    }

    @GetMapping("/provided")
    public List<BookingResponseDTO> getProvidedServices() {
        return Collections.emptyList();
    }
}
