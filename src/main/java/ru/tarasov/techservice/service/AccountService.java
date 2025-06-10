package ru.tarasov.techservice.service;

import com.nimbusds.jose.JOSEException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tarasov.techservice.constant.RoleType;
import ru.tarasov.techservice.entity.ApplicationUser;
import ru.tarasov.techservice.entity.Token;
import ru.tarasov.techservice.entity.UserRole;
import ru.tarasov.techservice.repository.ApplicationUserRepository;
import ru.tarasov.techservice.repository.UserRoleRepository;

import javax.security.auth.login.AccountException;

@Service
public class AccountService {

    private final ApplicationUserRepository applicationUserRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtSecurityService jwtSecurityService;
    private final AuthenticationManager authenticationManager;

    public AccountService(ApplicationUserRepository applicationUserRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, JwtSecurityService jwtSecurityService, AuthenticationManager authenticationManager) {
        this.applicationUserRepository = applicationUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtSecurityService = jwtSecurityService;
        this.authenticationManager = authenticationManager;
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

    public Token loginAccount(String username, String password) throws AccountException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Token token = new Token();
        try {
            token.setToken(jwtSecurityService.generateToken((UserDetails) authentication.getPrincipal()));
            token.setRefreshToken(jwtSecurityService.generateRefreshToken());
        } catch (JOSEException e) {
            throw new AccountException("Токен не может быть создан: " + e.getMessage());
        }
        System.out.println(token);
        return token;
    }
}
