package com.example.inventory.DTO;

import com.example.inventory.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private Long id;
    private Integer quantity;
    private ProductDTO product;
}
