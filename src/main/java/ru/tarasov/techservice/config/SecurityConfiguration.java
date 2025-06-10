package ru.tarasov.techservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.tarasov.techservice.service.UserDetailService;

import javax.security.auth.login.AccountException;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final UserDetailService userDetailService;

    public SecurityConfiguration(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws AccountException {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new AccountException("Менеджер аутентификации не сконфигурирован: " + e.getMessage());
        }
    }

    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/account/registration", "/account/login")
                        .not().authenticated()
                        .requestMatchers("/operator/create", "/operator/edit")
                        .hasRole("ADMIN")
                        .requestMatchers("/operator/booking", "/api/v1/bookings/")
                        .hasAnyRole("OPERATOR", "ADMIN")
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .anyRequest()
                        .authenticated())
                .build();
    }
}
