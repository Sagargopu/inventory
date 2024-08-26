package com.example.inventory.Controllers;

import com.example.inventory.DTO.ProductDTO;
import com.example.inventory.DTO.StockArrivalDTO;
import com.example.inventory.service.StockArrivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockarrivals")
public class StockArrivalController {

    private final StockArrivalService stockArrivalService;
    @Autowired
    public StockArrivalController(StockArrivalService stockArrivalService) {
        this.stockArrivalService = stockArrivalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockArrivalDTO addStockArrival(@RequestBody StockArrivalDTO stockArrivalDTO){
        return stockArrivalService.addStockArrival(stockArrivalDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockArrivalDTO> getAllStockArrivals(){
        return stockArrivalService.getAllStockArrivals();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StockArrivalDTO getStockArrivalById(@PathVariable Long id){
        return stockArrivalService.getStockArrivalById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StockArrivalDTO updateStockArrival(@PathVariable Long id,@RequestBody StockArrivalDTO stockArrivalDTO){
        return stockArrivalService.updateStockArrival(id,stockArrivalDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStockArrival(@PathVariable Long id){
        stockArrivalService.deleteStockArrivalById(id);
    }

}
