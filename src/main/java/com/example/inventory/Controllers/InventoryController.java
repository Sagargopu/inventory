package com.example.inventory.Controllers;

import com.example.inventory.DTO.InventoryDTO;
import com.example.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDTO> getInventory(){
        return inventoryService.getAllInventory();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryDTO addInventory(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.addInventory(inventoryDTO);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDTO getInventoryByProductId(@PathVariable Long id){
        return inventoryService.getInventoryByProductId(id);
    }

}
