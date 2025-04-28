package ru.tarasov.techservice.dto;

public class ServiceRequestDTO {

    private String name;

    public ServiceRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
