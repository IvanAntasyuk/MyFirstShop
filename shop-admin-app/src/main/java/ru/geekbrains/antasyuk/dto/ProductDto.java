package ru.geekbrains.antasyuk.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;

    private String title;

    private String description;

    private BigDecimal cost;

    private CategoryDto category;

    private BrandDto brand;

    public ProductDto(Long id, String title, String description, BigDecimal cost, CategoryDto category,BrandDto brand) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.category = category;
        this.brand=brand;
    }

    public ProductDto() {

    }
}