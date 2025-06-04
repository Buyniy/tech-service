package ru.tarasov.techservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import ru.tarasov.techservice.dto.ServiceRequestDTO;
import ru.tarasov.techservice.dto.ServiceResponseDTO;

import java.util.Collections;
import java.util.List;

@RequestMapping("/api/v1/services")
@RestController
public class ServiceController {

    @PostMapping("/")
    public void createService(@Valid @RequestBody ServiceRequestDTO serviceRequest) {
    }

    @PutMapping("/{id}")
    public void updateService(@Valid @RequestBody ServiceRequestDTO serviceRequest, @Positive @PathVariable Long id) {
    }

    @GetMapping("/all")
    public List<ServiceResponseDTO> getAllServices() {
        return Collections.emptyList();
    }

    @GetMapping("/id/{id}")
    public ServiceResponseDTO getServiceById(@Positive @PathVariable Long id) {
        return null;
    }
}
