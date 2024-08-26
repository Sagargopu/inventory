package com.example.inventory.repositoryDAO;

import com.example.inventory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepositoryDAO extends JpaRepository<Order,Long>{
}
