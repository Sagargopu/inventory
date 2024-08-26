package com.example.inventory.service;

import com.example.inventory.DTO.CategoryDTO;
import com.example.inventory.DTO.InventoryDTO;
import com.example.inventory.DTO.ProductDTO;
import com.example.inventory.model.Category;
import com.example.inventory.model.Inventory;
import com.example.inventory.model.Product;
import com.example.inventory.repositoryDAO.InventoryRepositoryDAO;
import com.example.inventory.repositoryDAO.ProductRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepositoryDAO inventoryRepositoryDAO;
    private final ProductRepositoryDAO productRepositoryDAO;

    @Autowired
    public InventoryService(InventoryRepositoryDAO inventoryRepositoryDAO, ProductRepositoryDAO productRepositoryDAO) {
        this.inventoryRepositoryDAO = inventoryRepositoryDAO;
        this.productRepositoryDAO = productRepositoryDAO;
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        CategoryDTO categoryDTO=CategoryDTO.builder()
                .id(inventory.getProduct().getCategory().getId())
                .name(inventory.getProduct().getCategory().getName())
                .build();
        ProductDTO productDTO = ProductDTO.builder()
                .id(inventory.getProduct().getId())
                .name(inventory.getProduct().getName())
                .description(inventory.getProduct().getDescription())
                .weight(inventory.getProduct().getWeight())
                .price(inventory.getProduct().getPrice())
                .category(categoryDTO)
                .build();
        return InventoryDTO.builder()
                .id(inventory.getId())
                .quantity(inventory.getQuantity())
                .product(productDTO).build();
    }

    private Inventory convertToEntity(InventoryDTO inventoryDTO) {
        Category category=Category.builder()
                .id(inventoryDTO.getProduct().getCategory().getId())
                .name(inventoryDTO.getProduct().getCategory().getName())
                .build();
        Product product = Product.builder()
                .id(inventoryDTO.getProduct().getId())
                .name(inventoryDTO.getProduct().getName())
                .description(inventoryDTO.getProduct().getDescription())
                .weight(inventoryDTO.getProduct().getWeight())
                .price(inventoryDTO.getProduct().getPrice())
                .category(category).build();

        // Build and return Inventory entity
        return Inventory.builder()
                .id(inventoryDTO.getId())
                .quantity(inventoryDTO.getQuantity())
                .product(product).build();
    }

    public InventoryDTO addInventory(InventoryDTO inventoryDTO){
        Inventory inventory=convertToEntity(inventoryDTO);
        Inventory savedProduct=inventoryRepositoryDAO.save(inventory);
        return convertToDTO(savedProduct);
    }

    public List<InventoryDTO> getAllInventory(){
        return inventoryRepositoryDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public InventoryDTO getInventoryByProductId(Long id){
        Product product = productRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        Inventory inventory = inventoryRepositoryDAO.findByProductId(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product ID: " + id));
        return convertToDTO(inventory);
    }
}