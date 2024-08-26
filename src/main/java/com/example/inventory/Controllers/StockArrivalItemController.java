package com.example.inventory.Controllers;

import com.example.inventory.DTO.ProductDTO;
import com.example.inventory.DTO.StockItemDTO;
import com.example.inventory.service.StockArrivalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockitems")
public class StockArrivalItemController {

    private final StockArrivalItemService stockArrivalItemService;
    @Autowired
    public StockArrivalItemController(StockArrivalItemService stockArrivalItemService) {
        this.stockArrivalItemService = stockArrivalItemService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<StockItemDTO> getAllStockItems(@PathVariable Long id){
        System.out.println(id);
        return stockArrivalItemService.getItemsByStockArrivalId(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockItemDTO addStockItem(StockItemDTO stockItemDTO){
        return stockArrivalItemService.addStockItem(stockItemDTO);
    }


}
