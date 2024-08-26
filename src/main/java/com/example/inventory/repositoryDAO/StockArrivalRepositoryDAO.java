package com.example.inventory.repositoryDAO;

import com.example.inventory.model.StockArrival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockArrivalRepositoryDAO extends JpaRepository<StockArrival,Long> {
}
