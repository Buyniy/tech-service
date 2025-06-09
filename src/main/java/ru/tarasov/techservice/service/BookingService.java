package ru.tarasov.techservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tarasov.techservice.constant.BookingStatus;
import ru.tarasov.techservice.dto.BookingRequestDTO;
import ru.tarasov.techservice.dto.BookingResponseDTO;
import ru.tarasov.techservice.dto.FavorResponseDTO;
import ru.tarasov.techservice.dto.RevenueReportDTO;
import ru.tarasov.techservice.entity.ApplicationUser;
import ru.tarasov.techservice.entity.Booking;
import ru.tarasov.techservice.entity.Favor;
import ru.tarasov.techservice.exception.BookingException;
import ru.tarasov.techservice.exception.FavorException;
import ru.tarasov.techservice.mapper.BookingMapper;
import ru.tarasov.techservice.mapper.FavorMapper;
import ru.tarasov.techservice.repository.ApplicationUserRepository;
import ru.tarasov.techservice.repository.BookingRepository;
import ru.tarasov.techservice.repository.FavorRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FavorRepository favorRepository;
    private final ApplicationUserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final FavorMapper favorMapper;

    public Long createBooking(BookingRequestDTO bookingRequest) {
        Favor favor = favorRepository.findById(bookingRequest.favorId())
                .orElseThrow(() -> new FavorException(HttpStatus.NOT_FOUND, "Запись не найдена"));

        ApplicationUser user = userRepository.findByUsername(bookingRequest.username())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Booking booking = bookingMapper.mapToEntity(bookingRequest);
        booking.setFavor(favor);
        booking.setUser(user);
        booking.setStatus(BookingStatus.NEW);

        bookingRepository.save(booking);

        return booking.getId();
    }

    public Long cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingException(HttpStatus.NOT_FOUND, "Запись не найдена"));

        booking.setStatus(BookingStatus.CANCELED);

        bookingRepository.save(booking);

        return booking.getId();
    }

    public BookingResponseDTO updateBookingTime(BookingRequestDTO bookingRequest, Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingException(HttpStatus.NOT_FOUND, "Запись не найдена"));

        booking.setBookingTime(bookingRequest.bookingTime());

        bookingRepository.save(booking);

        return bookingMapper.mapToDTO(booking);
    }

    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::mapToDTO).toList();
    }

    public List<FavorResponseDTO> getProvidedFavors() {
        return bookingRepository.findProvidedFavors().stream().map(favorMapper::mapToDTO).toList();
    }

    public List<BookingResponseDTO> getAllBookingsByTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return bookingRepository.findByBookingTimeBetween(startTime, endTime).stream().map(bookingMapper::mapToDTO).toList();
    }

    public List<RevenueReportDTO> getRevenueReport(LocalDateTime startTime, LocalDateTime endTime) {
        return bookingRepository.getRevenueReport(startTime.toLocalDate(), endTime.toLocalDate());
    }
}
