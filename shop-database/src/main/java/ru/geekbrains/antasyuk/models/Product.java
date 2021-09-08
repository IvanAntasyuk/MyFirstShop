package ru.geekbrains.antasyuk.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_title",length = 512,nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private BigDecimal cost;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Picture> pictures = new ArrayList<>();

    @Column(name = "main_picture_id")
    private Long mainPicture;


    public Product(String title, BigDecimal cost, Category category) {
        this.title = title;
        this.cost = cost;
        this.category=category;
    }

    public Product(String title, BigDecimal cost, Category category,Brand brand) {
        this.title = title;
        this.cost = cost;
        this.category=category;
        this.brand=brand;
    }

    public Product(String title, BigDecimal cost, Category category,Brand brand,Long mainPicture) {
        this.title = title;
        this.cost = cost;
        this.category=category;
        this.brand=brand;
        this.mainPicture=mainPicture;
    }


    public Product() {
    }

}