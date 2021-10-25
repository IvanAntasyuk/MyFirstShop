package ru.geekbrains.antasyuk.dto;

import lombok.Data;
import ru.geekbrains.antasyuk.models.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class OrderDto {

    private Long id;
    private BigDecimal price;
    private LocalDateTime orderDate;
    private Order.OrderStatus status;

    public OrderDto() {
    }

    public OrderDto(Long id, BigDecimal price, LocalDateTime orderDate, Order.OrderStatus status) {
        this.id = id;
        this.price = price;
        this.orderDate = orderDate;
        this.status = status;
    }

    public OrderDto(Long id, Order.OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public OrderDto(Order order) {
        this(order.getId(), order.getTotalPrice(), order.getOrderDate(), order.getStatus());
    }


}