package ru.tarasov.techservice.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tarasov.techservice.entity.ApplicationUser;
import ru.tarasov.techservice.repository.ApplicationUserRepository;

import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;

    public UserDetailService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        GrantedAuthority authority = new SimpleGrantedAuthority(
                applicationUser.getUserRole().getRoleType().name());
        System.out.println(authority.getAuthority());

        return new User(username, applicationUser.getPassword(), Set.of(authority));
    }
}
