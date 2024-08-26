package com.example.inventory.DTO;

import com.example.inventory.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private Long id;
    private OrderDTO orderId;
    private DriverDTO driverId;
    private VehicleDTO vehicleId;
    private AddressDTO destinationId;
    private LocalDate scheduleDate;
    private String status;
}
