package com.example.inventory.repositoryDAO;

import com.example.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryDAO extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
