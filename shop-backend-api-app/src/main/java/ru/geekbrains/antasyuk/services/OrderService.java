package ru.geekbrains.antasyuk.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.antasyuk.dto.LineItem;
import ru.geekbrains.antasyuk.dto.OrderDto;
import ru.geekbrains.antasyuk.interfaces.OrderInterface;
import ru.geekbrains.antasyuk.interfaces.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderInterface {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> getOrderByUser(String username) {
        return orderRepository.getOrderByUser(username).stream()
                .map(order -> new OrderDto(order.getId(),order.getPrice(),"New",order.getDate(),order.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderDto(order.getId(),order.getPrice(),"New",order.getDate(),order.getUser().getUsername()))
                .collect(Collectors.toList());

    }

    @Override
    public void addOrder(LineItem lineItem, BigDecimal price) {


    }

    @Override
    public OrderDto viewOrder(Long id) {
        return null;
    }
}