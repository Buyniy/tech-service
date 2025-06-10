package ru.tarasov.techservice.mapper;

import org.springframework.stereotype.Service;
import ru.tarasov.techservice.dto.BookingRequestDTO;
import ru.tarasov.techservice.dto.BookingResponseDTO;
import ru.tarasov.techservice.entity.Booking;

@Service
public class BookingMapper {
    public Booking mapToEntity(BookingRequestDTO bookingRequest) {
        return Booking.builder()
                .status(bookingRequest.status())
                .bookingTime(bookingRequest.bookingTime())
                .build();
    }

    public BookingResponseDTO mapToDTO(Booking booking) {
        return new BookingResponseDTO(
                booking.getBookingTime(),
                booking.getStatus(),
                booking.getFavor().getId(),
                booking.getUser().getId(),
                booking.getDiscount()
        );
    }
}
