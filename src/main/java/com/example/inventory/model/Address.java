package com.example.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "house_num", nullable = false, length = 10)
    private String houseNum;

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "unit", length = 10)
    private String unit;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "zipcode", nullable = false, length = 10)
    private String zipcode;

    @OneToMany(mappedBy = "destination")
    private List<Delivery> deliveries;
}
