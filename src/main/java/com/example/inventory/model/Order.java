package com.example.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "id",nullable = false)
//    private Delivery delivery;

    @Column(name = "order_date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
