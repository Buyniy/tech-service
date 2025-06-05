package ru.tarasov.techservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TimeIntervalDTO(
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
