package ru.tarasov.techservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ru.tarasov.techservice.constant.BookingStatus;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BookingResponseDTO(
        LocalDateTime bookingTime,
        BookingStatus status,
        Long favorId,
        Long userId,
        Double discount
) {
}
