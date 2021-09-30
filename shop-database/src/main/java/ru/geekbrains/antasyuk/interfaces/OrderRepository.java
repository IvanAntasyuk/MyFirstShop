package ru.geekbrains.antasyuk.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.antasyuk.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> getOrderByUser(String username);

}
