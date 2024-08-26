package com.example.inventory.service;

import com.example.inventory.DTO.CategoryDTO;
import com.example.inventory.DTO.ProductDTO;
import com.example.inventory.DTO.StockArrivalDTO;
import com.example.inventory.DTO.StockItemDTO;
import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.model.StockArrival;
import com.example.inventory.model.StockItem;
import com.example.inventory.repositoryDAO.StockArrivalItemRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockArrivalItemService {
    private final StockArrivalItemRepositoryDAO stockArrivalItemRepositoryDAO;
    @Autowired
    public StockArrivalItemService(StockArrivalItemRepositoryDAO stockArrivalItemRepositoryDAO) {
        this.stockArrivalItemRepositoryDAO = stockArrivalItemRepositoryDAO;
    }

    private StockItemDTO convertToDTO(StockItem stockItem){
        CategoryDTO categoryDTO =CategoryDTO.builder()
                .id(stockItem.getProduct().getCategory().getId())
                .name(stockItem.getProduct().getCategory().getName())
                .build();
        StockArrivalDTO stockArrivalDTO=StockArrivalDTO.builder()
                .id(stockItem.getStockArrival().getId())
                .date(stockItem.getStockArrival().getDate()).build();
        ProductDTO productDTO=ProductDTO.builder()
                .id(stockItem.getProduct().getId())
                .name(stockItem.getProduct().getName())
                .description(stockItem.getProduct().getDescription())
                .weight(stockItem.getProduct().getWeight())
                .price(stockItem.getProduct().getPrice())
                .category(categoryDTO).build();
        return StockItemDTO.builder()
                .id(stockItem.getId())
                .stockArrival(stockArrivalDTO)
                .product(productDTO)
                .quantity(stockItem.getQuantity()).build();
    }

    private StockItem convertToEntity(StockItemDTO stockItemDTO){
        System.out.println(stockItemDTO.getProduct());
        Category category=Category.builder()
                .id(stockItemDTO.getProduct().getCategory().getId())
                .name(stockItemDTO.getProduct().getCategory().getName()).build();
        StockArrival stockArrival=StockArrival.builder()
                .id(stockItemDTO.getStockArrival().getId())
                .date(stockItemDTO.getStockArrival().getDate()).build();
        Product product=Product.builder()
                .id(stockItemDTO.getProduct().getId())
                .name(stockItemDTO.getProduct().getName())
                .description(stockItemDTO.getProduct().getDescription())
                .weight(stockItemDTO.getProduct().getWeight())
                .price(stockItemDTO.getProduct().getPrice())
                .category(category).build();
        return StockItem.builder()
                .id(stockItemDTO.getId())
                .stockArrival(stockArrival)
                .product(product)
                .quantity(stockItemDTO.getQuantity()).build();
    }

    public StockItemDTO addStockItem(StockItemDTO stockItemDTO){
        StockItem stockItem= convertToEntity(stockItemDTO);
        StockItem savedProduct=stockArrivalItemRepositoryDAO.save(stockItem);
        return convertToDTO(savedProduct);
    }


    public List<StockItemDTO> getItemsByStockArrivalId(Long id){
        List<StockItem> stockItems=stockArrivalItemRepositoryDAO.findByStockArrival_Id(id);
        return stockItems.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
