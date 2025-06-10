package ru.tarasov.techservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.tarasov.techservice.dto.FavorRequestDTO;
import ru.tarasov.techservice.dto.FavorResponseDTO;
import ru.tarasov.techservice.entity.Favor;
import ru.tarasov.techservice.exception.FavorException;
import ru.tarasov.techservice.mapper.FavorMapper;
import ru.tarasov.techservice.repository.FavorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavorService {

    private final FavorRepository favorRepository;
    private final FavorMapper favorMapper;

    public Long createFavor(FavorRequestDTO favorRequest) {
        Favor favor = favorMapper.mapToEntity(favorRequest);

        favorRepository.save(favor);

        return favor.getId();
    }

    public FavorResponseDTO updateFavor(FavorRequestDTO favorRequest, Long id) {
        Favor favor = favorRepository.findById(id)
                .orElseThrow(() -> new FavorException(HttpStatus.NOT_FOUND, "Запись не найдена"));

        favor.setName(favorRequest.name());
        favor.setPrice(favorRequest.price());

        favorRepository.save(favor);

        return favorMapper.mapToDTO(favor);
    }

    public List<FavorResponseDTO> getAllFavors() {
        return favorRepository.findAll().stream().map(favorMapper::mapToDTO).toList();
    }

    public FavorResponseDTO getFavorById(long id) {
        Favor favor = favorRepository.findById(id)
                .orElseThrow(() -> new FavorException(HttpStatus.NOT_FOUND, "Запись не найдена"));

        return favorMapper.mapToDTO(favor);
    }
}
