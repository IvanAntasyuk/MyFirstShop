package ru.geekbrains.antasyuk.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false,name= "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItems;

    public Order() {
    }

    public Order(Long id, LocalDateTime orderDate, OrderStatus status, User user,BigDecimal totalPrice) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.user = user;
        this.totalPrice=totalPrice;

    }

    public enum OrderStatus {
        CREATED, PROCESSED, IN_DELIVERY, DELIVERED, CLOSED, CANCELED
    }
}