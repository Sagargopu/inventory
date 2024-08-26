package com.example.inventory.repositoryDAO;

import com.example.inventory.DTO.OrderItemDTO;
import com.example.inventory.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OderItemRepositoryDAO extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrderId(Long orderId);
}
