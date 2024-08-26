package com.example.inventory.service;

import com.example.inventory.DTO.CategoryDTO;
import com.example.inventory.DTO.ProductDTO;
import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.repositoryDAO.CategoryRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepositoryDAO categoryRepositoryDAO;
    @Autowired
    public CategoryService(CategoryRepositoryDAO categoryRepositoryDAO) {
        this.categoryRepositoryDAO = categoryRepositoryDAO;
    }

    private CategoryDTO convertToDTO(Category category){
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private Category convertToEntity(CategoryDTO categoryDTO){
        return Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .build();
    }

    public CategoryDTO addCategory(CategoryDTO categoryDTO){
        Category category=convertToEntity(categoryDTO);
        Category savedCategory=categoryRepositoryDAO.save(category);
        return convertToDTO(savedCategory);
    }
    public List<CategoryDTO> getAllCategories(){
        return categoryRepositoryDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id){
        return categoryRepositoryDAO.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO){
        Category existingCategory=categoryRepositoryDAO.findById(id).orElseThrow(()-> new RuntimeException("Product not found with id:" + id));
        existingCategory.setName(categoryDTO.getName());
        return convertToDTO(categoryRepositoryDAO.save(existingCategory));
    }

    public void deleteCategory(Long id){
        if(!categoryRepositoryDAO.existsById(id)){
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepositoryDAO.deleteById(id);
    }
}
