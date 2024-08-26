package com.example.inventory.service;

import com.example.inventory.DTO.CategoryDTO;
import com.example.inventory.DTO.ProductDTO;
import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.repositoryDAO.CategoryRepositoryDAO;
import com.example.inventory.repositoryDAO.ProductRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepositoryDAO productRepository;
    private final CategoryRepositoryDAO categoryRepositoryDAO;

    @Autowired
    public ProductService(ProductRepositoryDAO productRepository, CategoryRepositoryDAO categoryRepositoryDAO) {
        this.productRepository = productRepository;
        this.categoryRepositoryDAO = categoryRepositoryDAO;
    }

    //Convert Entity to DTO
    public ProductDTO convertToDTO(Product product){
        CategoryDTO categoryDTO=CategoryDTO.builder()
                .id(product.getCategory().getId())
                .name(product.getCategory().getName())
                .build();
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .weight((product.getWeight()))
                .price(product.getPrice())
                .category(categoryDTO).build();
    }

    public Product convertToEntity(ProductDTO productDTO){
        Category category=Category.builder()
                .id(productDTO.getCategory().getId())
                .name(productDTO.getCategory().getName())
                .build();
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .weight(productDTO.getWeight())
                .price(productDTO.getPrice())
                .category(category)
                .build();
    }

    public ProductDTO addProduct (ProductDTO productDTO){
        Category category = categoryRepositoryDAO.findByName(productDTO.getCategory().getName());
        if (category == null) {
            // Create new category if not found
            category = Category.builder()
                    .name(productDTO.getCategory().getName())
                    .build();
            category = categoryRepositoryDAO.save(category);
        }
        Product product=convertToEntity(productDTO);
        product.setCategory(category);
        Product savedProduct=productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts(){
        return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id){
        return productRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public ProductDTO updateProduct(Long id,ProductDTO productDTO){
        Category category=Category.builder()
                .id(productDTO.getCategory().getId())
                .name(productDTO.getCategory().getName())
                .build();
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id:" + id));
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setWeight(productDTO.getWeight());
        existingProduct.setCategory(category);
        existingProduct.setPrice(productDTO.getPrice());
        return convertToDTO(productRepository.save(existingProduct));
    }

    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
