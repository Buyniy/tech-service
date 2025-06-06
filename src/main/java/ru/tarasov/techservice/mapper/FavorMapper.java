package ru.tarasov.techservice.mapper;

import org.springframework.stereotype.Service;
import ru.tarasov.techservice.dto.FavorRequestDTO;
import ru.tarasov.techservice.dto.FavorResponseDTO;
import ru.tarasov.techservice.entity.Favor;

@Service
public class FavorMapper {
    public Favor mapToEntity(FavorRequestDTO favorRequest) {
        return Favor.builder()
                .name(favorRequest.name())
                .price(favorRequest.price())
                .build();
    }

    public FavorResponseDTO mapToDTO(Favor favor) {
        return new FavorResponseDTO(
                favor.getName(),
                favor.getPrice()
        );
    }
}
