package ru.tarasov.techservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import ru.tarasov.techservice.constant.BookingStatus;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BookingRequestDTO(

        @Positive
        Long favorId,

        @FutureOrPresent
        LocalDateTime bookingTime,

        BookingStatus status,

        @NotBlank
        @NotEmpty
        String username
) {
}
