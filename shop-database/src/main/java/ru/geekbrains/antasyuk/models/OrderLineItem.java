package ru.geekbrains.antasyuk.models;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Data
@Table(name = "order_line_items")
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer qty;

    @Column
    private String color;

    @Column
    private String material;

    public OrderLineItem() {
    }

    public OrderLineItem(Long id,
                         Order order,
                         Product product,
                         BigDecimal price,
                         Integer qty,
                         String color,
                         String material) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.price = price;
        this.qty = qty;
        this.color = color;
        this.material = material;
    }
}