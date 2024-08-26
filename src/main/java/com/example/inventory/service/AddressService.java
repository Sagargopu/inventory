package com.example.inventory.service;

import com.example.inventory.DTO.AddressDTO;
import com.example.inventory.DTO.CategoryDTO;
import com.example.inventory.model.Address;
import com.example.inventory.repositoryDAO.AddressRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepositoryDAO addressRepositoryDAO;
    @Autowired
    public AddressService(AddressRepositoryDAO addressRepositoryDAO) {
        this.addressRepositoryDAO = addressRepositoryDAO;
    }

    public AddressDTO convertToDTO(Address address){
        return AddressDTO.builder()
                .id(address.getId())
                .houseNum(address.getHouseNum())
                .street(address.getStreet())
                .unit(address.getUnit())
                .city(address.getCity())
                .state(address.getState())
                .zipcode(address.getZipcode()).build();
    }

    public Address convertToEntity(AddressDTO addressDTO){
        return Address.builder()
                .id(addressDTO.getId())
                .houseNum(addressDTO.getHouseNum())
                .street(addressDTO.getStreet())
                .unit(addressDTO.getUnit())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipcode(addressDTO.getZipcode()).build();
    }

    public AddressDTO addAddress(AddressDTO addressDTO){
        Address address=convertToEntity(addressDTO);
        Address savedAddress=addressRepositoryDAO.save(address);
        return convertToDTO(savedAddress);
    }

    public List<AddressDTO> getAllAddresses() {
        return addressRepositoryDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AddressDTO getAddressById(Long id) {
        return addressRepositoryDAO.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address existingAddress=addressRepositoryDAO.findById(id).orElseThrow(()-> new RuntimeException("Product not found with id:" + id));
        existingAddress.setHouseNum(addressDTO.getHouseNum());
        existingAddress.setHouseNum(addressDTO.getStreet());
        existingAddress.setUnit(addressDTO.getUnit());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setZipcode(addressDTO.getZipcode());
        return convertToDTO(existingAddress);
    }

    public void deleteAddress(Long id) {
    }
}
