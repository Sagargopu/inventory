package com.example.inventory.repositoryDAO;

import com.example.inventory.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepositoryDAO extends JpaRepository<Driver,Long> {
}
