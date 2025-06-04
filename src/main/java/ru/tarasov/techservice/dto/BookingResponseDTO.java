package ru.tarasov.techservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ru.tarasov.techservice.constants.BookingStatus;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BookingResponseDTO(
        Long id,
        LocalDateTime bookingTime,
        BookingStatus status,
        Long serviceId,
        Long serviceName
) {
}
