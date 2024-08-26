package com.example.inventory.service;

import com.example.inventory.DTO.DeliveryDTO;
import com.example.inventory.model.*;
import com.example.inventory.repositoryDAO.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    private final DeliveryRepositoryDAO deliveryRepositoryDAO;
    private final DriverRepositoryDAO driverRepositoryDAO;
    private final VehicleRepositoryDAO vehicleRepositoryDAO;
    private final AddressRepositoryDAO addressRepositoryDAO;
    private final OrderRepositoryDAO orderRepositoryDAO;
    private final OrderService orderService;
    private final DriverService driverService;
    private final AddressService addressService;
    private final VehicleService vehicleService;

    public DeliveryService(DeliveryRepositoryDAO deliveryRepositoryDAO, DriverRepositoryDAO driverRepositoryDAO, VehicleRepositoryDAO vehicleRepositoryDAO, AddressRepositoryDAO addressRepositoryDAO, OrderRepositoryDAO orderRepositoryDAO, OrderService orderService, DriverService driverService, AddressService addressService, VehicleService vehicleService) {
        this.deliveryRepositoryDAO = deliveryRepositoryDAO;
        this.driverRepositoryDAO = driverRepositoryDAO;
        this.vehicleRepositoryDAO = vehicleRepositoryDAO;
        this.addressRepositoryDAO = addressRepositoryDAO;
        this.orderRepositoryDAO = orderRepositoryDAO;
        this.orderService = orderService;
        this.driverService = driverService;
        this.addressService = addressService;
        this.vehicleService = vehicleService;
    }

    public DeliveryDTO convertToDTO(Delivery delivery) {
        return DeliveryDTO.builder()
                .id(delivery.getId())
                .orderId(orderService.convertToDTO(delivery.getOrder()))
                .driverId(driverService.convertToDTO(delivery.getDriver()))
                .vehicleId(vehicleService.convertToDTO(delivery.getVehicle()))  // Get the Vehicle ID directly
                .destinationId(addressService.convertToDTO(delivery.getDestination()))  // Get the Address ID directly
                .scheduleDate(delivery.getScheduleDate())
                .status(delivery.getStatus())
                .build();
    }

    public Delivery convertToEntity(DeliveryDTO deliveryDTO) {
        return Delivery.builder()
                .id(deliveryDTO.getId())
                .order(orderService.convertToEntity(deliveryDTO.getOrderId()))
                .driver(driverService.convertToEntity(deliveryDTO.getDriverId()))
                .vehicle(vehicleService.convertToEntity(deliveryDTO.getVehicleId()))
                .destination(addressService.convertToEntity(deliveryDTO.getDestinationId()))
                .scheduleDate(deliveryDTO.getScheduleDate())
                .status(deliveryDTO.getStatus())
                .build();
    }



    public DeliveryDTO addDelivery(DeliveryDTO deliveryDTO) {
        Driver driver = driverRepositoryDAO.findById(deliveryDTO.getDriverId().getId())
                .orElseGet(() -> {
                    Driver newDriver = driverService.convertToEntity(deliveryDTO.getDriverId());
                    return driverRepositoryDAO.save(newDriver);
                });
        Vehicle vehicle = vehicleRepositoryDAO.findById(deliveryDTO.getVehicleId().getId())
                .orElseGet(() -> {
                    Vehicle newVehicle = vehicleService.convertToEntity(deliveryDTO.getVehicleId());
                    return vehicleRepositoryDAO.save(newVehicle);
                });
        Address address = addressRepositoryDAO.findById(deliveryDTO.getDestinationId().getId())
                .orElseGet(() -> {
                    Address newAddress = addressService.convertToEntity(deliveryDTO.getDestinationId());
                    return addressRepositoryDAO.save(newAddress);
                });
        Order order = orderRepositoryDAO.findById(deliveryDTO.getOrderId().getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + deliveryDTO.getOrderId().getId()));


        Delivery delivery = Delivery.builder()
                .id(deliveryDTO.getId())
                .driver(driver)
                .vehicle(vehicle)
                .order(order)
                .destination(address)
                .scheduleDate(deliveryDTO.getScheduleDate())
                .status(deliveryDTO.getStatus())
                .build();

        Delivery savedDelivery = deliveryRepositoryDAO.save(delivery);

        return convertToDTO(savedDelivery);
    }

    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        return convertToDTO(delivery);
    }

    public List<DeliveryDTO> getAllDeliveries() {
        List<Delivery> deliveries = deliveryRepositoryDAO.findAll();
        return deliveries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO) {
        Delivery existingDelivery = deliveryRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));

        existingDelivery.setDriver(driverService.convertToEntity(driverService.getDriverById(deliveryDTO.getDriverId().getId())));
        existingDelivery.setVehicle(vehicleService.convertToEntity(deliveryDTO.getVehicleId()));
        existingDelivery.setDestination(addressService.convertToEntity(deliveryDTO.getDestinationId()));
        existingDelivery.setScheduleDate(deliveryDTO.getScheduleDate());
        existingDelivery.setStatus(deliveryDTO.getStatus());

        Delivery updatedDelivery = deliveryRepositoryDAO.save(existingDelivery);
        return convertToDTO(updatedDelivery);
    }

    public void deleteDelivery(Long id) {
        Delivery delivery = deliveryRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        deliveryRepositoryDAO.delete(delivery);
    }






}
