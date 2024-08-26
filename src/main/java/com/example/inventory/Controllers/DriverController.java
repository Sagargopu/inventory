package com.example.inventory.Controllers;

import com.example.inventory.DTO.DriverDTO;
import com.example.inventory.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO addDriver(@RequestBody DriverDTO driverDTO) {
        System.out.println("Received Driver DTO: " + driverDTO);
        return driverService.addDriver(driverDTO);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DriverDTO> getAllDrivers() {
        return driverService.getAllDrivers();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverDTO getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DriverDTO updateDriver(@PathVariable Long id, @RequestBody DriverDTO driverDTO) {
        return driverService.updateDriver(id, driverDTO);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }
}
