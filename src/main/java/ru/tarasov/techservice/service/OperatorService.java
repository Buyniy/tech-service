package ru.tarasov.techservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tarasov.techservice.constant.RoleType;
import ru.tarasov.techservice.entity.ApplicationUser;
import ru.tarasov.techservice.entity.Booking;
import ru.tarasov.techservice.entity.UserRole;
import ru.tarasov.techservice.exception.BookingException;
import ru.tarasov.techservice.repository.ApplicationUserRepository;
import ru.tarasov.techservice.repository.BookingRepository;
import ru.tarasov.techservice.repository.UserRoleRepository;

import javax.management.relation.RoleNotFoundException;
import javax.security.auth.login.AccountException;

@Service
public class OperatorService {

    private final ApplicationUserRepository applicationUserRepository;
    private final UserRoleRepository userRoleRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    public OperatorService(ApplicationUserRepository applicationUserRepository, UserRoleRepository userRoleRepository, BookingRepository bookingRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.bookingRepository = bookingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createOperator(ApplicationUser user) throws AccountException {
        if (applicationUserRepository.existsByUsername(user.getUsername())) {
            throw new AccountException("Имя пользователя уже используется");
        }
        userRoleRepository.findByRoleType(RoleType.ROLE_OPERATOR)
                .ifPresentOrElse(user::setUserRole,
                        () -> {
                            UserRole userRole = new UserRole();
                            userRole.setRoleType(RoleType.ROLE_OPERATOR);
                            user.setUserRole(userRole);
                            userRoleRepository.save(userRole);
                        }
                );
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }

    public void editOperator(ApplicationUser user) throws AccountException, RoleNotFoundException {
        ApplicationUser extUser = applicationUserRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        UserRole userRole = userRoleRepository.findByRoleType(user.getUserRole().getRoleType())
                .orElseThrow(() -> new RoleNotFoundException("Роль не найдена"));

        extUser.setUsername(user.getUsername());
        extUser.setPassword(passwordEncoder.encode(user.getPassword()));
        extUser.setUserRole(userRole);

        applicationUserRepository.save(extUser);
    }

    public void updateBooking(Booking booking) {
        Booking extBooking = bookingRepository.findById(booking.getId())
                .orElseThrow(() -> new BookingException(HttpStatus.NOT_FOUND, "Запись не найдена"));

        if (booking.getBookingTime() != null) {
            extBooking.setBookingTime(booking.getBookingTime());
        }
        if (booking.getStatus() != null) {
            extBooking.setStatus(booking.getStatus());
        }
        if (booking.getDiscount() != null) {
            extBooking.setDiscount(booking.getDiscount());
        }

        bookingRepository.save(extBooking);
    }
}
