package com.example.inventory.repositoryDAO;

import com.example.inventory.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeliveryRepositoryDAO extends JpaRepository<Delivery,Long> {
}
