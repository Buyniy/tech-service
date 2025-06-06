package ru.tarasov.techservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tarasov.techservice.entity.Favor;

public interface FavorRepository extends JpaRepository<Favor, Long> {
}
