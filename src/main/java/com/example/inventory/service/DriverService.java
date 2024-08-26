package com.example.inventory.service;

import com.example.inventory.DTO.DriverDTO;

import com.example.inventory.model.Driver;
import com.example.inventory.repositoryDAO.DriverRepositoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {
    private final DriverRepositoryDAO driverRepositoryDAO;

    public DriverService(DriverRepositoryDAO driverRepositoryDAO) {
        this.driverRepositoryDAO = driverRepositoryDAO;
    }

    public DriverDTO convertToDTO(Driver driver) {
        return DriverDTO.builder()
                .id(driver.getId())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .DLnum(driver.getDLnum())
                .build();
    }

    public Driver convertToEntity(DriverDTO driverDTO) {
        return Driver.builder()
                .id(driverDTO.getId())
                .firstName(driverDTO.getFirstName())
                .lastName(driverDTO.getLastName())
                .DLnum(driverDTO.getDLnum())
                .build();
    }

    public DriverDTO addDriver(DriverDTO driverDTO) {
        System.out.println(driverDTO.getFirstName());
        System.out.println(driverDTO.getDLnum());
        Driver driver = convertToEntity(driverDTO);
//        System.out.println(driver.getDLnum());
        Driver savedDriver = driverRepositoryDAO.save(driver);
//        System.out.println(savedDriver.getDLnum());
        return convertToDTO(savedDriver);
    }

    public List<DriverDTO> getAllDrivers() {
        List<Driver> drivers = driverRepositoryDAO.findAll();
        return drivers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DriverDTO getDriverById(Long id) {
        Driver driver = driverRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
        return convertToDTO(driver);
    }

    public DriverDTO updateDriver(Long id, DriverDTO driverDTO) {
        Driver existingDriver = driverRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));

        existingDriver.setFirstName(driverDTO.getFirstName());
        existingDriver.setLastName(driverDTO.getLastName());
        existingDriver.setDLnum(driverDTO.getDLnum());

        Driver updatedDriver = driverRepositoryDAO.save(existingDriver);
        return convertToDTO(updatedDriver);
    }


    public void deleteDriver(Long id) {
        Driver existingDriver = driverRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
        driverRepositoryDAO.delete(existingDriver);
    }


}
