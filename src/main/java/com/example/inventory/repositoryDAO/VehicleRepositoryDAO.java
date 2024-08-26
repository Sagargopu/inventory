package com.example.inventory.repositoryDAO;

import com.example.inventory.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepositoryDAO extends JpaRepository<Vehicle,Long> {
}
