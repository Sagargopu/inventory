package com.example.inventory.repositoryDAO;

import com.example.inventory.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockArrivalItemRepositoryDAO extends JpaRepository<StockItem,Long> {
    List<StockItem> findByStockArrival_Id(Long id);
}
