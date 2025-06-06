package ru.tarasov.techservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tarasov.techservice.dto.RevenueReportDTO;
import ru.tarasov.techservice.entity.Booking;
import ru.tarasov.techservice.entity.Favor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookingTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT b.favor FROM Booking b " +
            "WHERE b.status = 'PROVIDED' ")
    List<Favor> findProvidedFavors();

    @Query("SELECT NEW ru.tarasov.techservice.dto.RevenueReportDTO(" +
            "CAST(b.bookingTime as LOCALDATE), SUM(f.price)) " +
            "FROM Booking b JOIN b.favor f " +
            "WHERE b.status = 'PROVIDED' " +
            "AND CAST(b.bookingTime as LOCALDATE) BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(b.bookingTime as LOCALDATE) " +
            "ORDER BY CAST(b.bookingTime as LOCALDATE)")
    List<RevenueReportDTO> getRevenueReport(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
