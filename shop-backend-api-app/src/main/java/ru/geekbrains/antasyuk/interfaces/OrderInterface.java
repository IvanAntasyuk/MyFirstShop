package ru.geekbrains.antasyuk.interfaces;

import ru.geekbrains.antasyuk.dto.AllCartDto;
import ru.geekbrains.antasyuk.dto.LineItem;
import ru.geekbrains.antasyuk.dto.OrderDto;

import java.math.BigDecimal;
import java.util.List;

public interface OrderInterface {

    List<OrderDto> findOrdersByUsername(String username);

    void createOrder(String username, AllCartDto allCartDto);
}