package ru.tarasov.techservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tarasov.techservice.constant.RoleType;
import ru.tarasov.techservice.entity.UserRole;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByRoleType(RoleType roleType);
}
