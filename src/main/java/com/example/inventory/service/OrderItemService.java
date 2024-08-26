package com.example.inventory.service;

import com.example.inventory.DTO.OrderItemDTO;
import com.example.inventory.model.OrderItem;
import com.example.inventory.model.Order;
import com.example.inventory.model.Product;
import com.example.inventory.model.Users;
import com.example.inventory.repositoryDAO.OrderRepositoryDAO;
import com.example.inventory.repositoryDAO.ProductRepositoryDAO;
import com.example.inventory.repositoryDAO.OderItemRepositoryDAO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final OderItemRepositoryDAO orderItemRepositoryDAO;
    private final OrderRepositoryDAO orderRepositoryDAO;
    private final ProductRepositoryDAO productRepositoryDAO;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;

    public OrderItemService(OderItemRepositoryDAO orderItemRepositoryDAO,
                            OrderRepositoryDAO orderRepositoryDAO,
                            ProductRepositoryDAO productRepositoryDAO, OrderService orderService, ProductService productService, UserService userService) {
        this.orderItemRepositoryDAO = orderItemRepositoryDAO;
        this.orderRepositoryDAO = orderRepositoryDAO;
        this.productRepositoryDAO = productRepositoryDAO;
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;

    }

    public OrderItemDTO convertToDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .order(orderService.convertToDTO(orderItem.getOrder()))
                .product(productService.convertToDTO(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .weight(orderItem.getWeight())
                .build();
    }

    public OrderItem convertToEntity(OrderItemDTO orderItemDTO) {
        Order order = orderRepositoryDAO.findById(orderItemDTO.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderItemDTO.getOrder().getId()));
        Product product = productRepositoryDAO.findById(orderItemDTO.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + orderItemDTO.getProduct().getId()));

        return OrderItem.builder()
                .id(orderItemDTO.getId())
                .order(order)
                .product(product)
                .quantity(orderItemDTO.getQuantity())
                .weight(orderItemDTO.getWeight())
                .build();
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            throw new IllegalArgumentException("OrderItem cannot be null");
        }
        orderItemRepositoryDAO.save(orderItem);
    }



    // Method to place an order
    public void placeOrder(List<OrderItemDTO> orderItemDTOs, Long userId, LocalDate date) {
        if (orderItemDTOs == null || orderItemDTOs.isEmpty()) {
            throw new IllegalArgumentException("OrderItemDTO list cannot be null or empty");
        }

        Users user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        Order order = Order.builder()
                .user(user)
                .status("PENDING")
                .date(date)
                .build();

        Order savedOrder = orderRepositoryDAO.save(order);

        for (OrderItemDTO dto : orderItemDTOs) {
            if (dto == null) {
                throw new IllegalArgumentException("OrderItemDTO cannot be null");
            }

            OrderItem orderItem = convertToEntity(dto);
            orderItem.setOrder(savedOrder);
            addOrderItem(orderItem);
        }
    }

    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepositoryDAO.findAll();
        return orderItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));
        return convertToDTO(orderItem);
    }

    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem existingOrderItem = orderItemRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + id));

        // Update fields from DTO
        existingOrderItem.setProduct(productService.convertToEntity(orderItemDTO.getProduct()));
        existingOrderItem.setQuantity(orderItemDTO.getQuantity());
        existingOrderItem.setWeight(orderItemDTO.getWeight());

        OrderItem updatedOrderItem = orderItemRepositoryDAO.save(existingOrderItem);
        return convertToDTO(updatedOrderItem);
    }

    public void deleteOrderItem(Long id) {
        if (!orderItemRepositoryDAO.existsById(id)) {
            throw new RuntimeException("OrderItem not found with id: " + id);
        }
        orderItemRepositoryDAO.deleteById(id);
    }

    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItems = orderItemRepositoryDAO.findByOrderId(orderId);
        return orderItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }






}
