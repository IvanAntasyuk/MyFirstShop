package ru.geekbrains.antasyuk.interfaces;

import ru.geekbrains.antasyuk.dto.LineItem;
import ru.geekbrains.antasyuk.dto.OrderDto;

import java.math.BigDecimal;
import java.util.List;

public interface OrderInterface {

    List<OrderDto> getAllOrders();

    List<OrderDto> getOrderByUser(String username);

    void addOrder(LineItem lineItem, BigDecimal price);

    OrderDto viewOrder(Long id);

}