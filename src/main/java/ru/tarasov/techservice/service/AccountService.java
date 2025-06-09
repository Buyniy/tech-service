package ru.tarasov.techservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tarasov.techservice.constant.RoleType;
import ru.tarasov.techservice.entity.ApplicationUser;
import ru.tarasov.techservice.entity.UserRole;
import ru.tarasov.techservice.repository.ApplicationUserRepository;
import ru.tarasov.techservice.repository.UserRoleRepository;

import javax.security.auth.login.AccountException;

@Service
public class AccountService {

    private final ApplicationUserRepository applicationUserRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(ApplicationUserRepository applicationUserRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registration(ApplicationUser user) throws AccountException {
        if (applicationUserRepository.existsByUsername(user.getUsername())) {
            throw new AccountException("Имя пользователя уже используется");
        }
        userRoleRepository.findByRoleType(RoleType.ROLE_USER)
                .ifPresentOrElse(user::setUserRole,
                        () -> {
                            UserRole userRole = new UserRole();
                            userRole.setRoleType(RoleType.ROLE_USER);
                            user.setUserRole(userRole);
                            userRoleRepository.save(userRole);
                        }
                );
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }
}
