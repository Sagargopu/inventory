package com.example.inventory.service;

import com.example.inventory.DTO.VehicleDTO;
import com.example.inventory.model.Product;
import com.example.inventory.model.Vehicle;
import com.example.inventory.repositoryDAO.VehicleRepositoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepositoryDAO vehicleRepositoryDAO;

    public VehicleService(VehicleRepositoryDAO vehicleRepositoryDAO) {
        this.vehicleRepositoryDAO = vehicleRepositoryDAO;
    }

    public VehicleDTO convertToDTO(Vehicle vehicle){
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .regNumber(vehicle.getRegNumber())
                .type(vehicle.getType())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .vin(vehicle.getVin()).build();
    }

    public Vehicle convertToEntity(VehicleDTO vehicleDTO){
        return Vehicle.builder()
                .id(vehicleDTO.getId())
                .regNumber(vehicleDTO.getRegNumber())
                .type(vehicleDTO.getType())
                .make(vehicleDTO.getMake())
                .model(vehicleDTO.getModel())
                .year(vehicleDTO.getYear())
                .vin(vehicleDTO.getVin()).build();
    }

    public VehicleDTO addVehicle(VehicleDTO vehicleDTO){
        Vehicle vehicle=convertToEntity(vehicleDTO);
        vehicleRepositoryDAO.save(vehicle);
        return convertToDTO(vehicle);
    }

    public List<VehicleDTO> getAllVehicles(){
        return vehicleRepositoryDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public VehicleDTO getVehicleById(Long id){
        Vehicle vehicle = vehicleRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        return convertToDTO(vehicle);
    }

    public VehicleDTO updateVehicle(Long id,VehicleDTO vehicleDTO){
        Vehicle existingProduct=vehicleRepositoryDAO.findById(id).orElseThrow(()-> new RuntimeException("Product not found with id:" + id));
        existingProduct.setRegNumber(vehicleDTO.getRegNumber());
        existingProduct.setType(vehicleDTO.getType());
        existingProduct.setMake(vehicleDTO.getMake());
        existingProduct.setModel(vehicleDTO.getModel());
        existingProduct.setYear(vehicleDTO.getYear());
        existingProduct.setVin(vehicleDTO.getVin());
        return convertToDTO(vehicleRepositoryDAO.save(existingProduct));
    }

    public void deleteVehicle(Long id){
        if(!vehicleRepositoryDAO.existsById(id)){
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
        vehicleRepositoryDAO.deleteById(id);
    }
}
