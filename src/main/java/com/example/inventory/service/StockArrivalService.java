package com.example.inventory.service;

import com.example.inventory.DTO.CategoryDTO;
import com.example.inventory.DTO.ProductDTO;
import com.example.inventory.DTO.StockArrivalDTO;
import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.model.StockArrival;
import com.example.inventory.repositoryDAO.StockArrivalRepositoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockArrivalService {
    private final StockArrivalRepositoryDAO stockArrivalRepositoryDAO;

    public StockArrivalService(StockArrivalRepositoryDAO stockArrivalRepositoryDAO) {
        this.stockArrivalRepositoryDAO = stockArrivalRepositoryDAO;
    }
    private StockArrivalDTO convertToDTO(StockArrival stockArrival){
        return StockArrivalDTO.builder()
                .id(stockArrival.getId())
                .date(stockArrival.getDate()).build();
    }
    private StockArrival convertToEntity(StockArrivalDTO stockArrivalDTO){
        return StockArrival.builder()
                .id(stockArrivalDTO.getId())
                .date((stockArrivalDTO.getDate())).build();
    }

    public StockArrivalDTO addStockArrival(StockArrivalDTO stockArrivalDTO){
        StockArrival stockArrival=convertToEntity(stockArrivalDTO);
        StockArrival savedProduct=stockArrivalRepositoryDAO.save(stockArrival);
        return convertToDTO(savedProduct);
    }

    public List<StockArrivalDTO> getAllStockArrivals(){
        return stockArrivalRepositoryDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public StockArrivalDTO getStockArrivalById(Long id){
        return stockArrivalRepositoryDAO.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Stock Arrival not found with id: " + id));
    }

    public StockArrivalDTO updateStockArrival(Long id,StockArrivalDTO stockArrivalDTO){
        StockArrival existingStockArrival=stockArrivalRepositoryDAO.findById(id).orElseThrow(()-> new RuntimeException("Stock Arrival not found with id:" + id));
        existingStockArrival.setDate(stockArrivalDTO.getDate());
        return convertToDTO(stockArrivalRepositoryDAO.save(existingStockArrival));
    }

    public void deleteStockArrivalById(Long id){
        if(!stockArrivalRepositoryDAO.existsById(id)){
            throw new RuntimeException("Stock Arrival not found with id: " + id);
        }
        stockArrivalRepositoryDAO.deleteById(id);
    }

}
