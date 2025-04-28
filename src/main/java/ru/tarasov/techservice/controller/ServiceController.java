package ru.tarasov.techservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.tarasov.techservice.dto.ServiceRequestDTO;
import ru.tarasov.techservice.dto.ServiceResponseDTO;

import java.util.Collections;
import java.util.List;

@RequestMapping("/api/v1/services")
@RestController
public class ServiceController {

    @PostMapping("/")
    public void createService(@RequestBody ServiceRequestDTO serviceRequest) {
    }

    @PutMapping("/{id}")
    public void updateService(@RequestBody ServiceRequestDTO serviceRequest, @PathVariable Long id) {
    }

    @GetMapping("/all")
    public List<ServiceResponseDTO> getServices() {
        return Collections.emptyList();
    }

    @GetMapping("/id/{id}")
    public ServiceResponseDTO getServiceById(@PathVariable Long id) {
        return null;
    }
}
