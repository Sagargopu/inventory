package com.example.inventory.Controllers;

import com.example.inventory.DTO.AddressDTO;
import com.example.inventory.DTO.CategoryDTO;
import com.example.inventory.service.AddressService;
import com.example.inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO addAddress(@RequestBody AddressDTO addressDTO){
        return addressService.addAddress(addressDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDTO> getAllAddresses(){
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO getAddressById(@PathVariable Long id){
        return addressService.getAddressById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO updateAddress(@PathVariable Long id,@RequestBody AddressDTO addressDTO){
        return addressService.updateAddress(id,addressDTO);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
    }
}
