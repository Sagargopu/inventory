package com.example.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "stock_arrivals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockArrival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "stockArrival")
    private List<StockItem> stockItems;
}
