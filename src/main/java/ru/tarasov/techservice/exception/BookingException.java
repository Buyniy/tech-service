package ru.tarasov.techservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BookingException extends RuntimeException {
    private HttpStatus httpStatus;

    public BookingException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}