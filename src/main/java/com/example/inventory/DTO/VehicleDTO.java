package com.example.inventory.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id;
    private String regNumber;
    private String type;
    private String make;
    private String model;
    private int year;
    private String vin;
}
