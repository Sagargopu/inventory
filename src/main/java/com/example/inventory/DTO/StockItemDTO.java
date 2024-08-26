package com.example.inventory.DTO;

import com.example.inventory.model.Product;
import com.example.inventory.model.StockArrival;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockItemDTO {
    private Long id;
    private StockArrivalDTO stockArrival;
    private ProductDTO product;
    private Integer quantity;
}
