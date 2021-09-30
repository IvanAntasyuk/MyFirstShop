package ru.geekbrains.antasyuk.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String status;

    @Column
    private String date;

    public Order(Long id, BigDecimal price, String status,User user,String date) {
        this.id = id;
        this.price = price;
        this.status = status;
        this.user=user;
        this.date=date;
    }

    public Order() {

    }
}