package com.example.inventory.repositoryDAO;

import com.example.inventory.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional(readOnly = true)
@Repository
public interface UserRepositoryDAO extends JpaRepository<Users,Long>{
    Optional<Users> findByEmail(String email);
}
