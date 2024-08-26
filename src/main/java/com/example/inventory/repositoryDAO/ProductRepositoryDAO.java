package com.example.inventory.repositoryDAO;

import com.example.inventory.DTO.ProductDTO;
import com.example.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositoryDAO extends JpaRepository<Product, Long> {

}
