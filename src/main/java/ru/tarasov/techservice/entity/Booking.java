package ru.tarasov.techservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.tarasov.techservice.constants.BookingStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "favor_id", nullable = false)
    private Favor favor;
}
