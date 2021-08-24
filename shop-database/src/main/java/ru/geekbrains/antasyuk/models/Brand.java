package ru.geekbrains.antasyuk.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="brand_name",length = 512,nullable = false)
    private String brandName;

    @OneToMany(mappedBy = "brand")
    List<Product> products;

    public Brand(Long id,String brandName) {
        this.id=id;
        this.brandName = brandName;
    }

    public Brand(){}


}