package ru.geekbrains.antasyuk.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512,nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal cost;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    public Product(String title, BigDecimal cost, Category category) {
        this.title = title;
        this.cost = cost;
        this.category=category;
    }

    public Product() {
    }

}
