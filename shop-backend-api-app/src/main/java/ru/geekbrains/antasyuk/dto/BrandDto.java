package ru.geekbrains.antasyuk.dto;

import lombok.Data;

@Data
public class BrandDto {
    private Long id;

    private String brandName;

    public BrandDto(Long id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public BrandDto() {
    }
}