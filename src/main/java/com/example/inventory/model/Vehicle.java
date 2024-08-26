package com.example.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_number", nullable = false, unique = true, length = 20)
    private String regNumber;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "make", nullable = false, length = 50)
    private String make;

    @Column(name = "model", nullable = false, length = 50)
    private String model;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "vin", nullable = false, unique = true, length = 17)
    private String vin;

    @OneToMany(mappedBy = "vehicle")
    private List<Delivery> deliveries;
}
