package com.example.inventory.service;

import com.example.inventory.DTO.DeliveryDTO;
import com.example.inventory.DTO.OrderDTO;
import com.example.inventory.model.Delivery;
import com.example.inventory.model.Order;
import com.example.inventory.model.Role;
import com.example.inventory.model.Users;
import com.example.inventory.repositoryDAO.OrderRepositoryDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepositoryDAO orderRepositoryDAO;
    private final UserService userService;
    private final AddressService addressService;
    private final DriverService driverService;
    private final VehicleService vehicleService;

    public OrderService(OrderRepositoryDAO orderRepositoryDAO, UserService userService, AddressService addressService,DriverService driverService, VehicleService vehicleService) {
        this.orderRepositoryDAO = orderRepositoryDAO;
        this.userService = userService;
        this.addressService = addressService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
    }

    public OrderDTO convertToDTO(Order order) {
//        DeliveryDTO deliveryDTO=DeliveryDTO.builder()
//                .id(order.getDelivery().getId())
//                .orderId(convertToDTO(order))
//                .driverId(driverService.convertToDTO(order.getDelivery().getDriver()))
//                .vehicleId(vehicleService.convertToDTO(order.getDelivery().getVehicle()))
//                .destinationId(addressService.convertToDTO(order.getDelivery().getDestination()))
//                .scheduleDate(order.getDelivery().getScheduleDate())
//                .status(order.getDelivery().getStatus()).build();
        return OrderDTO.builder()
                .id(order.getId())
                .user(userService.convertToDTO(order.getUser()))
                .status(order.getStatus())
//                .delivery(deliveryDTO)
                .date(order.getDate())
                .build();
    }

    public Order convertToEntity(OrderDTO orderDTO) {
        Role role;
        try {
            role = Role.valueOf(orderDTO.getUser().getRole());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + orderDTO.getUser().getRole());
        }
        Users users= Users.builder()
                .id(orderDTO.getUser().getId())
                .firstName(orderDTO.getUser().getFirstName())
                .lastName(orderDTO.getUser().getLastName())
                .email(orderDTO.getUser().getEmail())
                .phone(orderDTO.getUser().getPhone())
                .role(role)
                .address(addressService.convertToEntity(orderDTO.getUser().getAddress())).build();
//        Delivery delivery=Delivery.builder()
//                .id(orderDTO.getDelivery().getId())
//                .order(convertToEntity(orderDTO.getDelivery().getOrderId()))
//                .driver(driverService.convertToEntity(orderDTO.getDelivery().getDriverId()))
//                .vehicle(vehicleService.convertToEntity(orderDTO.getDelivery().getVehicleId()))
//                .destination(addressService.convertToEntity(orderDTO.getDelivery().getDestinationId()))
//                .scheduleDate(orderDTO.getDelivery().getScheduleDate())
//                .status(orderDTO.getDelivery().getStatus()).build();
        return Order.builder()
                .id(orderDTO.getId())
                .user(users)
                .status(orderDTO.getStatus())
//                .delivery(delivery)
                .date(orderDTO.getDate())
                .build();
    }

    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderRepositoryDAO.save(order);
        return convertToDTO(savedOrder);
    }


    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepositoryDAO.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .toList();
    }


    public OrderDTO getOrderById(Long id) {
        Order order = orderRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Orderr not found with id: " + id));
        return convertToDTO(order);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order existingOrder = orderRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        Order updatedOrder = convertToEntity(orderDTO);
        updatedOrder.setId(id);
        Order savedOrder = orderRepositoryDAO.save(updatedOrder);
        return convertToDTO(savedOrder);
    }


    public void deleteOrder(Long id) {
        if (!orderRepositoryDAO.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepositoryDAO.deleteById(id);
    }
}
