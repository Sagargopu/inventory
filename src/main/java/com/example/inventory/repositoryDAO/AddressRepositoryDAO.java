package com.example.inventory.repositoryDAO;

import com.example.inventory.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepositoryDAO extends JpaRepository<Address,Long> {
}
