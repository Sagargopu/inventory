package com.example.inventory.repositoryDAO;

import com.example.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepositoryDAO extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findByProductId(Long productId);
}
