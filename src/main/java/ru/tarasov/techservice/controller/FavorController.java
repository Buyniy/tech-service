package ru.tarasov.techservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.tarasov.techservice.dto.FavorRequestDTO;
import ru.tarasov.techservice.dto.FavorResponseDTO;
import ru.tarasov.techservice.exceptions.FavorException;
import ru.tarasov.techservice.service.FavorService;

import java.net.URI;
import java.util.List;

@Slf4j
@RequestMapping("/api/v1/favors")
@RestController
public class FavorController {

    private final FavorService favorService;

    public FavorController(FavorService favorService) {
        this.favorService = favorService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createFavor(@Valid @RequestBody FavorRequestDTO favorRequest) {
        log.info("POST request. Favor: {}", favorRequest);
        URI uri = URI.create("api/v1/favors?id=" + favorService.createFavor(favorRequest));
        return ResponseEntity.created(uri)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavorResponseDTO> updateFavor(@Valid @RequestBody FavorRequestDTO favorRequest, @Positive @PathVariable Long id) {
        log.info("PUT request. Favor: {}", favorRequest);
        try {
            return new ResponseEntity<>(favorService.updateFavor(favorRequest, id), HttpStatus.OK);
        } catch (FavorException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }

    @GetMapping(path = "/", produces = "application/json")
    public List<FavorResponseDTO> getAllFavors() {
        log.info("GET request. All favors.");
        return favorService.getAllFavors();
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public ResponseEntity<FavorResponseDTO> getFavorById(@Positive @PathVariable Long id) {
        log.info("GET request. Favor with id: {}", id);
        try {
            return new ResponseEntity<>(favorService.getFavorById(id), HttpStatus.OK);
        } catch (FavorException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }
}
